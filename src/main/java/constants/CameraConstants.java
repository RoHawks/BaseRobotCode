/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package constants;

/**
 * Constants for cameras. Mostly used for the Limelight.
 * Can create static subclasses for each camera used, if necessary.
 * 
 * @author Tal Zussman
 * @author Daniel Chao
 */
public class CameraConstants {

    /**
    * Height of Limelight in inches. Used when looking at a target on the floor. Found that it's more accurate to 
    * derive this value from the Limelight's output than to measure it.
    */
    public static final double HEIGHT = 10.00;

    /**
     * Horizontal field of view of the Limelight 2 in degrees.
     */
    public static final double HORIZONTAL_FOV = 59.6;

    /**
     * Vertical field of view of the Limelight 2 in degrees.
     */
    public static final double VERTICAL_FOV = 49.7;

    public static final double IMAGE_CAPTURE_LATENCY = 11;

    public static final int MAX_PIPELINE = 9;

    public static final int MIN_PIPELINE = 0;

    public static final long MAX_UPDATE_TIME = 200000; //TODO check units

    /**
     * Default pipeline used for the Limelight.
     */
    public static final int DEFAULT_PIPELINE = 0;

}
