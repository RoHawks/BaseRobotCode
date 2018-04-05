package autonomous.sequence;

import autonomous.AutonomousSequence;
import constants.AutoConstants;
import constants.GrabberConstants;
import robotcode.driving.DriveTrain;
import robotcode.driving.DriveTrain.LinearVelocity;
import robotcode.driving.DriveTrain.RotationalVelocity;
import robotcode.systems.Elevator;
import robotcode.systems.Grabber;
import robotcode.systems.IntakeHingeMotor;

public class SwitchSequence implements AutonomousSequence {//ATS Tune
	
	private DriveTrain mDriveTrain;
	private Elevator mElevator;
	private Grabber mGrabber;
	private IntakeHingeMotor mHinge;

	private long scoringSequenceStartingTime = 0;
	private boolean enteredScoringRoutine = false;

	private long wheelAlignEndingTime = 0;
	private boolean wheelAlignEnded = false;

	private double actualAngle;
	private long actualDriveTime;

	public SwitchSequence(DriveTrain pDriveTrain, Elevator pElevator, Grabber pGrabber, IntakeHingeMotor pHinge) {
		mDriveTrain = pDriveTrain;
		mElevator = pElevator;
		mGrabber = pGrabber;
		mHinge = pHinge;
	}

	@Override
	public boolean run() {
		mElevator.setSwitch();

		if (autonomous.PlateAssignmentReader.GetNearSwitchSide() == 'L') {
			actualAngle = AutoConstants.SwitchMiddle.LEFT_ANGLE;
			actualDriveTime = AutoConstants.SwitchMiddle.SIDEWAYS_FORWARD_FULL_SPEED_DRIVE_TIME_LEFT;
		} 
		else {
			actualAngle = AutoConstants.SwitchMiddle.RIGHT_ANGLE;
			actualDriveTime = AutoConstants.SwitchMiddle.SIDEWAYS_FORWARD_FULL_SPEED_DRIVE_TIME_RIGHT;
		}

		while (!mDriveTrain.AllWheelsInRange(actualAngle)) {
			mHinge.up();
			mGrabber.grab();
			mDriveTrain.enactMovement(0, actualAngle, LinearVelocity.ANGLE_ONLY, RotationalVelocity.NONE, 0);
		} // TZ fixed, need to check

		if (!wheelAlignEnded) {
			wheelAlignEnded = true;
			wheelAlignEndingTime = System.currentTimeMillis();
		}

		if (System.currentTimeMillis() - wheelAlignEndingTime < actualDriveTime) {
			//mGrabber.out(); //for faster release
			mDriveTrain.enactMovement(0, actualAngle, LinearVelocity.NORMAL, RotationalVelocity.NONE, 0.3);
		}

		else if (System.currentTimeMillis() - wheelAlignEndingTime < actualDriveTime
				+ AutoConstants.SwitchMiddle.PISTON_WORK_TIME) {
			mDriveTrain.enactMovement(0, 0, LinearVelocity.NONE, RotationalVelocity.NONE, 0);

			if (!enteredScoringRoutine) {
				enteredScoringRoutine = true;
				scoringSequenceStartingTime = System.currentTimeMillis();
			}

			long scoringSequenceElapsedMilliseconds = System.currentTimeMillis() - scoringSequenceStartingTime;

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
		else if (System.currentTimeMillis() - wheelAlignEndingTime < actualDriveTime
				+ AutoConstants.SwitchMiddle.PISTON_WORK_TIME + AutoConstants.SwitchMiddle.BACKWARDS_FULL_SPEED_DRIVE_TIME) {
			mDriveTrain.enactMovement(0, 180, LinearVelocity.NORMAL, RotationalVelocity.NONE, 0.3);
		} 
		else {
			mDriveTrain.enactMovement(0, 0, LinearVelocity.NONE, RotationalVelocity.NONE, 0);
			return true;
		}
		return false;
	}

}
