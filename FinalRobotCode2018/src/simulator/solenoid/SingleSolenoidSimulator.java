package simulator.solenoid;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class SingleSolenoidSimulator implements SolenoidInterface {

	private Value mValue = Value.kReverse;

	@Override
	public void set(Value pDirection) {
		mValue = pDirection;
	}

	@Override
	public Value get() {
		return mValue;
	}

	public void setOpposite() {
		this.set(this.get().equals(Value.kForward) ? Value.kReverse : Value.kForward);
	}

	@Override
	public void simulate(long pDeltaMillis) {
		// vroom
	}

}
