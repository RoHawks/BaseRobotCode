/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package sensors;

/**
 * Add your docs here.
 */
public interface Encoder {

    public double getRawPosition();

    public double getCookedPosition();

    public double getRawVelocity();

    public double getCookedVelocity();
    
}
