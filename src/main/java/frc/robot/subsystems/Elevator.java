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
    public Elevator(int motorID, int bottomSensorID, int topSensorID) {
        // Initialize variables
        motor = new CANSparkMax(motorID, MotorType.kBrushless);
        motor.setInverted(true);

        bottomSensor = new AnalogInput(bottomSensorID);
        topSensor = new AnalogInput(topSensorID);
    }

    // Accessor methods
    public boolean topSensorHasBall() { return topSensorHasBall; }
    public boolean bottomSensorHasBall() { return bottomSensorHasBall; }

    // Sets the motor to full speed
    public void fullForward() { motor.set(1); }

    // Sets the motor to full speed in reverse
    public void fullBackward() { motor.set(-1); }

    // Turns on the motor 
    public void set(double pwr) { motor.set(pwr); }

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
    }
}
