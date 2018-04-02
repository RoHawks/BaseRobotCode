package robotcode.systems;

import constants.HingeConstants;
import sensors.OtherTalonAbsoluteEncoder;
import simulator.talon.TalonInterface;

public class IntakeHingeMotor {
	private TalonInterface mLeftHinge;
	private TalonInterface mRightHinge;
	private HingeState mState;
	private OtherTalonAbsoluteEncoder mLeftEncoder, mRightEncoder;

	public IntakeHingeMotor(TalonInterface pLeftHinge, TalonInterface pRightHinge) {
		mLeftHinge = pLeftHinge;
		mRightHinge = pRightHinge;
		mLeftEncoder = new OtherTalonAbsoluteEncoder(mLeftHinge, HingeConstants.Motor.LEFT_OFFSET);
		mRightEncoder = new OtherTalonAbsoluteEncoder(mRightHinge, HingeConstants.Motor.RIGHT_OFFSET);
		setState();
	}

	public enum HingeState {
		UP,
		DOWN,
		MIDWAY,
		SCREWED
	}

	public void down() {
		mState = HingeState.MIDWAY;
		if (mLeftHinge.getOutputCurrent() > HingeConstants.Motor.MAX_CURRENT || 
				mLeftEncoder.getAngleDegrees() <= HingeConstants.Motor.LEFT_DOWN + HingeConstants.Motor.MOVE_TOLERANCE) {
			mLeftHinge.set(0);
		}
		else {
			mLeftHinge.set(0.2);
		}

		if (mRightHinge.getOutputCurrent() > HingeConstants.Motor.MAX_CURRENT || 
				mRightEncoder.getAngleDegrees() >= HingeConstants.Motor.RIGHT_DOWN - HingeConstants.Motor.MOVE_TOLERANCE) {
			mRightHinge.set(0);
		} 
		else {
			mRightHinge.set(0.2);
		}

		if (mLeftEncoder.getAngleDegrees() <= HingeConstants.Motor.LEFT_DOWN + HingeConstants.Motor.MOVE_TOLERANCE
				&& mRightEncoder.getAngleDegrees() >= HingeConstants.Motor.RIGHT_DOWN - HingeConstants.Motor.MOVE_TOLERANCE) {
			mState = HingeState.DOWN;
		}
	}

	public void up() {
		mState = HingeState.MIDWAY;
		if (mLeftHinge.getOutputCurrent() > HingeConstants.Motor.MAX_CURRENT || mLeftEncoder.getAngleDegrees() >= HingeConstants.Motor.LEFT_UP - HingeConstants.Motor.MOVE_TOLERANCE) {
			mLeftHinge.set(0);
		} 
		else {
			mLeftHinge.set(-0.3);
		}

		if (mRightHinge.getOutputCurrent() > HingeConstants.Motor.MAX_CURRENT || 
				mRightEncoder.getAngleDegrees() <= HingeConstants.Motor.RIGHT_UP + HingeConstants.Motor.MOVE_TOLERANCE) {
			mRightHinge.set(0);
		} 
		else {
			mRightHinge.set(-0.2);
		}

		if (mLeftEncoder.getAngleDegrees() >= HingeConstants.Motor.LEFT_UP - HingeConstants.Motor.MOVE_TOLERANCE
				&& mRightEncoder.getAngleDegrees() <= HingeConstants.Motor.RIGHT_UP
				+ HingeConstants.Motor.MOVE_TOLERANCE) {
			mState = HingeState.UP;
		}
	}

	public void setSpeed(double speed) {
		mLeftHinge.set(speed);
		mRightHinge.set(speed);
	}

	private void setState() {
		boolean leftMidway = mLeftEncoder.getAngleDegrees() < HingeConstants.Motor.LEFT_UP - 10
				&& mLeftEncoder.getAngleDegrees() > HingeConstants.Motor.LEFT_DOWN + 5;
		boolean rightMidway = mRightEncoder.getAngleDegrees() > HingeConstants.Motor.RIGHT_UP + 10
				&& mRightEncoder.getAngleDegrees() < HingeConstants.Motor.RIGHT_DOWN - 5;
		if (leftMidway) {
			if (rightMidway) {
				mState = HingeState.MIDWAY;
			} 
			else {
				mState = HingeState.SCREWED;
			}
		} 
		else {
			if (rightMidway) {
				mState = HingeState.SCREWED;
			}
		}
	}

	public HingeState getHingeState() {
		setState();
		return mState;
	}

}