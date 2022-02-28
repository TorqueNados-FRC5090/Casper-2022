package frc.robot.subsystems;

// Imports
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

// Controls the hood of the shooter
public class Hood {
    CANSparkMax motor;

    // Constructor
    public Hood(int motorID) {
        motor = new CANSparkMax(motorID, MotorType.kBrushless);
    }

    // Accessors
    public double getRPM() { return motor.getEncoder().getVelocity(); }
    public double getPosition() { return motor.getEncoder().getPosition(); }
    public CANSparkMax getMotor() { return motor; }

    // Sets motor to specified power
    public void setPower(double pwr) { motor.set(pwr); }

    // Stops the hood
    public void off() { motor.set(0); }
}