package frc.robot.subsystems;

// Imports
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Intake {
  // Variable Declaration
  private DoubleSolenoid dubs;
  private CANSparkMax motor;
  private double motorPower;

  // Is true if the arms are up 
  // Motor should NOT move if this is true
  private boolean solenoidIsUp;

  // Constructor
  public Intake(int motorID, double motorPower) {
    // Initialize Variables
    this.dubs = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 0, 1);

    this.motor = new CANSparkMax(motorID, MotorType.kBrushless);
    this.motorPower = motorPower;

    // Arms start in the up position
    this.solenoidIsUp = true;
  }

  // Accessor methods
  public boolean armsAreUp() { return this.solenoidIsUp; }
  public double getMotorPower() { return this.motorPower; }
  public int getMotorRPM() { return (int) Math.round(this.motor.getEncoder().getVelocity()); }

  // Put arms up
  public void up() { dubs.set(DoubleSolenoid.Value.kForward); }

  // Put arms down
  public void down() { dubs.set(DoubleSolenoid.Value.kReverse); }

  // Turn motor on
  public void motorOn() { if(!solenoidIsUp) motor.set(this.motorPower); }

  // Turn the motor on and override the default power
  public void motorSet(double power) { if(!solenoidIsUp) motor.set(power); }

  // Turn motor off
  public void motorOff() { motor.set(0); }

  // Change the power that the motor gets set to
  public void setMotorPower(double motorPower) { this.motorPower = motorPower; }

  // If arms are up, they will move down
  // If arms are down they will move up
  public void toggleArms() {
    if(solenoidIsUp){
      dubs.set(DoubleSolenoid.Value.kReverse);
      solenoidIsUp = false;
    }
    else {
      dubs.set(DoubleSolenoid.Value.kForward);
      solenoidIsUp = true;
    }
  }
}
