package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;


public class Climber {

    private CANSparkMax climberMotor1, climberMotor2;

    public Climber (int motorID1, int motorID2) {
        
        climberMotor1 = new CANSparkMax(motorID1, MotorType.kBrushless);
        climberMotor2 = new CANSparkMax(motorID2, MotorType.kBrushless);
    }

    public double getLeftArmPower () {
        return climberMotor1.get();
    }

    public double getRightArmPower () {
        return climberMotor2.get();
    }

    public void setLeftArmPower (double pwr) {
        climberMotor1.set(pwr);
    }

    public void setRightArmPower (double pwr) {
        climberMotor2.set(pwr);
    }

    public void setPower (double pwr) {
        climberMotor1.set(pwr);
        climberMotor2.set(pwr);
    }

    public void off () {
        climberMotor1.set(0);
        climberMotor2.set(0);
    }
}
