package frc.robot;

import java.util.ArrayList;

import com.kauailabs.navx.frc.AHRS;

import autonomous.AutonomousRoutineType;
import autonomous.commands.AutonomousCommand;
import autonomous.routines.DefaultRoutine;
import autonomous.routines.DoNothingRoutine;
import common.motors.TalonSRX;
import common.motors.configs.TalonSRXConfig;
import common.motors.interfaces.IMotor;
import config.Config;
import config.Robot2017Config;
import config.Robot2018Config;
import config.Robot2019Config;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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

	//config
	private Config mConfig;

	// controllers
	private XboxController mController;
	private Joystick mJoystick;

	// drive train
	private DriveTrain mDriveTrain;
	private Wheel[] mWheel = new Wheel[4];
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

	//a test intake
	private IMotor intakeMotor;
	private double intakeOutput;

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
		mConfig = new Robot2019Config();
		//mConfig = new Robot2018Config();
		// mConfig = new Robot2017Config();
		mController = new XboxController(mConfig.ports.XBOX);
		mNavX = new AHRS(mConfig.ports.NAVX);
		mPDP = new PowerDistributionPanel();

		if (mConfig.runConstants.RUNNING_DRIVE) {
			driveInit();
		}

		if (mConfig.runConstants.RUNNING_INTAKE) {
			intakeMotor = new TalonSRX(new TalonSRXConfig(mConfig.intakeConstants.INTAKE_PORT, mConfig.intakeConstants.INTAKE_INVERTED));
			intakeOutput = mConfig.intakeConstants.INTAKE_POWER_OUTPUT;
		}

		if (mConfig.runConstants.SECONDARY_JOYSTICK) {
			mJoystick = new Joystick(mConfig.ports.JOYSTICK);
		}

		if (mConfig.runConstants.RUNNING_CAMERA) {
			UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
			camera.setResolution(240, 180);
			camera.setFPS(30);
		}

		if (mConfig.runConstants.RUNNING_PNEUMATICS) {
			mCompressor = new Compressor(mConfig.ports.COMPRESSOR);
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

			if (mConfig.runConstants.RUNNING_INTAKE && mConfig.runConstants.SECONDARY_JOYSTICK) {
				runIntake();	
			}

			// put info on SmartDashboard
			if (mConfig.runConstants.RUNNING_DRIVE) {
				for (int i = 0; i < 4; i++) {
					SmartDashboard.putNumber("Motor Current " + i, mWheel[i].Drive.getOutput());
					SmartDashboard.putNumber("Current Offset Angle " + i, mWheel[i].Turn.getReversedOffsetAngle());
					SmartDashboard.putBoolean("Wheel Reversed " + i, mWheel[i].Turn.getReversed());
					SmartDashboard.putBoolean("Drive Inverted " + i, mWheel[i].Drive.getInverted());
					SmartDashboard.putNumber("Raw Ticks " + i, mWheel[i].Turn.getRawPosition());
					SmartDashboard.putNumber("Motor Output " + i, mWheel[i].Drive.getOutput());
					SmartDashboard.putNumber("Gyro Raw Angle", mRobotAngle.getRawAngleDegrees());
				}
			}
		

			Timer.delay(0.005); // wait for a motor update time
		}
	}

	private void runIntake() {
		//check secondary for speed change
		if(mJoystick.getRawButtonReleased(mConfig.intakeConstants.SPEED_UP_BUTTON)) {
			intakeOutput += mConfig.intakeConstants.SPEED_INCREMENT;
		}
		else if(mJoystick.getRawButtonReleased(mConfig.intakeConstants.SPEED_DOWN_BUTTON)) {
			intakeOutput -= mConfig.intakeConstants.SPEED_INCREMENT;
		}
		intakeMotor.setOutput(intakeOutput);
		SmartDashboard.putNumber("Intake speed", intakeOutput);
	}

	public void startGame() {
		if (!mInGame) {
			mGameStartMillis = System.currentTimeMillis();

			CompressorWrapper.action(mCompressor, mConfig);
			mInGame = true;
		}
	}

	@Override
	public void disabled() {
		endGame();

		while (this.isDisabled()) {
			if (mConfig.runConstants.SECONDARY_JOYSTICK && mJoystick.getTriggerPressed()) {
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
		if (mConfig.runConstants.RUNNING_DRIVE) {
			mDriveTrain.driveTank();
		}
	}

	private void crabDrive() {
		if (mConfig.runConstants.RUNNING_DRIVE) {
			mDriveTrain.driveCrab();
		}
	}

	private void swerveDrive() {
		if (mConfig.runConstants.RUNNING_DRIVE) {
			mDriveTrain.driveSwerve();
		}
	}

	public void driveInit() {
		for (int i = 0; i < 4; i++) {
			mWheel[i] = new Wheel(mConfig.wheelConfigs[i]);
		}
		mRobotAngle = new RobotAngle(mNavX, false, 0);
		mDriveTrain = new DriveTrain(mWheel, mController, mRobotAngle, mConfig);
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

		if (mConfig.runConstants.SECONDARY_JOYSTICK) {
			for (int i = 1; i < 12; i++) {
				addLogValueBoolean(logString, mJoystick.getRawButton(i));
			}
		}

		if (mConfig.runConstants.RUNNING_DRIVE) {
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

		if (mConfig.runConstants.RUNNING_PNEUMATICS) {
			addLogValueDouble(logString, mCompressor.getCompressorCurrent());
		}
		addLogValueDouble(logString, mPDP.getTotalCurrent());
		addLogValueDouble(logString, mPDP.getVoltage());

		addLogValueEndDouble(logString, mRobotAngle.getAngleDegrees());

		SmartDashboard.putString("LogString", logString.toString());
	}
}
