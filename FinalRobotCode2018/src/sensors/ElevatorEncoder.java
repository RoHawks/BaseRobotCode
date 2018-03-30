package sensors;

import simulator.talon.TalonInterface;

public class ElevatorEncoder {
	TalonInterface mTalon;
	double mOffset;
	
	
	double mSensorReadingAtElevatorBottom;
	
	public void TriggerAtElevatorBottom()
	{
		mSensorReadingAtElevatorBottom = getRawTicks();		
	}
	
	public double getTicksFromElevatorBottom()
	{
		return getRawTicks() - mSensorReadingAtElevatorBottom;
	}
	
	public double getHeightInInchesFromElevatorBottom()
	{
		return (getTicksFromElevatorBottom() / 4096.0) * 2.40 * 5.0 / 2.54;
	}
	
	public ElevatorEncoder (TalonInterface pTalon, double pOffset) {

		mTalon = pTalon;
		mOffset = pOffset;
	}
	
	public double getRawTicks() {
		return mTalon.getSelectedSensorPosition(0);
	}
	
	public double getTicks() {
		return getRawTicks() - getOffset();
	}
	
	public double getRawHeightCenti() {
		return TickToCenti(getRawTicks());
	}
	
	public double getHeightCenti() {
		return TickToCenti(getRawTicks() - this.getOffset());
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