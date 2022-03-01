package frc.robot.misc_subclasses;

// Imports
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.*;
import frc.robot.wrappers.GenericPID;

// This object is used to handle SmartDashboard outputs
public class Dashboard {
    // Constructor method
    public Dashboard() {}

    // Prints the rpm of a shooter object to a SmartDashboard
    public void printShooterRPM(Shooter shooter) {
        SmartDashboard.putNumber("ShooterRPM", Math.round(shooter.getTopMotorRPM()));
    }

    // Prints the current position of a turret
    public void printTurretPos(Turret turret) {
        SmartDashboard.putNumber("TurretPos", turret.getPosition());
    }

    // Prints relevant data from a PID controller. If a value is manually 
    // input into the dashboard, the pid setpoint will update accordingly
    public void PIDtoDashboard(GenericPID pid, int id) {
        double setpointD = SmartDashboard.getNumber("PID-" + id + " Setpoint", pid.getSetpoint());

        if( setpointD != pid.getSetpoint() )
            pid.setSetpoint(setpointD);

        SmartDashboard.putNumber("PID-" + id + " Setpoint", pid.getSetpoint());
        SmartDashboard.putNumber("PID-" + id + " RPM", pid.getRPM());
        SmartDashboard.putNumber("PID-" + id + " Position", pid.getPosition());
        SmartDashboard.putString("PID-" + id + " Domain", "[" + pid.getMin() + ", " + pid.getMax() + "]");
    }

    // Prints relevant data from a PID controller. If a value is manually 
    // input into the dashboard, the pid setpoint will update accordingly
    public void PIDtoDashboard(GenericPID pid, String name) {
        double setpointD = SmartDashboard.getNumber(name + " Setpoint", pid.getSetpoint());

        if( setpointD != pid.getSetpoint() )
            pid.setSetpoint(setpointD);
            
        SmartDashboard.putNumber(name + " Setpoint", pid.getSetpoint());
        SmartDashboard.putNumber(name + " RPM", pid.getRPM());
        SmartDashboard.putNumber(name + " Position", pid.getPosition());
        SmartDashboard.putString(name + " Domain", "[" + pid.getMin() + ", " + pid.getMax() + "]");
    }

    /* Prints the rpm of a shooter object to a SmartDashboard
     * Shooters have two motors, but we only need to print the RPM
     * of one motor, because they're locked together physically on the robot
     * However, when testing with only one motor, we want to make sure it 
     * isn't just printing the RPM of the motor that's disconnected (0 RPM)
     */
    public void testShooterRPM( Shooter shooter ) {
        // Make temporary variables to shorten code
        double topRPM = Math.round(shooter.getTopMotorRPM());
        double bottomRPM = Math.round(shooter.getBottomMotorRPM());

        // Logic to insure that we can test with only one motor
        if( topRPM == 0 && bottomRPM != 0 )
            SmartDashboard.putNumber("ShooterRPM", bottomRPM);
        else
            SmartDashboard.putNumber("ShooterRPM", topRPM);
    }
}
