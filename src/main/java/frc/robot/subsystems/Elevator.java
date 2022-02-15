package frc.robot.subsystems;

//These are the libraries we will use 
import edu.wpi.first.wpilibj.AnalogInput;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.RobotController;


public class Elevator {

    //Sets up the sensors   
    public final AnalogInput bottomSensor;
    public AnalogInput topSensor; 

    //Sets up the motor 
    private CANSparkMax motor; 

    boolean motorOn; 

    //Holds on to the distance from the sensor
    double sensor1Distance;

    boolean bottomSensorHasBall;
    boolean topSensorHasBall;

    //Tells which motor & sensor we are going to use for the elevator  
    public Elevator(int motorID){
        motor = new CANSparkMax(motorID, MotorType.kBrushless);
        bottomSensor = new AnalogInput(0);
        topSensor = new AnalogInput(2);

        motorOn = true;     
    }

    public boolean topSensorHasBall() { return topSensorHasBall; }
    public boolean bottomSensorHasBall() { return bottomSensorHasBall; }

    //Turns on the motor 
    public void on(){
        motor.set(0.5);
    }
    //Turns off the motor
    public void off(){
        motor.set(0);
    }

    //This finds the distance between sensor and ball
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

    //If the shooter motor is up to the correct rpm, then fire the ball 
    //Paramaters- "rpm" is the rpm we want. "shooter" is the motor we are going to get the rpm from. 
    public void shootCheck(int rpm, Shooter shooter){
        if(Math.abs(rpm-shooter.getTopMotorRPM()) > 100){
            off();
        }else{
            on();
        }
    }
    
    //This checks if the elevator has a ball in it or not 
    public void updateElevator(){
        if(findDistanceCm(bottomSensor) < 35)
            bottomSensorHasBall = true;
        else
            bottomSensorHasBall = false; 

        if(findDistanceCm(topSensor) < 35)
            topSensorHasBall = true;
        else
            topSensorHasBall = false; 
        


        if(bottomSensorHasBall == false && topSensorHasBall == false){
            on();
        }else if(bottomSensorHasBall && topSensorHasBall == false){
            off();
            //Tell dashboard to prep
        }else {
            off();
            //Tell dashboard ready to fire
        }
    }





}
