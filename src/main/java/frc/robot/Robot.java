// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.



package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.simulation.XboxControllerSim;
//import edu.wpi.first.wpilibj2.command.Command;
//import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj.Compressor;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.motorcontrol.Talon;
/**
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
*/
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Compressor;

import edu.wpi.first.cameraserver.CameraServer;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.motorcontrol.VictorSP;
import edu.wpi.first.wpilibj.XboxController;

//import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.networktables.*;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;




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
  private boolean equals;
  private int armIsMoving = 0;
  private CANSparkMax topMover;
  private static final int  topMoverID = 4;
  private int topIsMoving = 0;
  
  //temporary code for shooter testing  
  private CANSparkMax m_shooterFlywheelTop;
  private static final int shooterFlywheelTopID = 9;
  private int shootingFlywheelTop = 0;
  private CANSparkMax m_shooterFlywheelBottom;
  private static final int shooterFlywheelBottomID = 5;
  private int shootingFlywheelBottom = 0;
  private boolean shooterIsOn = false;

  //private MotorControllerGroup m_shooter;
  int onOffValue = 0;

  private DoubleSolenoid dubs = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 0, 1);
/*
  private NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
  private NetworkTableEntry tx = table.getEntry("tx");
  private NetworkTableEntry ty = table.getEntry("ty");
  private NetworkTableEntry ta = table.getEntry("ta");
  
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
*/
  //private boolean m_LimelightHasValidTarget = false;
 // private double m_LimelightDriveCommand = 0.0;
 // private double m_LimelightSteerCommand = 0.0;


  //private WPI_VictorSPX myVictor1 = new WPI_VictorSPX(1);
  //private WPI_VictorSPX myVictor2 = new WPI_VictorSPX(2);
  //private WPI_VictorSPX myVictor3 = new WPI_VictorSPX(3);
  //private WPI_VictorSPX myVictor4 = new WPI_VictorSPX(4);
  //Lines 41 through 45 are to move the victor workhorse

  // private Command m_autonomousCommand;

  // private RobotContainer m_robotContainer;

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
    //MotorControllerGroup m_shooter = new MotorControllerGroup(m_shooterFlywheelBottom, m_shooterFlywheelTop);
    m_shooterFlywheelBottom.setInverted(true);

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

   // m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    //    m_chooser.addOption("My Auto", kCustomAuto);
    //    SmartDashboard.putData("Auto choices", m_chooser);
  

    



    // Instantiate our RobotContainer. This will perform all our button bindings,
    // and put our
    // autonomous chooser on the dashboard.
    // m_robotContainer = new RobotContainer();

    //myVictor1.set(ControlMode.PercentOutput, 0);
    //myVictor2.set(ControlMode.PercentOutput, 0);
    //myVictor3.set(ControlMode.PercentOutput, 0);
    //myVictor4.set(ControlMode.PercentOutput, 0);

    //WPI_VictorSPX m_frontLeft = new WPI_VictorSPX(1);
    //WPI_VictorSPX m_rearLeft = new WPI_VictorSPX(2);
    //motorcontrol.MotorControllerGroup m_left = new motorcontrol.MotorControllerGroup(m_frontLeft, m_rearLeft);

    //WPI_VictorSPX m_frontRight = new WPI_VictorSPX(3);
    //WPI_VictorSPX m_rearRight = new WPI_VictorSPX(4);
    //motorcontrol.MotorControllerGroup m_right = new motorcontrol.MotorControllerGroup(m_frontRight, m_rearRight);

    //setM_drive(new DifferentialDrive(m_left, m_right));
    //setM_driveStick(new Joystick(1));
    //Lines 65 through 80 are to move the victor workhorse

  }

  //public Joystick getM_driveStick() {
  //  return m_driveStick;
  //}

  //public void setM_driveStick(Joystick m_driveStick) {
  //  this.m_driveStick = m_driveStick;
  //}

  //public DifferentialDrive getM_drive() {
  //  return m_drive;
  //}

  //public void setM_drive(DifferentialDrive m_drive) {
  //  this.m_drive = m_drive;
  //}
  //Lines 84 through 99 are to move the victor workhorse

  /**
   * This function is called every robot packet, no matter the mode. Use this for
   * items like diagnostics that you want ran during disabled, autonomous,
   * teleoperated and test.
   *
   * <p>
   * This runs after the mode specific periodic functions, but before LiveWindow
   * and SmartDashboard integrated updating.
   */
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
  public void autonomousPeriodic() {

    // spark = new PWMSparkMax(2);
    // spark.set(-0.76);
    // System.out.println("hello");
  }

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
  if( xbox.getAButtonPressed() && shooterIsOn ) {
    m_shooterFlywheelBottom.set(.62);
    m_shooterFlywheelTop.set(.62);
    shootingFlywheelTop = 69;
    shootingFlywheelBottom = 69;
    shooterIsOn = true;
  }
  else if( xbox.getAButtonPressed() && !shooterIsOn ) {
    m_shooterFlywheelTop.set(0);
    m_shooterFlywheelBottom.set(0);
    shootingFlywheelTop = 0;
    shootingFlywheelBottom = 0;
    shooterIsOn = false;
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

    //armMover.set(controller.getLeftTriggerAxis());

    
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
/*
    //read values periodically
    double x = tx.getDouble(0.0);
    double y = ty.getDouble(0.0);
    double area = ta.getDouble(0.0);

    //post to smart dashboard periodically
    SmartDashboard.putNumber("LimelightX", x);
    SmartDashboard.putNumber("LimelightY", y);
    SmartDashboard.putNumber("LimelightArea", area);
    
    Update_Limelight_Tracking();

    double steer = xbox.getRightX();
    double drive = -xbox.getLeftY();
    boolean auto = xbox.getAButton();

    steer *= 0.70;
    drive *= 0.70;

    if (auto)
    {
      if (m_LimelightHasValidTarget)
      {
        m_myRobot.arcadeDrive(m_LimelightDriveCommand,m_LimelightSteerCommand);
      }
      else
      {
        m_myRobot.arcadeDrive(0.0,0.0);
      }
    }
    else
    {
      m_myRobot.arcadeDrive(drive,steer);
    }

*/
    
     
    // The 5 lines above move a sparkmax named "SparkMotor" with the A button

    // SparkMotor.set(controller.getLeftTriggerAxis());
    // The line above moves a sparkmax named "SparkMotor" with the left trigger

    // if(controller.getYButton()) {
    // mytalon1.set(ControlMode.PercentOutput,.5);
    // } else {
    // mytalon1.set(ControlMode.PercentOutput,0);
    // }
    // The 5 lines above are to move "mytalon1" with the y button

    //var MD = getM_drive();
    //var MDrive = getM_driveStick();

    //GenericHID driveStick;
    //MD.arcadeDrive(-controller.getRawAxis(1), controller.getRawAxis(0));
    // Lines 183 through 188 are used to control the victor work horse

  }

  //private void set(int i) {
  //}
/*
  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    // CommandScheduler.getInstance().cancelAll();
  }
*/
  /** This function is called periodically during test mode. */
  /*
  @Override
  public void testPeriodic() {
  }

  public void Update_Limelight_Tracking()
  {
        // These numbers must be tuned for your Robot!  Be careful!
        final double STEER_K = 0.03;                    // how hard to turn toward the target
        final double DRIVE_K = 0.26;                    // how hard to drive fwd toward the target
        final double DESIRED_TARGET_AREA = 13.0;        // Area of the target when the robot reaches the wall
        final double MAX_DRIVE = 0.7;                   // Simple speed limit so we don't drive too fast

        double tv = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);
        double tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
        double ty = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
        double ta = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta").getDouble(0);

        if (tv < 1.0)
        {
          m_LimelightHasValidTarget = false;
          m_LimelightDriveCommand = 0.0;
          m_LimelightSteerCommand = 0.0;
          return;
        }

        m_LimelightHasValidTarget = true;

        // Start with proportional steering
        double steer_cmd = tx * STEER_K;
        m_LimelightSteerCommand = steer_cmd;

        // try to drive forward until the target area reaches our desired area
        double drive_cmd = (DESIRED_TARGET_AREA - ta) * DRIVE_K;

        // don't let the robot drive too fast into the goal
        if (drive_cmd > MAX_DRIVE)
        {
          drive_cmd = MAX_DRIVE;
        }
        m_LimelightDriveCommand = drive_cmd;
  }
  */
}