/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package constants;

/**
 * Constants for the Rev Robotics Blinkin LED Driver patterns. 
 * 
 * <p>
 * The Blinkin is controlled through the PWM signal, and is represented as a motor controller of type Spark in the code.
 * This is not to be confused with the Spark Motor Controller class in the Rev Robotics API.
 * 
 * <p>
 * The constants in this class are PWM values which correspond to patterns that are displayed when the motor object 
 * is set to their value. The system colors, brightness, LED strip type, and default mode are selected via hardware.
 * Values can be found here: http://www.revrobotics.com/content/docs/REV-11-1105-UM.pdf
 * 
 * @author Tal Zussman
 * @author Daniel Chao
 */
public class BlinkinConstants {

    public static final double
        RAINBOW_RAINBOW_PALETTE = -0.99,
        RAINBOW_PARTY_PALETTE = -0.97,
        RAINBOW_OCEAN_PALETTE = -0.95,
        RAINBOW_LAVA_PALETTE = -0.93,
        RAINBOW_FOREST_PALETTE = -0.91,
        RAINBOW_WITH_GLITTER = -0.89,
        CONFETTI = -0.87,
        SHOT_RED = -0.85,
        SHOT_BLUE = -0.83,
        SHOT_WHITE = -0.81,
        SINELON_RAINBOW = -0.79,
        SINELON_PARTY = -0.77,
        SINELON_OCEAN = -0.75,
        SINELON_LAVA = -0.73,
        SINELON_FOREST = -0.71,
        BPM_RAINBOW = -0.69,
        BPM_PARTY = -0.67,
        BPM_OCEAN = -0.65,
        BPM_LAVA = -0.63,
        BPM_FOREST = -0.61,
        FIRE_MEDIUM = -0.59,
        FIRE_LARGE = -0.57,
        TWINKLES_RAINBOW = -0.55,
        TWINKLES_PARTY = -0.53,
        TWINKLES_OCEAN = -0.51,
        TWINKLES_LAVA = -0.49,
        TWINKLES_FOREST = -0.47,
        COLOR_WAVES_RAINBOW = -0.45,
        COLOR_WAVES_PARTY = -0.43,
        COLOR_WAVES_OCEAN = -0.41,
        COLOR_WAVES_LAVA = -0.39,
        COLOR_WAVES_FOREST = -0.37,
        LARSON_SCANNER_RED = -0.35,
        LARSON_SCANNER_GRAY = -0.33,
        LIGHT_CHASE_RED = -0.31,
        LIGHT_CHASE_BLUE = -0.29,
        LIGHT_CHASE_GRAY = -0.27,
        HEARTBEAT_RED = -0.25,
        HEARTBEAT_BLUE = -0.23,
        HEARTBEAT_WHITE = -0.21,
        HEARTBEAT_GRAY = -0.19,
        BREATH_RED = -0.17,
        BREATH_BLUE = -0.15,
        BREATH_GRAY = -0.13,
        STROBE_RED = -0.11,
        STROBE_BLUE = -0.09,
        STROBE_GOLD = -0.07,
        STROBE_WHITE = -0.05,
        BLEND_TO_BLACK_1 = -0.03,
        LARSON_SCANNER_1 = -0.01,
        LIGHT_CHASE_1 = 0.01,
        HEARTBEAT_SLOW_1 = 0.03,
        HEARTBEAT_MEDIUM_1 = 0.05,
        HEARTBEAT_FAST_1 = 0.07,
        BREATH_SLOW_1 = 0.09,
        BREATH_FAST_1 = 0.11,
        SHOT_1 = 0.13,
        STROBE_1 = 0.15,
        BLEND_TO_BLACK_2 = 0.17,
        LARSON_SCANNER_2 = 0.19,
        LIGHT_CHASE_2 = 0.21,
        HEARTBEAT_SLOW_2 = 0.23,
        HEARTBEAT_MEDIUM_2 = 0.25,
        HEARTBEAT_FAST_2 = 0.27,
        BREATH_SLOW_2 = 0.29,
        BREATH_FAST_2 = 0.31,
        SHOT_2 = 0.33,
        STROBE_2 = 0.35,
        SPARKLE_1_ON_2 = 0.37,
        SPARKLE_2_ON_1 = 0.39,
        COLOR_GRADIENT = 0.41,
        BPM = 0.43,
        END_TO_END_1_TO_2 = 0.45,
        END_TO_END = 0.47,
        NO_BLEND_1_AND_2 = 0.49,
        TWINKLES = 0.51,
        COLOR_WAVES = 0.53,
        SINELON = 0.55,
        HOT_PINK = 0.57,
        DARK_RED = 0.59,
        RED = 0.61,
        RED_ORANGE = 0.63,
        ORANGE = 0.65, 
        GOLD = 0.67,
        YELLOW = 0.69,
        LAWN_GREEN = 0.71,
        LIME = 0.73,
        DARK_GREEN = 0.75,
        GREEN = 0.77,
        BLUE_GREEN = 0.79,
        AQUA = 0.81,
        SKY_BLUE = 0.83,
        DARK_BLUE = 0.85,
        BLUE = 0.87,
        BLUE_VIOLET = 0.89,
        VIOLET = 0.91,
        WHITE = 0.93,
        GRAY = 0.95,
        DARK_GRAY = 0.97,
        BLACK = 0.99;
}
