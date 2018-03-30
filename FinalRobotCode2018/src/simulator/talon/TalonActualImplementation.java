package simulator.talon;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class TalonActualImplementation implements TalonInterface {
	private WPI_TalonSRX mTalon;

	@Override
	public WPI_TalonSRX getTalon() {
		return mTalon;
	}

	public TalonActualImplementation(WPI_TalonSRX pTalon) {
		mTalon = pTalon;
	}

	@Override
	public void set(double p) {
		mTalon.set(p);
	}

	@Override
	public void set(ControlMode pMode, double p) {
		mTalon.set(pMode, p);

	}

	@Override
	public void setInverted(boolean p) {
		mTalon.setInverted(p);

	}

	@Override
	public void configSelectedFeedbackSensor(FeedbackDevice p1, int p2, int p3) {
		mTalon.configSelectedFeedbackSensor(p1, p2, p3);

	}

	@Override
	public void setSensorPhase(boolean p) {
		mTalon.setSensorPhase(p);

	}

	@Override
	public void setNeutralMode(NeutralMode p) {
		mTalon.setNeutralMode(p);

	}

	@Override
	public void config_kP(int p1, double p2, int p3) {
		mTalon.config_kP(p1, p2, p3);

	}

	@Override
	public void config_kI(int p1, double p2, int p3) {
		mTalon.config_kI(p1, p2, p3);

	}

	public double getMotorOutputPercent() {
		return mTalon.getMotorOutputPercent();
	}

	public boolean getInverted() {
		return mTalon.getInverted();
	}

	@Override
	public void config_kD(int p1, double p2, int p3) {
		mTalon.config_kD(p1, p2, p3);

	}

	@Override
	public void configAllowableClosedloopError(int p, int p2, int p3) {
		mTalon.configAllowableClosedloopError(p, p2, p3);

	}

	public void config_IntegralZone(int p1, int p2, int p3) {
		mTalon.config_IntegralZone(p1, p2, p3);
	}

	public int getSelectedSensorPosition(int p) {
		return mTalon.getSelectedSensorPosition(p);
	}

	public double getOutputCurrent() {
		return mTalon.getOutputCurrent();
	}

	public int getSensorCollection_getPulseWidthPosition() {
		return mTalon.getSensorCollection().getPulseWidthPosition();
	}

	public boolean getSensorCollection_isRevLimitSwitchClosed() {
		return mTalon.getSensorCollection().isRevLimitSwitchClosed();
	}

	public void setSelectedSensorPosition(int p1, int p2, int p3) {
		mTalon.setSelectedSensorPosition(p1, p2, p3);
	}

	public void configPeakOutputForward(double pOutput, int j) {
		mTalon.configPeakOutputForward(pOutput, j);
	}

	public void configPeakOutputReverse(double pOutput, int j) {
		mTalon.configPeakOutputReverse(pOutput, j);
	}

	public double getClosedLoopError(int p1) {
		return mTalon.getClosedLoopError(p1);
	}

	public double getMotorOutputVoltage() {
		return mTalon.getMotorOutputVoltage();
	}

	public void simulate(long pDeltaMillis) {

	}

	@Override
	public void configPeakCurrentDuration(int milliseconds, int timeoutMs) {
		mTalon.configPeakCurrentDuration(milliseconds, timeoutMs);
	}

	@Override
	public void configPeakCurrentLimit(int amps, int timeoutMs) {
		mTalon.configPeakCurrentLimit(amps, timeoutMs);
	}

	@Override
	public void configContinuousCurrentLimit(int amps, int timeoutMs) {
		mTalon.configContinuousCurrentLimit(amps, timeoutMs);
	}

	@Override
	public void enableCurrentLimit(boolean enable) {
		mTalon.enableCurrentLimit(enable);
	}

	@Override
	public boolean getSensorCollection_isFwdLimitSwitchClosed() {
		return mTalon.getSensorCollection().isFwdLimitSwitchClosed();
	}

}
