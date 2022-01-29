package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ShuffBoard {
    public ShuffBoard() {}

    public void printShooterRPM( Shooter shooter ) {
        SmartDashboard.putNumber("ShooterRPM", Math.round(shooter.getBottom().getEncoder().getVelocity()));
    }
}
