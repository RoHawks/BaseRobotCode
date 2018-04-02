package autonomous.sequence;

import autonomous.AutonomousSequence;
import constants.AutoConstants;
import constants.GrabberConstants;
import robotcode.driving.DriveTrain;
import robotcode.driving.DriveTrain.LinearVelocity;
import robotcode.driving.DriveTrain.RotationalVelocity;
import robotcode.driving.Wheel;
import robotcode.systems.Elevator;
import robotcode.systems.Grabber;
import robotcode.systems.IntakeHingeMotor;

public class SwitchSequence implements AutonomousSequence {//ATS Tune
	private final double WHEEL_ANGLE = 45;
	private final double ACCELERATE_TO_SPEED = 0.6;
	private final long ACCELERATION_TIME = 1000;
	private final long DRIVE_FULL_SPEED_TIME = 1000;
	private final long DECELERATION_TIME = 1000;	
	private final double DRIVE_BACKWARDS_SPEED = 0.3;//Going back by wheel angle instead
	private final long DRIVE_BACKWARDS_TIME = 2000;
	
	private DriveTrain mDriveTrain;
	private Elevator mElevator;
	private Grabber mGrabber;
	private IntakeHingeMotor mHinge;
	private Wheel[] mWheel;
	
	private long autoTimeStart = System.currentTimeMillis();
	private long scoringSequenceStartingTime = 0;
	private boolean enteredScoringRoutine = false;
	private double actualAngle;
	private long actualDriveTime;
	
	public SwitchSequence(DriveTrain pDriveTrain, Elevator pElevator, Grabber pGrabber, IntakeHingeMotor pHinge,
			Wheel[] pWheel)	{
		mDriveTrain = pDriveTrain;
		mElevator = pElevator;
		mGrabber = pGrabber;
		mHinge = pHinge;
		mWheel = pWheel;
	}
	
	
	@Override
	public boolean run() {
		mElevator.setSwitch();

		if (autonomous.PlateAssignmentReader.GetNearSwitchSide() == 'L') {
			actualAngle = AutoConstants.SwitchMiddle.LEFT_ANGLE;
			actualDriveTime = AutoConstants.SwitchMiddle.FORWARD_DRIVE_TIME_LEFT;
		} 
		else {
			actualAngle = AutoConstants.SwitchMiddle.RIGHT_ANGLE;
			actualDriveTime = AutoConstants.SwitchMiddle.FORWARD_DRIVE_TIME_RIGHT;
		}
		
		while (!mDriveTrain.AllWheelsInRange(actualAngle)) {
			mHinge.up();
			mGrabber.grab();
			for (int i = 0; i < 4; i++) {
				mWheel[i].set(actualAngle, 0);
			}
		}//TZ fix
		
		if (System.currentTimeMillis() - autoTimeStart < AutoConstants.SwitchMiddle.WHEEL_POINT_TIME
				+ actualDriveTime) {
			mDriveTrain.enactMovement(0, actualAngle, LinearVelocity.NORMAL, RotationalVelocity.NONE, 0.3);
		} 
		else if (System.currentTimeMillis() - autoTimeStart < AutoConstants.SwitchMiddle.WHEEL_POINT_TIME
				+ actualDriveTime + AutoConstants.SwitchMiddle.PISTON_WORK_TIME) {
			for (int i = 0; i < 4; i++) {
				mWheel[i].set(0, 0);
			}

			if (enteredScoringRoutine == false) {
				enteredScoringRoutine = true;
				scoringSequenceStartingTime = System.currentTimeMillis();
			}

			long scoringSequenceElapsedMilliseconds = System.currentTimeMillis() - scoringSequenceStartingTime;
			// ATS play around with these times to make it faster

			if (scoringSequenceElapsedMilliseconds < GrabberConstants.EXTEND_PISTON_OUT_TIME) {
				mGrabber.out();
				mGrabber.grab();
			} 
			else if (scoringSequenceElapsedMilliseconds < GrabberConstants.EXTEND_PISTON_OUT_TIME
					+ GrabberConstants.GRAB_PISTON_OUT_TIME) {
				mGrabber.out();
				mGrabber.release();
			} 
			else if (scoringSequenceElapsedMilliseconds < GrabberConstants.EXTEND_PISTON_OUT_TIME
					+ GrabberConstants.GRAB_PISTON_OUT_TIME + GrabberConstants.EXTEND_PISTON_IN_TIME) {
				mGrabber.in();
				mGrabber.release();
			}
		} 
		else if (System.currentTimeMillis() - autoTimeStart < AutoConstants.SwitchMiddle.WHEEL_POINT_TIME
				+ actualDriveTime + AutoConstants.SwitchMiddle.PISTON_WORK_TIME
				+ AutoConstants.SwitchMiddle.DRIVE_BACKWARDS_TIME) {
			for (int i = 0; i < 4; i++) {
				mWheel[i].set(180, 0.3);
			}

		} 
		else {
			for (int i = 0; i < 4; i++) {
				mWheel[i].set(0, 0);
			}
			return true;
		}
		return false;
	}
	
	
}
