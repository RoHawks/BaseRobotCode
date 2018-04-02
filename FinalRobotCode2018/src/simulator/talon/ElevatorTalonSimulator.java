package simulator.talon;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ElevatorTalonSimulator extends BaseTalonSimulator {

	private int mBottomLimitSwitchTicks = 9000;

	@Override
	public int getSensorCollection_getPulseWidthPosition() {
		return mCurrentTicks;
	}

	@Override
	public boolean getSensorCollection_isRevLimitSwitchClosed() {
		return mCurrentTicks <= mBottomLimitSwitchTicks;
	}

	@Override
	public void setSelectedSensorPosition(int p1, int p2, int p3) {
		// wherever we were relative to the bottom, we need to adjust the bottom to
		// still be that far away;
		int currentDistanceFromBottom = mCurrentTicks - mBottomLimitSwitchTicks;
		mBottomLimitSwitchTicks = p2 - currentDistanceFromBottom;
		mCurrentTicks = p2;
		// mCurrentTicks = p2;
	}

	public int getSelectedSensorPosition(int p) {
		return mCurrentTicks;
	}

	public void simulate(long pMillisecondsSinceLastUpdate) {
		double rawSpeed = 18700;
		double gearRatio = 100;
		double efficiency = 0.8 * 0.8 * 0.8 * 0.95;
		double ticksPerRotation = 4096;

		if (mCurrentControlMode == ControlMode.Position) {
			pidCalc();
		}

		double changeInTicks = pMillisecondsSinceLastUpdate / 1000.0 * // seconds
				rawSpeed / 60.0 / gearRatio * efficiency * this.mCurrentPercentOutput// revs per second
				* ticksPerRotation; // ticksPerRotation

		SmartDashboard.putNumber("percent output", this.mCurrentPercentOutput);
		SmartDashboard.putNumber("change in ticks", changeInTicks);

		mCurrentTicks += changeInTicks;
		SmartDashboard.putNumber("current ticks", mCurrentTicks);
	}

	@Override
	public void configPeakCurrentDuration(int milliseconds, int timeoutMs) {
		// TODO Auto-generated method stub

	}

	@Override
	public void configPeakCurrentLimit(int amps, int timeoutMs) {
		// TODO Auto-generated method stub

	}

	@Override
	public void configContinuousCurrentLimit(int amps, int timeoutMs) {
		// TODO Auto-generated method stub

	}

	@Override
	public void enableCurrentLimit(boolean enable) {
		// TODO Auto-generated method stub

	}

}
