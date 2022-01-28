package frc.robot.subsystems;

// Imports
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

// This object is used to control the shooter
public class Shooter {
    // Declare all shooter variables 
    private CANSparkMax m_shooterFlywheelTop;
    private CANSparkMax m_shooterFlywheelBottom;
    private boolean shooterIsOn;
  
  // Constructor method initiallizes variables used
  public Shooter( int topID, int bottomID) {
    // Initiallize top motor
    this.m_shooterFlywheelTop = new CANSparkMax(topID, MotorType.kBrushless);
    this.m_shooterFlywheelTop.restoreFactoryDefaults();

    // Initialize bottom motor
    // Bottom motor is inverted so that it will
    // always spin opposite to the top motor
    this.m_shooterFlywheelBottom = new CANSparkMax(bottomID, MotorType.kBrushless);
    this.m_shooterFlywheelBottom.restoreFactoryDefaults();
    this.m_shooterFlywheelBottom.setInverted(true);

    // Shooter starts in the 'off' state
    this.shooterIsOn = false;
  }

  // Power is locked to [-1, 1]
  public void setPower( double pwr ) {

    // Force pwr in bounds
    if( pwr > 1 ) { pwr = 1; }
    else if( pwr < -1 ) { pwr = -1; }

    // Set motors
    m_shooterFlywheelTop.set(pwr);
    m_shooterFlywheelBottom.set(pwr);

    // Update shooter state
    if( pwr == 0 ) { shooterIsOn = false; }
    else { shooterIsOn = true; }
  }

  // Sets shooter to full power
  public void fullTilt() {
    m_shooterFlywheelTop.set(1);
    m_shooterFlywheelBottom.set(1);
    shooterIsOn = true;
  }

  // Turns shooter off
  public void off(){
    m_shooterFlywheelTop.set(0);
    m_shooterFlywheelBottom.set(0);
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