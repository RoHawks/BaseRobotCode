package sensors;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import resource.ResourceFunctions;
import simulator.talon.TalonInterface;

public class TalonAbsoluteEncoder extends RotationInputter {
	TalonInterface mTalon;

	public TalonAbsoluteEncoder(TalonInterface pTalon, double pOffset) {
		super(pOffset);
		mTalon = pTalon;

		mTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 10);
	}

	public double getRawAngleDegrees() {
		return ResourceFunctions.putAngleInRange(mTalon.getSelectedSensorPosition(0) * 360 / 4096 - this.getOffset());
	}

	public double getAngleDegrees() {
		double angle = getRawAngleDegrees();
		angle = this.getAdd180() ? angle + 180 : angle;
		angle = ResourceFunctions.putAngleInRange(angle);

		return angle;
	}

}
