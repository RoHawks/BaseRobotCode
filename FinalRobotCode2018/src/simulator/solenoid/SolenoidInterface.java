package simulator.solenoid;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public interface SolenoidInterface {
	void set(Value pDirection);

	Value get();

	void setOpposite();

	void simulate(long pDeltaMillis);
}
