/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

/*****************************************************************
* Wrapper class for an IR sensor implementation of an analog input
* Contains a low pass filter
/*****************************************************************/

    package sensors;

    import edu.wpi.first.wpilibj.AnalogInput;

    public class InfraredSensor extends AnalogInput {

        private long mTimeLastChanged;
        private boolean mActualValue;
        private boolean mReturnedValue;
        private boolean mKeepingTrackValue;
        
        public static final long TIME_THRESHOLD = 500; //temporary
        public static final double DISTANCE_THRESHOLD = 4000; //temporary, check specs for voltage/distance graph

        public InfraredSensor(int pPort) {
            super(pPort);
            mActualValue = getRaw();
            mReturnedValue = getRaw();
            mKeepingTrackValue = mActualValue;
            mTimeLastChanged = System.currentTimeMillis();
        }

        public boolean getCooked() {
            mActualValue = this.getRaw();

            if(mActualValue != mKeepingTrackValue){
                mKeepingTrackValue = mActualValue;
                mTimeLastChanged = System.currentTimeMillis();
            }

            if(mActualValue != mReturnedValue && System.currentTimeMillis() - mTimeLastChanged > TIME_THRESHOLD){
                mReturnedValue = mActualValue;
                mKeepingTrackValue = mActualValue;
            }

            return mReturnedValue;
        }

        private boolean getRaw(){
            return this.getValue() > DISTANCE_THRESHOLD;
        }

    }
