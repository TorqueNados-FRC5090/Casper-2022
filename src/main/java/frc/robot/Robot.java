// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.



package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.Compressor;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;

import edu.wpi.first.cameraserver.CameraServer;




/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  
  private DifferentialDrive m_myRobot;
  private Joystick m_stick;
  private static final int leftFrontDeviceID = 7;
  private static final int leftRearDeviceID = 6;
  private static final int rightFrontDeviceID = 3;
  private static final int rightRearDeviceID = 2;
  private CANSparkMax m_leftFrontMotor;
  private CANSparkMax m_leftRearMotor;
  private CANSparkMax m_rightFrontMotor;
  private CANSparkMax m_rightRearMotor;  
  private XboxController xbox;
  private static final int armMoverID = 8;
  private CANSparkMax armMover;
  private Compressor comp;
  private int armIsMoving = 0;
  private CANSparkMax topMover;
  private static final int  topMoverID = 4;
  private int topIsMoving = 0;
  
  //temporary code for shooter testing  
  private CANSparkMax m_shooterFlywheelTop;
  private static final int shooterFlywheelTopID = 9;
  private CANSparkMax m_shooterFlywheelBottom;
  private static final int shooterFlywheelBottomID = 5;
  private boolean shooterIsOn;

  //private MotorControllerGroup m_shooter;
  int onOffValue = 0;

  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {

    comp = new Compressor(0, PneumaticsModuleType.CTREPCM);

    xbox  = new XboxController(1);

    m_shooterFlywheelTop = new CANSparkMax(shooterFlywheelTopID, MotorType.kBrushless);
    m_shooterFlywheelTop.restoreFactoryDefaults();
    m_shooterFlywheelBottom = new CANSparkMax(shooterFlywheelBottomID, MotorType.kBrushless);
    m_shooterFlywheelBottom.restoreFactoryDefaults();
    m_shooterFlywheelBottom.setInverted(true);

    shooterIsOn = false;

    armMover = new CANSparkMax(armMoverID, MotorType.kBrushless);
    armMover.restoreFactoryDefaults();
    
    topMover= new CANSparkMax(topMoverID, MotorType.kBrushless);
    topMover.restoreFactoryDefaults();

    m_leftFrontMotor = new CANSparkMax(leftFrontDeviceID, MotorType.kBrushless);
    m_leftFrontMotor.restoreFactoryDefaults();
    m_leftRearMotor = new CANSparkMax(leftRearDeviceID, MotorType.kBrushless);
    m_leftRearMotor.restoreFactoryDefaults();
    MotorControllerGroup m_left = new MotorControllerGroup(m_leftFrontMotor, m_leftRearMotor);

    m_rightFrontMotor = new CANSparkMax(rightFrontDeviceID, MotorType.kBrushless);
    m_rightFrontMotor.restoreFactoryDefaults();
    m_rightRearMotor = new CANSparkMax(rightRearDeviceID, MotorType.kBrushless);
    m_rightRearMotor.restoreFactoryDefaults();
    MotorControllerGroup m_right = new MotorControllerGroup(m_rightFrontMotor, m_rightRearMotor);

    m_myRobot = new DifferentialDrive(m_left, m_right);

    m_stick = new Joystick(0);

    CameraServer.startAutomaticCapture();
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

  // Hold A to shoot
  /*
  if (xbox.getAButtonPressed()) {

    m_shooterFlywheelBottom.set(.62);
    m_shooterFlywheelTop.set(.62);
    shootingFlywheelTop = 69;
    shootingFlywheelBottom = 69;

  } else if (xbox.getAButtonReleased()) {

    m_shooterFlywheelTop.set(0);
    m_shooterFlywheelBottom.set(0);
    shootingFlywheelTop = 0;
    shootingFlywheelBottom = 0;

  }
  */
    
  // Press A to toggle the shooter 
  if( xbox.getAButtonPressed() ) {
    if( shooterIsOn ) {
      m_shooterFlywheelTop.set(0);
      m_shooterFlywheelBottom.set(0);
      shooterIsOn = false;
    }
    else {
      m_shooterFlywheelBottom.set(.62);
      m_shooterFlywheelTop.set(.62);
      shooterIsOn = true;
    }
  }



  /* this is commented out for testing shooter
  if (xbox.getAButton()) { 

    dubs.set(DoubleSolenoid.Value.kForward);

  } else if (xbox.getBButton()) {

    dubs.set(DoubleSolenoid.Value.kReverse);

  }
*/


  if (xbox.getLeftBumper()) {

    comp.enableDigital();
  
  } else if(xbox.getRightBumper()) {

    comp.disable();

  }

    m_myRobot.arcadeDrive(-m_stick.getRawAxis(1), m_stick.getRawAxis(0));

    
	if (xbox.getYButton() && armIsMoving == 0 ) { 

      armMover.set(-1);
      armIsMoving = 69;
  
    } else if (xbox.getYButton()) {
  
       armMover.set(0);
       armIsMoving = 0;
      
    }
    
  if (xbox.getXButton() && topIsMoving == 0 ) {

      topMover.set(-1);
      topIsMoving = 69;

    } else if (xbox.getXButton()) {

      topMover.set(0);
      topIsMoving = 0;
    }
  }
}