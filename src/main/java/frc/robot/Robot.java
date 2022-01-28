// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.



package frc.robot;

// Controller Imports
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.Joystick;

// Actuation imports (Motors, Compressors, etc.)
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

// Camera imports
import edu.wpi.first.cameraserver.CameraServer;

// Subsystem imports
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Drivetrain;

// Misc imports
import edu.wpi.first.wpilibj.TimedRobot;


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

  // ??
  private static final int armMoverID = 8;
  private CANSparkMax armMover;
  private Compressor comp;
  private boolean armIsMoving;
  private CANSparkMax topMover;
  private static final int  topMoverID = 4;
  private boolean topIsMoving;

  // Subsystem objects
  private Shooter shooter;
  private Drivetrain drivetrain;

  // Misc variables/objects
  private DifferentialDrive m_myRobot;

  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {
    // Initialize variables
    comp = new Compressor(0, PneumaticsModuleType.CTREPCM);

    xbox  = new XboxController(1);
    m_stick = new Joystick(0);

    armMover = new CANSparkMax(armMoverID, MotorType.kBrushless);
    armMover.restoreFactoryDefaults();
    armIsMoving = false;
    
    topMover= new CANSparkMax(topMoverID, MotorType.kBrushless);
    topMover.restoreFactoryDefaults();
    topIsMoving = false;

    drivetrain = new Drivetrain(7, 3, 6, 2);
    m_myRobot = new DifferentialDrive(
      drivetrain.getLeftMotorGroup(), drivetrain.getRightMotorGroup());

    CameraServer.startAutomaticCapture();

    shooter = new Shooter(5, 9);
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
    // CommandScheduler.getInstance().run();
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

  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {

    // Uses 'RT' to toggle the shooter
    /*
    if (xbox.getRightTriggerAxis() > .9 ) {
      shooter.toggle(.6);
    }
    */

    // Uses 'RT' to set the shooter and 'B' to turn off
    if (xbox.getRightTriggerAxis() > .02 ) {
      shooter.setPower(xbox.getRightTriggerAxis());
    }
    if (xbox.getBButton()) { 
      shooter.off(); 
    }

    if (xbox.getLeftBumper()) {
      comp.enableDigital();
    }
    if (xbox.getRightBumper()) {
      comp.disable();
    }

    m_myRobot.arcadeDrive(-m_stick.getRawAxis(1), m_stick.getRawAxis(0));

      
    if (xbox.getYButton()) { 
      if (armIsMoving) {
        armMover.set(0);
        armIsMoving = false;
      }
      else {
        armMover.set(-1);
        armIsMoving = true;
      }
    }
      
    if (xbox.getXButton()) { 
      if (topIsMoving) {
        topMover.set(0);
        topIsMoving = false;
      }
      else {
        topMover.set(-1);
        topIsMoving = true;
      }
    }
  }
}