package simulator.solenoid;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import robotcode.systems.SingleSolenoidReal;

public class SingleSolenoidActualImplementation implements SolenoidInterface{
	
	private SingleSolenoidReal mSolenoid;
	
	public SingleSolenoidActualImplementation(SingleSolenoidReal pSolenoid) {
		mSolenoid = pSolenoid;
	}
	
	@Override
	public void set(Value pDirection) {
		mSolenoid.set(pDirection);
	}

	@Override
	public Value get() {
		return mSolenoid.get();
	}
	
	public void setOpposite() {
		mSolenoid.setOpposite();
	}

	@Override
	public void simulate(long pDeltaMillis) {
		
	}

}
