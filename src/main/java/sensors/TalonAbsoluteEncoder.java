package sensors;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import resource.ResourceFunctions;

public class TalonAbsoluteEncoder extends RotationInputter {

	WPI_TalonSRX mTalon;

	public TalonAbsoluteEncoder(WPI_TalonSRX pTalon, double pOffset) {
		super(pOffset);

		mTalon = pTalon;
		mTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 10);
	}

	public double getRawAngleDegrees() {
		return ResourceFunctions.tickToAngle(mTalon.getSelectedSensorPosition(0));
	}

	public double getVelocity(){
		return mTalon.getSelectedSensorVelocity(0);
	}

}
