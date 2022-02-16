package frc.robot.subsystems;

// Imports
import edu.wpi.first.wpilibj.AnalogInput;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.RobotController;

public class Elevator {

    // Declare variables
    private AnalogInput bottomSensor;
    private AnalogInput topSensor; 
    private CANSparkMax motor; 
    private boolean bottomSensorHasBall;
    private boolean topSensorHasBall;

    // Constructor 
    public Elevator(int motorID) {
        // Initialize variables
        motor = new CANSparkMax(motorID, MotorType.kBrushless);
        bottomSensor = new AnalogInput(0);
        topSensor = new AnalogInput(2);
    }

    // Accessor methods
    public boolean topSensorHasBall() { return topSensorHasBall; }
    public boolean bottomSensorHasBall() { return bottomSensorHasBall; }

    // Turns on the motor 
    public void on() { motor.set(0.5); }
    // Turns off the motor
    public void off() { motor.set(0); }

    // Returns the distance between sensor and ball in centimeters
    public double findDistance(AnalogInput sensor) {
        double rawValue = sensor.getValue();
    
        // voltageScaleFactor allows us to compensate for differences in supply voltage
        double voltageScaleFactor = 5/RobotController.getVoltage5V();
        return rawValue * voltageScaleFactor * 0.125;
    }
    
    // Updates the state of the elevator's sensors
    public void updateElevator() {
        // Checks each of the sensors for a ball
        if(findDistance(bottomSensor) < 35)
            bottomSensorHasBall = true;
        else
            bottomSensorHasBall = false; 

        if(findDistance(topSensor) < 35)
            topSensorHasBall = true;
        else
            topSensorHasBall = false; 
        
        // Decides what to do for each of the possible sensor states
        if(!bottomSensorHasBall && !topSensorHasBall)
            on();
        else if(bottomSensorHasBall && !topSensorHasBall) {
            off();
            // Tell dashboard: ready to prep
        }
        else {
            off();
            // Tell dashboard: ready to fire
        }
    }
}
