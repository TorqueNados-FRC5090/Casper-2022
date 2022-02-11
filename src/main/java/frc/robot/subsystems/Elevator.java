package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogInput;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.RobotController;

public class Elevator {

    public final AnalogInput sensor1;
    public AnalogInput sensor2; 

    private CANSparkMax motor; 

    boolean motorOn; 
    boolean aButtonNotPressed;

    double sensor1Distance;
    double sensor2Distance;

    public Elevator(int motorID){
        motor = new CANSparkMax(motorID, MotorType.kBrushless);
        sensor1 = new AnalogInput(0);
        sensor2 = new AnalogInput(2);

        motorOn = true;     
        aButtonNotPressed = true; 
    }

    //Turns on the motor 
    public void on(){
        motor.set(0.5);
    }
    //Turns off the motor
    public void off(){
        motor.set(0);
    }

    double findDistanceCm(AnalogInput sensor){
        //Stuff from the MaxBotix website 
        //final AnalogInput ultrasonic = new AnalogInput(0);
        double rawValue = sensor.getValue();
    
        //voltage_scale_factor allows us to compensate for differences in supply voltage.
        double voltage_scale_factor = 5/RobotController.getVoltage5V();
        double currentDistanceCentimeters = rawValue * voltage_scale_factor * 0.125;
        //double currentDistanceInches = rawValue * voltage_scale_factor * 0.0492;
        return currentDistanceCentimeters;
    }

    //"rpm" is the rpm we want. "shooter" is the rpm the motor is at currently. 
    public void shootCheck(int rpm, Shooter shooter){
        if(Math.abs(rpm-shooter.getTopMotorRPM()) > 100){
            off();
        }else{
            on();
        }
    }
    
    public void updateElevator(){
        sensor1Distance = findDistanceCm(sensor1);
        sensor2Distance = findDistanceCm(sensor2);

        //If both sensors are detect nothing, the motors are on
        if(sensor1Distance > 35 && sensor2Distance > 35){
            on();

         //If bottom sensor detects a ball while top sensor doesn't, turn off motor    
        }else if(sensor1Distance > 35 && sensor2Distance < 35){
            off();
            //Desplay ball is ready in dashboard. 

         //If top sensor detects a ball while bottom sensor doesn't, wait for the shooter button to be pressed     
        }else{
            //For shootCheck parameters, we need to get it from the dashboard.   
            //shootCheck(rpm, shooter);
        }



    }



}
