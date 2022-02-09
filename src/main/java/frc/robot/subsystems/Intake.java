package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Intake {
    boolean solenoidIsUp;
    boolean disableMotor;

    private DoubleSolenoid dubs1;
    private CANSparkMax motor;

    public Intake(int motorID){
        solenoidIsUp = false;
        disableMotor = true;

        dubs1 = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 0, 1);
        motor = new CANSparkMax(motorID, MotorType.kBrushless);
    }

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

    public void setMotor(double motorDirection){   
      if(disableMotor == false){
        motor.set(motorDirection);

      }
    }

}
