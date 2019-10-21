package sensors;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

/**
 * Wrapper for the Talon's relative encoder. This class is mainly used for
 * velocity.
 */
public class TalonRelativeEncoder extends RelativeEncoder {

	private WPI_TalonSRX mTalon;

	/**
	 * @param pTalon      the Talon the encoder is connected to
	 * @param pTicksToRPS conversion factor from ticks per 100 ms to revolutions per
	 *                    second
	 */
	public TalonRelativeEncoder(WPI_TalonSRX pTalon, double pTicksToRPS) {
		super(pTicksToRPS);
		mTalon = pTalon;

		mTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
	}

	public double getRawTicksPerSecond() {
		return mTalon.getSelectedSensorVelocity(0);
	}
}
