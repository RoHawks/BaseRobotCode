package simulator.gyro;

import com.kauailabs.navx.frc.AHRS;


public class GyroActualImplementation implements GyroInterface{
	private AHRS mNavX;
	
	public GyroActualImplementation (AHRS pInput) {
		mNavX = pInput;
	}

	@Override
	public double getAngle() {
		return mNavX.getAngle();
	}

	@Override
	public double getRate() {
		return mNavX.getRate();
	}

	@Override
	public void reset() {
		mNavX.reset();
	}
	
	
}
