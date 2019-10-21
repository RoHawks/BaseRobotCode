package robotcode.pneumatics;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

/**
 * Class that wraps the WPI Library DoubleSolenoid class.
 * 
 * <p>
 * The class protects against repeated calls to the JNI by tracking its state
 * internally to improve efficiency. See {@link SingleSolenoidReal} for more
 * information.
 * 
 * @author Tal Zussman
 * @author Alex Cohen
 */
public class DoubleSolenoidReal implements SolenoidInterface {

	private DoubleSolenoid mDoubleSolenoid;
	private Value mCurrent;

	/**
	 * @param pInPort  the forward channel number the solenoid is connected to on
	 *                 the PCM.
	 * @param pOutPort the reverse channel number the solenoid is connected to on
	 *                 the PCM.
	 */
	public DoubleSolenoidReal(int pInPort, int pOutPort) {
		mDoubleSolenoid = new DoubleSolenoid(pInPort, pOutPort);
		mCurrent = this.getActual();
	}

	/**
	 * Constructs a DoubleSolenoidReal. To be used when more than one PCM is in use.
	 * 
	 * @param pInPort       the forward channel number the solenoid is connected to
	 *                      on the PCM.
	 * @param pOutPort      the reverse channel number the solenoid is connected to
	 *                      on the PCM.
	 * @param pModuleNumber the PCM number the solenoid is connected to.
	 */
	public DoubleSolenoidReal(int pInPort, int pOutPort, int pModuleNumber) {
		mDoubleSolenoid = new DoubleSolenoid(pModuleNumber, pInPort, pOutPort);
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
		return mDoubleSolenoid.get();
	}

	public void set(Value pDirection) {
		// Checks if the state needs to be set again to avoid tripping the JNI wrapper
		if (pDirection != mCurrent) {
			mDoubleSolenoid.set(pDirection);
			mCurrent = pDirection;
		}
	}

	public void setOpposite() {
		this.set((this.get() == Value.kForward) ? Value.kReverse : Value.kForward);
	}

}
