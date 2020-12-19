/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package constants;

/**
 * Constants for cameras. Mostly used for the Limelight. Can create static
 * subclasses for each camera used, if necessary.
 */
public class CameraConstants {

    /**
     * Height of Limelight in inches. Used when looking at a target on the floor.
     * 
     * <p>
     * Found that it's more accurate to derive this value from the Limelight's
     * output than to measure it.
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

    /**
     * Amount of milliseconds it takes the Limelight to capture an image. Value is
     * from web documentation and should be treated as a minimum. Purely for
     * estimation purposes.
     */
    public static final double IMAGE_CAPTURE_LATENCY = 11;

    /**
     * The lowest valid pipeline index. Update based on Limelight software.
     */
    public static final int MIN_PIPELINE = 0;

    /**
     * The highest valid pipeline index. Update based on Limelight software.
     */
    public static final int MAX_PIPELINE = 9;

    /**
     * Time threshold that checks whether the Limelight is connected or not. Units
     * unclear.
     */
    public static final long MAX_UPDATE_TIME = 200;

    /**
     * Initial size of the HashMap that contains the NetworkTableEntries.
     */
    public static final int HASH_TABLE_INITIAL_SIZE = 20;

    /**
     * Default pipeline used for the Limelight.
     */
    public static final int DEFAULT_PIPELINE = 0;

}
