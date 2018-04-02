package simulator.talon;

public class IntakeWheelSimulator extends BaseTalonSimulator {

	private boolean mOn;
	private boolean mDirection; // true = forward

	@Override
	public boolean getSensorCollection_isRevLimitSwitchClosed() {
		return false;
	}

	@Override
	public void setSelectedSensorPosition(int p1, int p2, int p3) {
	}

	public boolean getOn() {
		return mOn;
	}

	public boolean getDirection() {
		return mDirection;
	}

	@Override
	public void simulate(long pMillisecondsSinceLastUpdate) {
		mOn = this.getMotorOutputPercent() != 0;
		mDirection = this.getMotorOutputPercent() > 0;
	}

	@Override
	public void configPeakCurrentDuration(int milliseconds, int timeoutMs) {

	}

	@Override
	public void configPeakCurrentLimit(int amps, int timeoutMs) {

	}

	@Override
	public void configContinuousCurrentLimit(int amps, int timeoutMs) {

	}

	@Override
	public void enableCurrentLimit(boolean enable) {

	}

}
