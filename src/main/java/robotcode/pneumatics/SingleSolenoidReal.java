package robotcode.pneumatics;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Solenoid;

/**
 * Class that wraps the WPI Library Solenoid class.
 * 
 * <p>
 * Maps the {@code Solenoid} class' true/false value to
 * {@code DoubleSolenoid.Value} for clarity. The class also protects against
 * repeated calls to the JNI wrapper to optimize latency by keeping track of its
 * state internally and setting the new value only when required.
 * 
 * @author Tal Zussman
 * @author Alex Cohen
 */
public class SingleSolenoidReal implements SolenoidInterface {

	/*
	 * TODO would ideally extend from the Solenoid class for maximum functionality,
	 * and use of all Solenoid methods, but then we couldn't use Value. Find work
	 * around.
	 */

	private Solenoid mSingleSolenoid;
	private Value mCurrent;
	/*
	 * In order to limit calls to the JNI wrapper, we have the class keep track of
	 * its state internally through the mCurrent variabale instead of using the
	 * get() method over and over again. We then also only use the set() method when
	 * required - that is, when the value the Solenoid is to be set to is actually
	 * different from the current state.
	 */

	/**
	 * @param pPort the port the Solenoid is connected to on the PCM.
	 */
	public SingleSolenoidReal(int pPort) {
		mSingleSolenoid = new Solenoid(pPort);
		mCurrent = this.getActual();
	}

	/**
	 * Constructs a SingleSolenoidReal. To be used when more than one PCM is in use.
	 * 
	 * @param pPort         the port the solenoid is connected to on the PCM.
	 * @param pModuleNumber the PCM number the solenoid is connected to.
	 */
	public SingleSolenoidReal(int pPort, int pModuleNumber) {
		mSingleSolenoid = new Solenoid(pModuleNumber, pPort);
		mCurrent = this.getActual();
	}

	/**
	 * @return The current state of the solenoid using the internal tracking.
	 */
	public Value get() {
		return mCurrent;
	}

	/**
	 * @return The current state of the solenoid through an actual query. Should
	 *         never need to be used, unless there are potential connectivity or
	 *         other issues.
	 */
	public Value getActual() {
		// The WPI Solenoid class returns a boolean value, and we map it to a Value
		return mSingleSolenoid.get() ? Value.kForward : Value.kReverse;
	}

	public void set(Value pDirection) {
		// Checks if the state actually needs to be set again, in order to limit
		// tripping the JNI wrapper
		if (pDirection != mCurrent) {
			// forward maps to true, backward maps to false
			mSingleSolenoid.set(pDirection == Value.kForward);
			mCurrent = pDirection; // updates the internal tracking
		}
	}

	public void setOpposite() {
		this.set((this.get() == Value.kForward) ? Value.kReverse : Value.kForward);
	}

}
