package sensors;

import simulator.talon.TalonInterface;

public class ElevatorEncoder {
	TalonInterface mTalon;
	double mOffset;

	public ElevatorEncoder(TalonInterface pTalon) {
		mTalon = pTalon;
	}

	public void TriggerAtElevatorBottom() {
		mOffset = getRawTicks();
	}

	public double getTicksFromElevatorBottom() {
		return getRawTicks() - mOffset;
	}

	public double getHeightInInchesFromElevatorBottom() {
		return TickToInch(getTicksFromElevatorBottom());
	}

	public double getRawTicks() {
		return mTalon.getSelectedSensorPosition(0);
	}

	public double getRawHeightCenti() {
		return TickToCenti(getRawTicks());
	}

	public double getHeightCenti() {
		return TickToCenti(getTicksFromElevatorBottom());
	}

	public double getOffset() {
		return mOffset;
	}

	public static double TickToCenti(double tick) {
		return tick * 12 / 4096;
	}

	public static double CentiToTick(double centi) {
		return centi * 4096 / 12;
	}

	public static double InchToTick(double inch) {
		return CentiToTick(2.54 * inch);
	}

	public static double TickToInch(double tick) {
		return TickToCenti(tick) / 2.54;
	}

}