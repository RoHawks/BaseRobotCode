package simulator;

import edu.wpi.first.wpilibj.Joystick;

public class LimitSwitchSimulator implements DigitalInputInterface {
	private Joystick mJoystick;
	private boolean mTriggered;

	public LimitSwitchSimulator(Joystick pJoystick) {
		mJoystick = pJoystick;
	}

	@Override
	public void set() {
		if (mJoystick.getRawButtonReleased(10)) {
			mTriggered = !mTriggered;
		}
	}

	@Override
	public boolean get() {
		return mTriggered;
	}

	public void simulate() {
		set();
	}
}
