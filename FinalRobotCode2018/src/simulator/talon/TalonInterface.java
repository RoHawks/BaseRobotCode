package simulator.talon;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public interface TalonInterface {

	void set(double p);

	void set(ControlMode pMode, double p);

	void setInverted(boolean p);

	void configSelectedFeedbackSensor(FeedbackDevice p1, int p2, int p3);

	void setSensorPhase(boolean p);

	void setNeutralMode(NeutralMode p);

	void config_kP(int p1, double p2, int p3);

	void config_kI(int p1, double p2, int p3);

	void config_kD(int p1, double p2, int p3);

	void configAllowableClosedloopError(int p, int p2, int p3);

	void config_IntegralZone(int p1, int p2, int p3);

	double getMotorOutputPercent();

	double getOutputCurrent();

	double getMotorOutputVoltage();

	boolean getInverted();

	boolean getSensorCollection_isRevLimitSwitchClosed();

	boolean getSensorCollection_isFwdLimitSwitchClosed();

	int getSelectedSensorPosition(int p);

	int getSensorCollection_getPulseWidthPosition();

	double getClosedLoopError(int p1);

	void configPeakOutputForward(double pOutput, int j);

	void configPeakOutputReverse(double pOutput, int j);

	void configPeakCurrentDuration(int milliseconds, int timeoutMs);

	void configPeakCurrentLimit(int amps, int timeoutMs);

	void configContinuousCurrentLimit(int amps, int timeoutMs);

	void enableCurrentLimit(boolean enable);

	void setSelectedSensorPosition(int p1, int p2, int p3);

	void simulate(long pDeltaMillis);

	WPI_TalonSRX getTalon();

}