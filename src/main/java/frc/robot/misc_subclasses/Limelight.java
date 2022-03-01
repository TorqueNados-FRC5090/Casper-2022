
package frc.robot.misc_subclasses;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Limelight {
    // Declare variables
    private double distance;

    // Constants
    private final double dr = 0.017453292519943295; // Degrees to Rads
    private final int h1 = 39; // Height of limelight off the ground in inches
    private final int h2 = 104; // Height of target in inches
    private final double a1 = 45 * dr; // Angle of limelight relative to the floor

    // These numbers must be tuned for your robot, be careful!!!
    private final double DESIRED_TARGET_AREA = 1.5;      // Area of the target when the robot reaches the wall
    private final double DRIVE_K = 0.36;                 // How fast to drive toward the target
    private final double STEER_K = 0.05;                 // How quickly the robot turns toward the target
    private final double MAX_DRIVE = 0.5;                // Simple speed limit so we don't drive too fast

    private double a2; // Angle difference between limelight and target (height-wise)
    private double tv;
    private double tx; // Rotational angle from limelight to target
    private double ta;

    private SendableChooser<String> chooser;
    public boolean hasValidTarget = false;
    public double driveCommand;
    public double steerCommand;

    // Constructor
    public Limelight() {
        chooser = new SendableChooser<>();
        chooser.setDefaultOption("Default Auto", "Default");
        chooser.addOption("My Auto", "My Auto");
        SmartDashboard.putData("Auto choices", chooser);
    }

    // Accessor methods
    public boolean hasTarget() { return this.hasValidTarget; }
    public double getDistance() { return this.distance; }
    public double getSteer() { return this.steerCommand; }
    public double getDrive() { return this.driveCommand; }
    public double getRotationAngle() { return this.tx; }

    // Finds values for limelight drive and pastes to a data table
    public void updateLimelightTracking()
    {
        tv = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);
        tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
        ta = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta").getDouble(0);
        a2 = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0) * dr;

        // Determines if limelight has a target that is valid
        if ( tv < 1 ) {
            hasValidTarget = false;
            driveCommand = 0;
            steerCommand = 0;
            return;
        }

        hasValidTarget = true;
        // Start with proportional steering
        steerCommand = tx * STEER_K;
        // Drive forward until the target area reaches our desired area
        driveCommand = (DESIRED_TARGET_AREA - ta) * DRIVE_K;
        // Don't let the robot drive too fast into the goal!!!!
        if( driveCommand > MAX_DRIVE )
            driveCommand = MAX_DRIVE;

        distance = Math.round((h2-h1)/Math.tan(a1+a2));
    }
}