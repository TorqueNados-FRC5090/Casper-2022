package frc.robot;

// Controller Imports
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.Joystick;

// Actuation imports (Motors, Compressors, etc.)
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.wrappers.GenericPID;
import frc.robot.wrappers.LimitSwitch;
import com.revrobotics.CANSparkMax.ControlType;

// Camera imports
import edu.wpi.first.cameraserver.CameraServer;

// Subsystem imports
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Turret;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Hood;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Climber;

// Misc imports
import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.misc_subclasses.Dashboard;
import frc.robot.misc_subclasses.Limelight;
import static frc.robot.Constants.*; 



/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  // Controller ojects
  private Joystick joystick; 
  private XboxController xbox;

  // Subsystem objects
  private Shooter shooter;
  private Drivetrain drivetrain;
  private Dashboard dashboard;
  private Limelight limelight;
  private Elevator elevator; 
  private Intake intake;
  private Climber climber;
  private Turret turret;
  private Hood hood;

  // Misc variables/objects
  private DifferentialDrive m_myRobot;
  private Compressor comp;
  private LimitSwitch leftClimberSwitch;
  private LimitSwitch rightClimberSwitch;
  private LimitSwitch leftTurretSwitch;
  private LimitSwitch rightTurretSwitch;
  private LimitSwitch hoodZeroSwitch;
  private GenericPID turretPID;
  
  // This function is run when the robot is first started up and should be used
  // for any initialization code.
  @Override
  public void robotInit() {
    // Initialize variables
    joystick = new Joystick(0);
    xbox  = new XboxController(1);
  
    drivetrain = new Drivetrain(7, 3, 6, 2);
    m_myRobot = new DifferentialDrive(
      drivetrain.getLeftMotorGroup(), drivetrain.getRightMotorGroup());

    CameraServer.startAutomaticCapture();
    limelight = new Limelight();

    turret = new Turret(14);
    turretPID = new GenericPID(turret.getMotor(), ControlType.kPosition, .25);
    leftTurretSwitch = new LimitSwitch(4);
    rightTurretSwitch = new LimitSwitch(5);

    shooter = new Shooter(5, 9);    

    hood = new Hood(15);
    hoodZeroSwitch = new LimitSwitch(6);

    elevator = new Elevator(13, 0, 1);

    intake = new Intake(10);
    comp = new Compressor(0, PneumaticsModuleType.CTREPCM);

    climber = new Climber(11, 12);
    leftClimberSwitch = new LimitSwitch(2);
    rightClimberSwitch = new LimitSwitch(3);

    dashboard = new Dashboard();
  }

  @Override
  public void robotPeriodic() {
    // Runs the Scheduler. This is responsible for polling buttons, adding
    // newly-scheduled
    // commands, running already-scheduled commands, removing finished or
    // interrupted commands,
    // and running subsystem periodic() methods. This must be called from the
    // robot's periodic
    // block in order for anything in the Command-based framework to work.
    //CommandScheduler.getInstance().run();
  }

  // This function is called once each time the robot enters Disabled mode.
  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
  }

  /** 
   * This autonomous runs the autonomous command selected by your
   * {@link RobotContainer} class.
   */
  @Override
  public void autonomousInit() {
    // m_autonomousCommand = m_robotContainer.getAutonomousCommand();

   // m_autoSelected = m_chooser.getSelected();
    // // schedule the autonomous command (example)
    // if (m_autonomousCommand != null) {
    // m_autonomousCommand.schedule();
    // }
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() { }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    // if (m_autonomousCommand != null) {
    // m_autonomousCommand.cancel();
    // }

    turretPID.setDomain(-75 * TURRET_RATIO, 75 * TURRET_RATIO);
    turretPID.setSetpoint(0);

    comp.enableDigital();
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    // Puts the robot in arcade drive
    m_myRobot.arcadeDrive(-joystick.getRawAxis(0), joystick.getRawAxis(1));

    // Joystick trigger activates motor
    if(joystick.getTrigger())
      intake.set(1);
    else if(joystick.getRawButton(1))
      intake.set(-1);
    else
      intake.motorOff();

    // Manually control the turret with bumpers
    if(xbox.getLeftBumper()) {
      turretPID.pause();
      turret.setPower(.2);
    }
    else if(xbox.getRightBumper()) {
      turretPID.pause();
      turret.setPower(-.2);
    }
    else if(turretPID.getP() == 0)
      turret.off();

    // Dpad controls
    switch(xbox.getPOV()){
      case 0: // UP
        elevator.set(.2);
        break;
      case 180: // DOWN
        elevator.set(-.2);
        break;
      case 90: // RIGHT
        shooter.increasePowerBy(.004);
        break;
      case 270: // LEFT
        shooter.decreasePowerBy(.004);
        break;
      case -1: // NOT PRESSED
        elevator.off();
    }

    // Right trigger pushes a ball into the shooter
    if(xbox.getRightTriggerAxis() > 0)
      elevator.fullForward();

    if(xbox.getLeftTriggerAxis() > 0) {
      turretPID.activate(
        ((turret.getPosition() / TURRET_RATIO) - limelight.getRotationAngle()) * TURRET_RATIO );
    }
      
    // Climber cannot go further down after hitting limit switch
    if(leftClimberSwitch.isPressed())
      climber.setLeft(xbox.getLeftY() > 0 ? 0 : xbox.getLeftY());
    else if(xbox.getLeftY() > .05 || xbox.getLeftY() < -.05 )
      climber.setLeft(xbox.getLeftY());
    else
      climber.setLeft(0);

    if(rightClimberSwitch.isPressed())
      climber.setRight(xbox.getRightY() > 0 ? 0 : xbox.getRightY());
    else if(xbox.getRightY() > .05 || xbox.getRightY() < -.05 )
      climber.setRight(xbox.getRightY());
    else
      climber.setRight(0);

    // X button controls the intake state
    if(xbox.getXButton())
      intake.down();
    else if(xbox.getYButton())
      intake.up();

    if(xbox.getStartButton())
      hood.setPower(-.1);
    else if(xbox.getBackButton())
      hood.setPower(.1);
    else
      hood.setPower(0);
    
    // B is essentially an e-stop
    if(xbox.getBButton()){
      shooter.off();
      elevator.off();
      climber.off();
      turret.off();
      hood.off();
      turretPID.pause();
    }

    // Update anything that needs to update
    shooter.updateCurrentPower();
    dashboard.printShooterRPM(shooter);
    dashboard.printTurretDegrees(turret);
    dashboard.PIDtoDashboard(turretPID, "Turret");
    limelight.updateLimelightTracking();
    dashboard.printLimelightData(limelight);
  }
}