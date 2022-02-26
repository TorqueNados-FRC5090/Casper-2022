package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

// Controls the climber
public class Climber {

    // Declare motors
    private CANSparkMax leftMotor;
    private CANSparkMax rightMotor;

    // Constructor
    public Climber (int motorID1, int motorID2) {
        
        // Initialize motors
        leftMotor = new CANSparkMax(motorID1, MotorType.kBrushless);
        rightMotor = new CANSparkMax(motorID2, MotorType.kBrushless);
    }

    // Accessor methods
    public double getLeft() { return leftMotor.get(); }
    public double getRight() { return rightMotor.get(); }

    // Set either of the arms separately
    public void setLeft(double pwr) { leftMotor.set(pwr); }
    public void setRight(double pwr) { rightMotor.set(pwr); }

    // Set the power of the motors
    public void set(double pwr) {
        leftMotor.set(pwr);
        rightMotor.set(pwr);
    }

    // Stop the climber
    public void off() {
        leftMotor.set(0);
        rightMotor.set(0);
    }
}
