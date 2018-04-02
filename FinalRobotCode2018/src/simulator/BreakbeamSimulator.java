package simulator;

import edu.wpi.first.wpilibj.Joystick;

public class BreakbeamSimulator implements DigitalInputInterface {
	private Joystick mJoystick;
	private boolean mTriggered;

	public BreakbeamSimulator(Joystick pJoystick) {
		mJoystick = pJoystick;
	}

	@Override
	public void set() {
		if (mJoystick.getRawButtonReleased(9)) {
			mTriggered = !mTriggered;
		}
	}

	@Override
	public boolean get() {
		return mTriggered;
	}

	@Override
	public void simulate() {
		set();
	}
}
