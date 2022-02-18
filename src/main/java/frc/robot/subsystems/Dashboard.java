package frc.robot.subsystems;

// Imports
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// This object is used to handle SmartDashboard outputs
public class Dashboard {
    // Constructor method
    public Dashboard() {}

    // Prints the rpm of a shooter object to a SmartDashboard
    public void printShooterRPM( Shooter shooter ) {
        SmartDashboard.putNumber("ShooterRPM", Math.round(shooter.getTopMotor().getEncoder().getVelocity()));
    }

    // Prints the status of the elevator to the dashboard via two
    // booleans that return true when a ball is present
    public void printBallStatus(Elevator elevator){
        SmartDashboard.putBoolean("Top Sensor", elevator.topSensorHasBall());
        SmartDashboard.putBoolean("Bottom Sensor", elevator.bottomSensorHasBall());
    }

    /* Prints the rpm of a shooter object to a SmartDashboard
     * Shooters have two motors, but we only need to print the RPM
     * of one motor, because they're locked together physically on the robot
     * However, when testing with only one motor, we want to make sure it 
     * isn't just printing the RPM of the motor that's disconnected (0 RPM)
     */
    public void testShooterRPM( Shooter shooter ) {
        // Make temporary variables to shorten code
        double topRPM = Math.round(shooter.getTopMotor().getEncoder().getVelocity());
        double bottomRPM = Math.round(shooter.getBottomMotor().getEncoder().getVelocity());

        // Logic to insure that we can test with only one motor
        if( topRPM == 0 && bottomRPM != 0 )
            SmartDashboard.putNumber("ShooterRPM", bottomRPM);
        else
            SmartDashboard.putNumber("ShooterRPM", topRPM);
    }
}
