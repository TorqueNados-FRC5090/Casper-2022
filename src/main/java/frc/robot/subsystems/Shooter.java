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
  
  // Constructor method initiallizes variables used
  public Shooter( int topID, int bottomID) {
    // Initiallize top motor
    this.topMotor = new CANSparkMax(topID, MotorType.kBrushless);
    this.topMotor.restoreFactoryDefaults();

    // Initialize bottom motor
    // Bottom motor is inverted so that it will
    // always spin opposite to the top motor
    this.bottomMotor = new CANSparkMax(bottomID, MotorType.kBrushless);
    this.bottomMotor.restoreFactoryDefaults();
    this.bottomMotor.setInverted(true);

    // Shooter starts in the 'off' state
    this.shooterIsOn = false;
  }

  // Accessor Methods (getters)
  public CANSparkMax getTopMotor() {
    return this.topMotor;
  }
  public CANSparkMax getBottomMotor() {
    return this.bottomMotor;
  }
  
  // Returns true if the shooter is on
  public boolean isOn() { return this.shooterIsOn; }

  // Sets shooter to specified power
  // Power is locked to [-1, 1]
  public void setPower( double pwr ) {

    // Force pwr in bounds
    if( pwr > 1 ) { pwr = 1; }
    else if( pwr < -1 ) { pwr = -1; }

    // Set motors
    topMotor.set(pwr);
    bottomMotor.set(pwr);

    // Update shooter state
    if( pwr == 0 ) { shooterIsOn = false; }
    else { shooterIsOn = true; }
  }

  // Sets shooter to full power
  public void fullTilt() {
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
    if( shooterIsOn ) { this.off(); }
    else { this.fullTilt(); }
  }

  // If shooter is off, this will set it to
  // the specified power
  // If shooter is on, this will turn it off
  public void toggle( double pwr ) {
    if( shooterIsOn ) { this.off(); }
    else { this.setPower(pwr); }
  }
}