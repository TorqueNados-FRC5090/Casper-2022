// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    // How many times the turret's motor needs to rotate to turn the turret 1 degree
    public static final double TURRET_RATIO = 2.3737373737;

    // Limelight calculation numbers
    public static final int LIME_HEIGHT = 39; // Height of limelight off the ground in inches
    public static final int TARGET_HEIGHT = 104; // Height of target in inches
    public static final double LIME_ANGLE = Math.toRadians(51); // Angle of limelight relative to the floor

    // Limelight tuning numbers
    public static final double DESIRED_TARGET_AREA = 1.5;  // Area of the target when the robot reaches the wall
    public static final double DRIVE_K = 0.36;             // How fast to drive toward the target
    public static final double STEER_K = 0.05;             // How quickly the robot turns toward the target
    public static final double MAX_DRIVE = 0.5;            // Simple speed limit so we don't drive too fast
}
