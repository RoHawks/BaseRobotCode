package simulator.talon;

public class HingeTalonSimulator extends BaseTalonSimulator
{

	@Override
	public boolean getSensorCollection_isRevLimitSwitchClosed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setSelectedSensorPosition(int p1, int p2, int p3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void simulate(long pMillisecondsSinceLastUpdate) {
		// TODO Auto-generated method stub
		
	}

//	private double mOffset = 0;
//	private double mCurrentAngle = 230;
//	
//	public HingeTalonSimulator(double pOffset)
//	{
//		mOffset = pOffset;
//		mCurrentTicks =  (int)((230.0 + pOffset) / 360.0 * 4096.0);
//	}
//	
//	private ControlMode mCurrentControlMode = ControlMode.PercentOutput;
//	private double mCurrentPercentOutput = 0;
//	private boolean mInverted = false;
//	private boolean mSensorPhase = false;
//	private double mP = 0;
//	private double mI = 0;
//	private double mD = 0;
//	private double mAllowableClosedloopErorr = 0;
//	private double mIntegralZone  = 0;
//	private int mCurrentTicks;
//	private int mBottomLimitSwitchTicks = 9000;
//	
//	@Override
//	public void set(double p) 
//	{
//		if(mCurrentControlMode == ControlMode.PercentOutput)
//		{
//			//neeed to account for inverted here?
//			mCurrentPercentOutput = p;
//		}
//		else
//		{
//			throw new RuntimeException("In HingeTalonSimulator, ControlMode " + mCurrentControlMode.name() + " not implemented.");
//		}
//	}
//
//	@Override
//	public void set(ControlMode pMode, double p) 
//	{
//		mCurrentControlMode = pMode;
//		set(p);
//		
//	}
//
//	@Override
//	public void setInverted(boolean p) {
//		mInverted = p;
//		
//	}
//
//	@Override
//	public void configSelectedFeedbackSensor(FeedbackDevice p1, int p2, int p3) {
//		
//		
//	}
//
//	@Override
//	public void setSensorPhase(boolean p) {
//		mSensorPhase = p;
//		
//	}
//
//	@Override
//	public void setNeutralMode(NeutralMode p) {
//		
//		
//	}
//
//	@Override
//	public void config_kP(int p1, double p2, int p3) {
//		mP = p2;
//		
//	}
//
//	@Override
//	public void config_kI(int p1, double p2, int p3) {
//		mI = p2;
//		
//	}
//
//	@Override
//	public void config_kD(int p1, double p2, int p3) {
//		mD = p2;
//		
//	}
//
//	@Override
//	public void configAllowableClosedloopError(int p, int p2, int p3) {
//		mAllowableClosedloopErorr = p2;
//		
//	}
//
//	@Override
//	public void config_IntegralZone(int p1, int p2, int p3) {
//		mIntegralZone = p2;
//		
//	}
//
//	@Override
//	public double getMotorOutputPercent() {
//		return mCurrentPercentOutput;
//	}
//
//	@Override
//	public boolean getInverted() {
//		return mInverted;
//	}
//
//	@Override
//	public int getSelectedSensorPosition(int p) {
//		return mCurrentTicks;
//	}
//
//	@Override
//	public double getOutputCurrent() {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public int getSensorCollection_getPulseWidthPosition() {
//		// TODO Auto-generated method stub
//		return mCurrentTicks;
//	}
//
//	@Override
//	public boolean getSensorCollection_isRevLimitSwitchClosed() {
//		return mCurrentTicks <= mBottomLimitSwitchTicks;
//	}
//
//	@Override
//	public void setSelectedSensorPosition(int p1, int p2, int p3) {
//		////wherever we were relative to the bottom, we need to adjust the bottom to still be that far away;
//		int currentDistanceFromBottom = mCurrentTicks - mBottomLimitSwitchTicks;
//		mBottomLimitSwitchTicks = p2 - currentDistanceFromBottom;
//		mCurrentTicks = p2;
//		
//	}
//
//	@Override
//	public double getClosedLoopError(int p1) {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public double getMotorOutputVoltage() {
//		return mCurrentPercentOutput * 12.0;
//	}
//	
//	public void simulate(long pMillisecondsSinceLastUpdate)
//	{
//		double rawSpeed = 18700;
//		double gearRatio = 100;
//		double efficiency = 0.8*0.8*0.8*0.95;
//		double ticksPerRotation = 4096;
//		
//		double changeInTicks = 
//				pMillisecondsSinceLastUpdate / 1000.0 * //seconds 
//				rawSpeed / 60.0  / gearRatio * efficiency //revs per second
//				* ticksPerRotation; //ticksPerRotation
//		
//		mCurrentTicks += changeInTicks;
//	}
//
//	@Override
//	public void configPeakOutputForward(double pOutput, int j) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void configPeakOutputReverse(double pOutput, int j) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void configPeakCurrentDuration(int milliseconds, int timeoutMs) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void configPeakCurrentLimit(int amps, int timeoutMs) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void configContinuousCurrentLimit(int amps, int timeoutMs) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void enableCurrentLimit(boolean enable) {
//		// TODO Auto-generated method stub
//		
//	}
//
}
