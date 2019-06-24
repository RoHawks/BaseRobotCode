/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package robotcode.pneumatics;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

/**
 * Base interface for Solenoids to allow for wrapping the WPI Solenoid classes
 * 
 * @author Tal Zussman
 * @author Alex Cohen
 */
public interface SolenoidInterface {

    /**
     * @return the Solenoid's current state.
     */
    public Value get();

    /**
     * Sets the Solenoid's state.
     * 
     * @param pDirection direction to set the solenoid.
     */
    public void set(Value pDirection);

    /**
     * Flips the solenoid's direction.
     */
    public void setOpposite();

}
