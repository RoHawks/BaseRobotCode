package robotcode.systems;

import constants.HingeConstants;

public class IntakeHingePiston {

	private simulator.solenoid.SolenoidInterface mPiston;

	public IntakeHingePiston(simulator.solenoid.SolenoidInterface pPiston) {
		mPiston = pPiston;
	}

	public void Up() {
		mPiston.set(HingeConstants.Piston.UP);
	}

	public void Down() {
		mPiston.set(HingeConstants.Piston.DOWN);
	}
}
