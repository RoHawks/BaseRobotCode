/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package resource.datatypes;

/**
 * A boolean that only changes value once - once it negates, it can't switch
 * back.
 * 
 * <p>
 * For example, given an initial value of true, the boolean will switch to false
 * when told, and then remain as false regardless of future calls to set it back
 * to true.
 * 
 * @author Tal Zussman
 * @author Daniel Chao
 */
public class RatchetBoolean {

    private boolean mInitialValue;
    private boolean mValue;

    /**
     * Creates a {@code RatchetBoolean} with an initial value of {@code pValue}.
     * 
     * @param pValue start value
     */
    public RatchetBoolean(boolean pValue) {
        mValue = pValue;
        mInitialValue = pValue;
    }

    /**
     * Sets the boolean to a new value. Only changes its value the first time it
     * negates.
     * 
     * @param pNew value to switch the boolean to
     */
    public void set(boolean pNew) {
        if (pNew != mInitialValue) {
            mValue = pNew;
        }
    }

    /**
     * Getter for the boolean
     * 
     * @return current value of the boolean
     */
    public boolean get() {
        return mValue;
    }

}
