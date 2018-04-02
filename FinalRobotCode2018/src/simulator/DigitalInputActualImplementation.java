package simulator;

import edu.wpi.first.wpilibj.DigitalInput;

public class DigitalInputActualImplementation implements DigitalInputInterface {
	private DigitalInput mInput;

	public DigitalInputActualImplementation(DigitalInput pInput) {
		mInput = pInput;
	}

	@Override
	public void set() {
		// nothing
	}

	@Override
	public boolean get() {
		return mInput.get();
	}

	@Override
	public void simulate() {
	}

}
