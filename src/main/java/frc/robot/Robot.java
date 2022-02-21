package frc.robot;

// Controller Imports
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.Joystick;

// Actuation imports (Motors, Compressors, etc.)
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

// Camera imports
import edu.wpi.first.cameraserver.CameraServer;

// Subsystem imports
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Dashboard;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Climber;

// Misc imports
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import java.io.IOException;



/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  // Controller ojects
  private Joystick m_stick; 
  private XboxController xbox;

  // Subsystem objects
  private Shooter shooter;
  private Drivetrain drivetrain;
  private Dashboard dashboard;
  private Limelight limelight;
  private Elevator elevator; 
  private Intake intake;
  private Climber climber;

  // Misc variables/objects
  private DifferentialDrive m_myRobot;
  private Compressor comp;
  
  // This function is run when the robot is first started up and should be used
  // for any initialization code.
  @Override
  public void robotInit() {
    // Initialize variables
    xbox  = new XboxController(1);
    m_stick = new Joystick(0);

    drivetrain = new Drivetrain(7, 3, 6, 2);
    m_myRobot = new DifferentialDrive(
      drivetrain.getLeftMotorGroup(), drivetrain.getRightMotorGroup());

    CameraServer.startAutomaticCapture();
    limelight = new Limelight();

    shooter = new Shooter(5, 9);
    shooter.setLock(true);

    elevator = new Elevator(13);
    // Please change intake motor to the correct motor ID 
    intake = new Intake(14, 1);
    comp = new Compressor(0, PneumaticsModuleType.CTREPCM);

    climber = new Climber(11, 12);

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

  /** This function is called once each time the robot enters Disabled mode. */
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

    comp.disable();

    // Print limelight test data
    try {
      limelight.loadFromFile("./Test.txt");
      limelight.printData();
    } catch (IOException e) { e.printStackTrace(); }
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    // Puts the robot in arcade drive
    m_myRobot.arcadeDrive(-m_stick.getRawAxis(0), m_stick.getRawAxis(1));
    
    // 'RT' sets the shooter power and locks at highest value
    if (xbox.getRightTriggerAxis() > 0 ) {
      shooter.setPower(xbox.getRightTriggerAxis());
    }

    // When pressing the left trigger, the intake motor will turn on based on the 
    // amount of pressure applyed to the tigger. 
    if (m_stick.getRawButton(1) ) {
      intake.motorSet(1);
    }

    if (xbox.getLeftTriggerAxis() > 0) {
      climber.setPower(.95);
    }

    else {
      climber.setPower(0);
    }

    if (xbox.getLeftBumper()) {
      climber.setPower(-.95);
    }

    // 'LB' turns the compressor on
    if (xbox.getLeftBumper()) {
      comp.enableDigital();
    }
    // 'RB' turns the compressor off
    if (xbox.getRightBumper()) {
      comp.disable();
    }

    // 'B' turns off the shooter
    if (xbox.getBButton()) { 
      shooter.off(); 
      climber.setPower(0);
    }

    // Holding x activates the elevator
    if(xbox.getXButton()){
      elevator.on();
    }
    else {
      elevator.updateElevator();
    }

    // Update the SmartDashboard
    dashboard.printShooterRPM(shooter);

    // 'Y' toggles the arm position
    if(xbox.getYButton()){
      Timer.delay(0.2); //This is debounce 
      intake.toggleArms();
    }

    // Update the SmartDashboard
    dashboard.printShooterRPM(shooter);
    dashboard.printBallStatus(elevator);
  }
}