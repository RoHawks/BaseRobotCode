package frc.robot;

import java.util.ArrayList;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;

import autonomous.AutonomousRoutineType;
import autonomous.commands.AutonomousCommand;
import autonomous.routines.DefaultRoutine;
import autonomous.routines.DoNothingRoutine;
import config.Config;
import config.Config.RunConstants;
import config.TestChassis;
import config.TestChassis.Ports;
import drivetrain.controllers.SparkMax;
import drivetrain.controllers.SparkMax;
import drivetrain.controllers.SparkMax;
import drivetrain.controllers.TalonSRX;
import drivetrain.controllers.TalonSRXWithEncoder;
import drivetrain.controllers.configs.TalonSRXConfig;
import drivetrain.controllers.configs.TalonSRXWithEncoderConfig;
// import constants.DriveConstants;
// import constants.Ports;
// import constants.RunConstants;
// import constants.RobotState;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import resource.ResourceFunctions;
import robotcode.driving.DriveTrain;
import robotcode.driving.Wheel;
import robotcode.systems.CompressorWrapper;
import sensors.RobotAngle;
import sensors.TalonAbsoluteEncoder;

@SuppressWarnings("deprecation")
public class Robot extends SampleRobot {

	// *************//
	// VARIABLES //
	// *************//

	// controllers
	private XboxController mController;
	private Joystick mJoystick;

	// drive train
	private DriveTrain mDriveTrain;
	private Wheel[] mWheel = new Wheel[4];
	private TalonSRXWithEncoder[] mTurn = new TalonSRXWithEncoder[4];
	private SparkMax[] mDrive = new SparkMax[4];
	private TalonAbsoluteEncoder[] mEncoder = new TalonAbsoluteEncoder[4];

	// gyro
	private AHRS mNavX;
	private RobotAngle mRobotAngle;

	// PDP and compressor
	private PowerDistributionPanel mPDP;
	private Compressor mCompressor;

	// autonomous setup
	private AutonomousRoutineType mAutonomousRoutine = AutonomousRoutineType.DEFAULT;

	// game setup
	private boolean mInGame = false;

	private long mGameStartMillis;

	// ****************//
	// GENERAL CODE //
	// ****************//
	public Robot() {
	}

	@Override
	public void test() {
	}

	public void endGame() {
	}

	@Override
	public void robotInit() {

		mController = new XboxController(TestChassis.Ports.XBOX);
		mNavX = new AHRS(TestChassis.Ports.NAVX);
		mPDP = new PowerDistributionPanel();

		if (Config.RunConstants.RUNNING_DRIVE) {
			driveInit();
		}

		if (Config.RunConstants.SECONDARY_JOYSTICK) {
			mJoystick = new Joystick(TestChassis.Ports.JOYSTICK);
		}

		if (Config.RunConstants.RUNNING_CAMERA) {
			UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
			camera.setResolution(240, 180);
			camera.setFPS(30);
		}

		if (Config.RunConstants.RUNNING_PNEUMATICS) {
			mCompressor = new Compressor(TestChassis.Ports.COMPRESSOR);
		}
	}

	@Override
	public void autonomous() {
		// select auto commands
		ArrayList<AutonomousCommand> autonomousCommands;

		if (mAutonomousRoutine == AutonomousRoutineType.DEFAULT) {
			autonomousCommands = (new DefaultRoutine(this)).getAutonomousCommands();
		} else {
			autonomousCommands = (new DoNothingRoutine()).getAutonomousCommands();
		}

		// start game
		startGame();

		// initialize step variables
		int currentStep = 0;
		int previousStep = -1;

		while (isAutonomous() && isEnabled()) {
			SmartDashboard.putNumber("Autonomous step", currentStep);

			if (currentStep < autonomousCommands.size()) {
				AutonomousCommand command = autonomousCommands.get(currentStep);

				if (currentStep != previousStep) {
					command.startup();
					previousStep = currentStep;
				}

				boolean moveToNextStep = command.runCommand();
				if (moveToNextStep) {
					currentStep++;
				}
			} // else we're done with auto

			Timer.delay(0.005);
		}
	}

	public void operatorControl() {
		// start game, again
		startGame();

		while (isOperatorControl() && isEnabled()) {

			swerveDrive();

			if (Config.RunConstants.RUNNING_EVERYTHING) {
				doWork();
			}

			// put info on SmartDashboard
			if (Config.RunConstants.RUNNING_DRIVE) {
				for (int i = 0; i < 4; i++) {
					SmartDashboard.putNumber("Motor Current " + i, mDrive[i].getOutput());
				}
			}

			Timer.delay(0.005); // wait for a motor update time
		}
	}

	private void doWork() {

	}

	private void doSomeAction() {
		// Do some action... move to a different state?
	}

	public void startGame() {
		if (!mInGame) {
			mGameStartMillis = System.currentTimeMillis();

			CompressorWrapper.action(mCompressor);
			mInGame = true;
		}
	}

	@Override
	public void disabled() {
		endGame();

		while (this.isDisabled()) {
			if (Config.RunConstants.SECONDARY_JOYSTICK && mJoystick.getTriggerPressed()) {
				// rotate autonomous routines to select which one to start with:
				if (mAutonomousRoutine == AutonomousRoutineType.DEFAULT) {
					mAutonomousRoutine = AutonomousRoutineType.DO_NOTHING;
				} else if (mAutonomousRoutine == AutonomousRoutineType.DO_NOTHING) {
					mAutonomousRoutine = AutonomousRoutineType.DEFAULT;
				}
			}

			SmartDashboard.putString("AUTO ROUTINE:", mAutonomousRoutine.toString());

			Timer.delay(0.005); // wait for a motor update time
		}
	}

	private void tankDrive() {
		if (RunConstants.RUNNING_DRIVE) {
			mDriveTrain.driveTank();
		}
	}

	private void crabDrive() {
		if (RunConstants.RUNNING_DRIVE) {
			mDriveTrain.driveCrab();
		}
	}

	private void swerveDrive() {
		if (RunConstants.RUNNING_DRIVE) {
			mDriveTrain.driveSwerve();
		}
	}

	public void driveInit() {
		TalonSRXConfig driveConfig;
		TalonSRXWithEncoderConfig turnConfig;
		for (int i = 0; i < 4; i++) {
			driveConfig = new SparkMaxConfig();
			turnConfig = new TalonSRXWithEncoderConfig();
			
			driveConfig.inverted = Config.DriveConstants.DRIVE_INVERTED[i];
			driveConfig.port = Config.Ports.DRIVE[i];

			turnConfig.port = Config.Ports.TURN[i];
			turnConfig.reversed = Config.DriveConstants.ENCODER_REVERSED[i];
			turnConfig.inverted = Config.DriveConstants.TURN_INVERTED[i];
			turnConfig.offset = Config.DriveConstants.OFFSETS[i];
			turnConfig.p = Config.DriveConstants.ROTATION_P[i];
			turnConfig.i = Config.DriveConstants.ROTATION_I[i];
			turnConfig.d = Config.DriveConstants.ROTATION_D[i];
			turnConfig.iZone = Config.DriveConstants.ROTATION_IZONE[i];
			turnConfig.rotationTolerance = Config.DriveConstants.ROTATION_TOLERANCE[i];

			// initialize turn motors and set values:
			
			mTurn[i] = new TalonSRXWithEncoder(turnConfig);
			mDrive[i] = new SparkMax(driveConfig);

			// how to determine IMotor type? reflection?
			// OR maybe, enum for motors and types of motors, then add mappings in the config, then add switch statement to initlaize the wheels propperly?
			// mTurn = new TalonSRXWithEncoder(turnMotorPort, turnEncoderPort, offset);
			// mDrive = new SparkMax(driveMotorPort);
			mWheel[i] = new Wheel(mTurn[i], mDrive[i]);
		}

		mRobotAngle = new RobotAngle(mNavX, false, 0);
		mDriveTrain = new DriveTrain(mWheel, mController, mRobotAngle);
	}

	public DriveTrain getDriveTrain() {
		return mDriveTrain;
	}

	private void addLogValueDouble(StringBuilder pLogString, double pVal) {
		pLogString.append(pVal);
		pLogString.append(",");
	}

	private void addLogValueInt(StringBuilder pLogString, int pVal) {
		pLogString.append(pVal);
		pLogString.append(",");
	}

	private void addLogValueLong(StringBuilder pLogString, long pVal) {
		pLogString.append(pVal);
		pLogString.append(",");
	}

	private void addLogValueBoolean(StringBuilder pLogString, boolean pVal) {
		pLogString.append(pVal ? "1" : "0");
		pLogString.append(",");
	}

	private void addLogValueString(StringBuilder pLogString, String pVal) {
		pLogString.append(pVal);
		pLogString.append(",");
	}

	private void addLogValueEndDouble(StringBuilder pLogString, double pVal) {
		pLogString.append(pVal);
		pLogString.append("\n");
	}

	private void addLogValueEndInt(StringBuilder pLogString, int pVal) {
		pLogString.append(pVal);
		pLogString.append("\n");
	}

	private void addLogValueEndLong(StringBuilder pLogString, long pVal) {
		pLogString.append(pVal);
		pLogString.append("\n");
	}

	private void addLogValueEndBoolean(StringBuilder pLogString, boolean pVal) {
		pLogString.append(pVal ? "1" : "0");
		pLogString.append("\n");
	}

	private void addLogValueEndString(StringBuilder pLogString, String pVal) {
		pLogString.append(pVal);
		pLogString.append("\n");
	}

	public void log() {
		long time = System.currentTimeMillis();
		long timeElapsed = time - mGameStartMillis;

		SmartDashboard.putBoolean("Game Has Started:", mInGame);
		SmartDashboard.putNumber("Time Game Started:", mGameStartMillis);
		SmartDashboard.putNumber("Time Elapsed:", timeElapsed);

		StringBuilder logString = new StringBuilder();

		// for now it is one frame per line
		addLogValueInt(logString, (int) timeElapsed);

		addLogValueBoolean(logString, mController.getYButton());
		addLogValueBoolean(logString, mController.getBButton());
		addLogValueBoolean(logString, mController.getAButton());
		addLogValueBoolean(logString, mController.getXButton());
		addLogValueBoolean(logString, mController.getBumper(Hand.kLeft));
		addLogValueBoolean(logString, mController.getBumper(Hand.kRight));
		addLogValueDouble(logString, mController.getTriggerAxis(Hand.kLeft));
		addLogValueDouble(logString, mController.getTriggerAxis(Hand.kRight));
		addLogValueInt(logString, mController.getPOV());
		addLogValueBoolean(logString, mController.getStartButton());
		addLogValueBoolean(logString, mController.getBackButton());
		addLogValueDouble(logString, mController.getX(Hand.kLeft));
		addLogValueDouble(logString, mController.getY(Hand.kLeft));
		addLogValueDouble(logString, mController.getX(Hand.kRight));
		addLogValueDouble(logString, mController.getY(Hand.kRight));

		if (Config.RunConstants.SECONDARY_JOYSTICK) {
			for (int i = 1; i < 12; i++) {
				addLogValueBoolean(logString, mJoystick.getRawButton(i));
			}
		}

		if (Config.RunConstants.RUNNING_DRIVE) {
			for (int i = 0; i < 4; i++) {
				//TODO: put these back
				// addLogValueDouble(logString, mTurn[i].getOutputCurrent());
				// addLogValueDouble(logString, mDrive[i].getOutputCurrent());

				// addLogValueDouble(logString, mTurn[i].getMotorOutputVoltage());
				// addLogValueDouble(logString, mDrive[i].getMotorOutputVoltage());

				addLogValueDouble(logString, mEncoder[i].getAngleDegrees());
			}

			addLogValueDouble(logString, mDriveTrain.getDesiredRobotVel().getMagnitude());
			addLogValueDouble(logString, mDriveTrain.getDesiredRobotVel().getAngle());
			addLogValueDouble(logString, mDriveTrain.getDesiredAngularVel());
		}

		if (Config.RunConstants.RUNNING_PNEUMATICS) {
			addLogValueDouble(logString, mCompressor.getCompressorCurrent());
		}
		addLogValueDouble(logString, mPDP.getTotalCurrent());
		addLogValueDouble(logString, mPDP.getVoltage());

		addLogValueEndDouble(logString, mRobotAngle.getAngleDegrees());

		SmartDashboard.putString("LogString", logString.toString());
	}
}
