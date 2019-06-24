/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package robotcode.camera;

import constants.CameraConstants;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

/**
 * Class that simplifies interacting with and getting data from the Limelight
 * NetworkTable. NetworkTable entry names and values can be found in the
 * Limelight documentation:
 * http://docs.limelightvision.io/en/latest/networktables_api.html
 * 
 * @author Daniel Chao
 * @author Tal Zussman
 */
public class Limelight {

    // TODO implement PIDSource allowing Limelight to pass data to LinearFilter class directly
    // TODO consider saving all the NetworkTableEntry's used as instance variables to avoid JNI wrapper
    // Maybe use hash table to hold all the entries or is that really extra?

    private String mTableName;

    /**
     * Constructs a Limelight with the default NetworkTable name of "limelight".
     */
    public Limelight() {
        this("limelight");
    }

    /**
     * Constructs a Limelight with a non-default NetworkTable name. For cases when
     * more than one Limelight is being used and the NetworkTable names need to be
     * set.
     * 
     * @param pTableName name of the Limelight NetworkTable.
     */
    public Limelight(String pTableName) {
        mTableName = pTableName;
    }

    // **********//
    // UTILITY //
    // **********//

    /**
     * Gets {@code NetworkTableEntry} from the Limelight's {@code NetworkTable}
     * 
     * @param key the {@code NetworkTableEntry} name
     * @return The {@code NetworkTableEntry}. If it doesn't exist, it creates it.
     */
    private NetworkTableEntry getEntryFromNetworkTable(String key) {
        return NetworkTableInstance.getDefault().getTable(mTableName).getEntry(key);
    }

    /**
     * Gets a {@code double} value from a specified {@code NetworkTableEntry}.
     * 
     * @param key the {@code NetworkTableEntry} name
     * @return If the data value for the {@code NetworkTableEntry} is a double, it
     *         returns it. Otherwise, it returns {@code Double.NaN}.
     */
    private double getDoubleFromNetworkTable(String key) {
        /*
         * The Limelight documentation says to use 0 instead of Double.NaN as the
         * default value However, some of these methods could return 0 as possible
         * values, so using Double.NaN to avoid that confusion and represent the value
         * for when the entry doesn't exist or is a different type is more robust.
         */
        return getEntryFromNetworkTable(key).getDouble(Double.NaN);
    }

    private boolean mTimingTestValue = false;
    public boolean isConnected(){
        mTimingTestValue = !mTimingTestValue;
        getEntryFromNetworkTable("timing_test").forceSetBoolean(mTimingTestValue);
        long currentTime = getEntryFromNetworkTable("timing_test").getLastChange();

        long lastUpdate = getEntryFromNetworkTable("tl").getLastChange();

        long timeDifference = currentTime - lastUpdate;

        return timeDifference < CameraConstants.MAX_UPDATE_TIME;
        
    } //creds to stuy for the one good piece of code they've ever written
    //and for inspiring me in its jankiness to recognize its unacknowledged and unintended brilliance

    //******//
    // DATA //
    //******//

    /**
     * Gives the vision pipeline's latency contribution.
     * 
     * @return Latency contribution in milliseconds.
     */
    public double getPipelineLatency() {
        return getDoubleFromNetworkTable("tl");
    }

    /**
     * Gives the total latency of the Limelight for one cycle.
     * 
     * @return Latency of the Limelight in milliseconds.
     */
    public double getTotalLatency() {
        return getPipelineLatency() + CameraConstants.IMAGE_CAPTURE_LATENCY;
    }

    /**
     * Calculates the horizontal distance from the center of the Limelight target.
     * 
     * @return Horizontal distance from center of target in units of
     *         {@code CameraConstants.HEIGHT}.
     */
    public double xAngleToDistance() {
        // Limelight gives this distance in degrees, and using trig and our constants we
        // can convert to distance
        // Limelight 2 values: -29.8 to 29.8 degrees
        return (CameraConstants.HEIGHT * Math.tan(Math.toRadians(getDoubleFromNetworkTable("tx"))));
    }

    /**
     * Calculates the vertical distance from the center of the Limelight target.
     * 
     * @return Vertical distance from center of target in units of
     *         {@code CameraConstants.HEIGHT}.
     */
    public double yAngleToDistance() {
        // Limelight gives this distance in degrees, and using trig and our constants we
        // can convert to distance
        // Limelight 2 values: -24.85 to 24.85 degrees
        return (CameraConstants.HEIGHT * Math.tan(Math.toRadians(getDoubleFromNetworkTable("ty"))));
    }

    /**
     * Determines if the Limelight sees a viable target.
     * 
     * @return True if there is a target, false if not.
     */
    public boolean hasTarget() {
        // The Limelight returns 0 if no target is found, and 1 if there is a target
        // found.
        return getDoubleFromNetworkTable("tv") == 1;
    }

    /**
     * The percent of the Limelight's viewing space that its current target takes
     * up. Not used very often.
     * 
     * @return A value from 0.0 to 1.0 representing the target area percent of the
     *         total area.
     */
    public double getTargetAreaPercent() {
        // The Limelight returns a value from 0 to 100, so we divide to get a percent.
        return getDoubleFromNetworkTable("ta") / 100.0;
    }

    /**
     * Gives skew of the Limelight's target. Not used very often.
     * 
     * @return Degrees the target rectangle is off from being perfectly straight.
     */
    public double getTargetSkew() { // -90 to 0 degrees
        return getDoubleFromNetworkTable("ts");
    }

    /**
     * Gives the width of the rough bounding rectangle. Not used very often.
     * 
     * @return Width of the rectangle in pixels (0-320).
     */
    public double getTargetWidth() {
        return getDoubleFromNetworkTable("thor");
    }

    /**
     * Gives the length of the rough bounding rectangle. Not used very often.
     * 
     * @return Length of the rectangle in pixels (0-280).
     */
    public double getTargetLength() {
        return getDoubleFromNetworkTable("tvert");
    }

    /**
     * Gives the length of the shortest side of the fitted bounding box. Not used
     * very often.
     * 
     * @return Length of shortest side in pixels.
     */
    public double getShortestSideLength() {
        return getDoubleFromNetworkTable("tshort");
    }

    /**
     * Gives the length of the longest side of the fitted bounding box. Not used
     * very often.
     * 
     * @return Length of longest side in pixels.
     */
    public double getLongestSideLength() {
        return getDoubleFromNetworkTable("tlong");
    }

    //**************//
    // LED SETTINGS //
    //**************//

    /**
     * An enum that represents the different modes of the Limelight's LED array.
     * 
     * <p>
     * These are:
     * 
     * <ul>
     * <li>PIPELINE: Sets the LED based off of the set pipeline's settings.</li>
     * <li>OFF: Turns the LEDs off regardless of the pipeline settings.</li>
     * <li>BLINK: Sets the LEDs to blink regardless of the pipeline settings.</li>
     * <li>ON: Turns the LEDs on regardless of the pipeline settings.</li>
     * </ul>
     */
    public enum LEDMode {
        PIPELINE(0), OFF(1), BLINK(2), ON(3);

        private int mValue;

        private LEDMode(int pValue) {
            mValue = pValue;
        }

        /**
         * @return The integer value used to represent each enum.
         */
        public int getValue() {
            return mValue;
        }
    }

    /**
     * Sets the LED Mode of the Limelight.
     * 
     * @param pMode the {@code LEDMode} to set the Limelight to.
     */
    public void setLEDMode(LEDMode pMode) {
        getEntryFromNetworkTable("ledMode").setNumber(pMode.getValue());
    }

    //*************//
    // CAMERA MODE //
    //*************//

    /**
     * An enum that represents the different modes of the Limelight's operation.
     * 
     * <p>
     * These are:
     * 
     * <ul>
     * <li>VISION_PROCESSOR: Standard operating mode.</li>
     * <li>DRIVER_CAMERA: Increases the exposure and turns off vision processing.
     * </li>
     * </ul>
     */
    public enum CamMode {
        VISION_PROCESSOR(0), DRIVER_CAMERA(1);

        private int mValue;

        private CamMode(int pValue) {
            mValue = pValue;
        }

        /**
         * @return The integer value used to represent each enum.
         */
        public int getValue() {
            return mValue;
        }
    }

    /**
     * Sets the operating mode of the Limelight.
     * 
     * @param pMode the {@code CamMode} to set the Limelight to.
     */
    public void setCamMode(CamMode pMode) {
        getEntryFromNetworkTable("camMode").setNumber(pMode.getValue());
    }

    //********//
    // STREAM //
    //********//

    /**
     * An enum that represents the different streaming modes of the Limelight.
     * 
     * <p>
     * These are:
     * 
     * <ul>
     * <li>STANDARD: The default side-by-side stream of the Limelight and secondary
     * camera feeds.</li>
     * <li>PIP_MAIN: Picture-in-Picture, secondary stream placed in bottom-right of
     * primary stream. Decreases bandwidth usage.</li>
     * <li>PIP_SECONDARY: Picture-in-Picture, primary stream placed in bottom-right
     * of secondary stream. Decreases bandwith usage.</li>
     * </ul>
     */
    public enum StreamMode {
        STANDARD(0), PIP_MAIN(1), PIP_SECONDARY(2);

        private int mValue;

        private StreamMode(int pValue) {
            mValue = pValue;
        }

        /**
         * @return The integer value used to represent each enum.
         */
        public int getValue() {
            return mValue;
        }
    }

    /**
     * Sets the streaming mode of the Limelight.
     * 
     * @param pMode The {@code StreamMode} to set the Limelight to.
     */
    public void setStreamMode(StreamMode pMode) {
        getEntryFromNetworkTable("stream").setNumber(pMode.getValue());
    }

    //**********//
    // PIPELINE //
    //**********//

    /**
     * Sets the Limelight to a given pipeline.
     * @param pPipeline Pipeline to set the Limelight to. Values from 0 to 9 are acceptable in the current software.
     */
    public void setPipeline(int pPipeline) {
        if (pPipeline <= CameraConstants.MAX_PIPELINE && pPipeline >= CameraConstants.MIN_PIPELINE) {
            getEntryFromNetworkTable("pipeline").setNumber(pPipeline);
        }
    }

    /**
     * @return The pipeline the Limelight is currently set to.
     */
    public double getPipeline() {
        return getDoubleFromNetworkTable("getpipe");
    }

    //**********//
    // SNAPSHOT //
    //**********//

    /**
     * Stops the Limelight from taking snapshots during the match.
     */
    public void stopSnapshot() {
        getEntryFromNetworkTable("snapshot").setNumber(0);
    }

    /**
     * Starts taking snapshots. Takes two snapshots per second.
     */
    public void startSnapshot() {
        getEntryFromNetworkTable("snapshot").setNumber(1);
    }

}
