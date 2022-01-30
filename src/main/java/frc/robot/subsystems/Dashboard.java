package frc.robot.subsystems;

// Imports
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// This object is used to handle SmartDashboard outputs
public class Dashboard {
    // Constructor method
    public Dashboard() {}

    // Prints the rpm of a shooter object
    // Shooters have two motors, but we only need to print the RPM
    // of one motor, because they're locked together physically on the robot
    public void printShooterRPM( Shooter shooter ) {
        SmartDashboard.putNumber("ShooterRPM", Math.round(shooter.getBottom().getEncoder().getVelocity()));
    }
}
