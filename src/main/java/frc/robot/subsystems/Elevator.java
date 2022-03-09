package frc.robot.subsystems;

// Imports
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import frc.robot.wrappers.LaserDetector;

public class Elevator {

    // Declare variables
    private LaserDetector topSensor;
    private LaserDetector bottomSensor;

    private CANSparkMax motor; 
    private boolean bottomSensorHasBall;
    private boolean topSensorHasBall;

    // Constructor 
    public Elevator(int motorID, int bottomSensorID, int topSensorID) {
        // Initialize variables
        motor = new CANSparkMax(motorID, MotorType.kBrushless);
        motor.setInverted(true);

        //CHANGE TO THE RIGHT PORT 
        topSensor = new LaserDetector(bottomSensorID);
        bottomSensor = new LaserDetector(topSensorID);
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

    // Ejects a ball at half speed
    public void ejectBall() { set(-0.5); }

    public void updateElevator() {
        bottomSensorHasBall = bottomSensor.isBlocked();
        topSensorHasBall = topSensor.isBlocked();
        
        if(topSensorHasBall){
            off();
        }else if(bottomSensorHasBall){
            set(0.5);
        }else{
            off();
        }
    }

    public void shoot() { 
        fullForward();
    }
}
