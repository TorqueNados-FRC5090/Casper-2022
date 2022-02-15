package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Intake {
    //This tells us if the arms are up 
    boolean solenoidIsUp;

    //This is used to make sure that we don't turn on intake when the arms are up
    boolean disableMotor;

    //Defines the solenoid and motor 
    private DoubleSolenoid dubs1;
    private CANSparkMax motor;

    //We tell it which motor is for intake 
    public Intake(int motorID){
        solenoidIsUp = false;
        disableMotor = true;

        dubs1 = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 0, 1);
        motor = new CANSparkMax(motorID, MotorType.kBrushless);
    }

    //This makes the arms move up and down. 
    public void toggleArms(){
        if(solenoidIsUp){
            dubs1.set(DoubleSolenoid.Value.kReverse);
            solenoidIsUp = false;
            disableMotor = true;
        }else{
          dubs1.set(DoubleSolenoid.Value.kForward);
          solenoidIsUp =  true;
          disableMotor = false;
        }
    }

    //This turns on the motor for intake 
    public void setMotor(double motorDirection){   
      if(disableMotor == false){
        motor.set(motorDirection);

      }
    }

}
