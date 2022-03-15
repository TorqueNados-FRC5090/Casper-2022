package frc.robot.subsystems;

// Imports
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

// This object is used to control the shooter
public class Shooter {
    // Declare all shooter variables 
    private CANSparkMax topMotor; // CW
    private CANSparkMax bottomMotor; // CCW
    private boolean shooterIsOn;
    private boolean locked;
    private double currentPower;
  
  // Constructor method initiallizes variables used
  public Shooter( int topID, int bottomID) {
    // Initiallize top motor
    topMotor = new CANSparkMax(topID, MotorType.kBrushless);
    topMotor.restoreFactoryDefaults();

    // Initialize bottom motor
    // Bottom motor is inverted so that it will
    // always spin opposite to the top motor
    bottomMotor = new CANSparkMax(bottomID, MotorType.kBrushless);
    bottomMotor.restoreFactoryDefaults();
    bottomMotor.setInverted(true);

    // Shooter starts in the 'off' state
    shooterIsOn = false;
    // Shooter is unlocked by default
    locked = false;
  }

  // Accessor Methods (getters)
  public CANSparkMax getTopMotor() { return topMotor; }
  public CANSparkMax getBottomMotor() { return bottomMotor; }
  public double getTopMotorRPM() { return topMotor.getEncoder().getVelocity(); }
  public double getBottomMotorRPM() { return bottomMotor.getEncoder().getVelocity(); }
  public boolean isOn() { return shooterIsOn; }
  public boolean isLocked() { return locked; }
  public double getCurrentPower() { return currentPower; }

  // Makes sure that current power is accurate
  public void updateCurrentPower() { currentPower = topMotor.get(); }

  // Tracks the power of the motor and
  // locks the power at the highest value detected
  public void setLock(boolean locked) { this.locked = locked; }

  // Sets shooter to specified power
  // Power is locked to [-1, 1]
  public void set( double pwr ) {
    // If the lock is on and the desired power
    // is not greater than the current power,
    // the function immediately ends
    if(locked && pwr <= topMotor.get())
      return; 

    // Set motors
    topMotor.set(pwr);
    bottomMotor.set(pwr);

    // Update shooter state
    if( pwr == 0 ) { shooterIsOn = false; }
    else { shooterIsOn = true; }
  }

  public void increasePowerBy(double pwr) {
    currentPower = currentPower + pwr;
    set(currentPower);
  }

  public void decreasePowerBy(double pwr) {
    currentPower = currentPower - pwr;
    set(currentPower);
  }

  // Sets shooter to full power
  public void fullPower() {
    topMotor.set(1);
    bottomMotor.set(1);
    shooterIsOn = true;
  }

  // Turns shooter off
  public void off(){
    topMotor.set(0);
    bottomMotor.set(0);
    shooterIsOn = false;
  }

  // If shooter is off, this will turn it on
  // If shooter is on, this will turn it off
  public void toggle() {
    if( shooterIsOn ) { off(); }
    else { fullPower(); }
  }

  // If shooter is off, this will set it to
  // the specified power
  // If shooter is on, this will turn it off
  public void toggle( double pwr ) {
    if( shooterIsOn ) { off(); }
    else { set(pwr); }
  }
}
