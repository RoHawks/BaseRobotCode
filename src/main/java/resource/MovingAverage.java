/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package resource;

/*
 * package com.team254.lib.util;
 */

import java.util.ArrayList;

/**
 * Helper class for storing and calculating a moving average.
 * 
 * <p> Modified from Team 254's implementation.
 * 
 * @author Team 254
 * @author Tal Zussman
 * @author Daniel Chao
 */

public class MovingAverage {

    /* We use an ArrayList instead of an Array since an ArrayList allows for easy deletion and subsequent replacement
    of values, maintaining the order of addition without requiring significant shifting of indices. */

    private ArrayList<Double> mNumbers;
    private int mSize;

    /**
     * @param pMaxSize maximum number of values in the {@code MovingAverage}.
     */
    public MovingAverage(int pMaxSize) {
        this.mSize = pMaxSize;
        mNumbers = new ArrayList<Double>(pMaxSize);
    }

    /**
     * Adds given number to the average, and if necessary, removes the oldest value
     * in the average.
     * 
     * @param pNewNumber number to add to the average.
     */
    public void addNumber(double pNewNumber) {
        mNumbers.add(pNewNumber);
        if (mNumbers.size() > mSize) {
            mNumbers.remove(0);
        }
    }

    /**
     * Returns moving average of the last {@code mSize} values.
     * 
     * @return average of values in the {@code ArrayList}.
     */
    public double getAverage() {
        double total = 0;

        for (double number : mNumbers) {
            total += number;
        }

        // Cheesy ignored the case where mNumbers.size() is zero
        // We account for this by checking the size of the ArrayList beforehand
        return mNumbers.size() == 0 ? 0 : total / mNumbers.size();
    }

    /**
     * Getter for the size of the MovingAverage ArrayList
     * 
     * @return size of the ArrayList
     */
    public int getSize() {
        return mNumbers.size();
    }

    /**
     * Checks whether the average contains less than the maximum number of values
     * 
     * @return if the size of the ArrayList is below the given maximum size
     */
    public boolean isUnderMaxSize() {
        return getSize() < mSize;
    }

    /**
     * Clears the {@code MovingAverage} ArrayList
     */
    public void clear() {
        mNumbers.clear();
    }

}
