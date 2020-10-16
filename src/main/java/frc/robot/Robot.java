/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2020 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//This is the stuff I'm adding - Paul
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
//wtf is happening

// @Garpau09
//  update the detail @Garpau09
// Editing a "deleted branch"
// This is Tommy's contribution
//Checking a fetch vs. a pull
//Fetch and pull v2


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  private final Joystick m_stick = new Joystick(0);
  private final XboxController driverController = new XboxController(1);
  private final Timer m_timer = new Timer();

  private final PWMVictorSPX leftMotor = new PWMVictorSPX(0);
  private final PWMVictorSPX rightMotor = new PWMVictorSPX(1);
  private final DifferentialDrive m_robotDrive = new DifferentialDrive(leftMotor, rightMotor);
  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
  
    SmartDashboard.putData("Auto choices", m_chooser);
    
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
    m_timer.reset();
    m_timer.start();
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        if(m_timer.get() < 2.0){
          m_robotDrive.arcadeDrive(0.5, 0.0);
        } else {
          m_robotDrive.arcadeDrive(0.0, 0.0);
        }
        break;
      case kDefaultAuto:
        m_robotDrive.arcadeDrive(0.0, 0.0);
      default:
        // Put default auto code here
        break;
    }
  }

  /**
   * This function is called once when teleop is enabled.
   */
  @Override
  public void teleopInit() {
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    //m_robotDrive.arcadeDrive(-1*m_stick.getY(), m_stick.getX(), true);

    
    m_robotDrive.arcadeDrive(.65*Math.pow(driverController.getRawAxis(3)-driverController.getRawAxis(2), 3), .65*Math.pow(driverController.getRawAxis(4), 3));

    SmartDashboard.putNumber("Joystick Y value", m_stick.getY());
    SmartDashboard.putNumber("Joystick X", m_stick.getX());
    SmartDashboard.putNumber("Left Victor Value", leftMotor.get());
    SmartDashboard.putNumber("Right Victor Value", rightMotor.get());

  }

  /**
   * This function is called once when the robot is disabled.
   */
  @Override
  public void disabledInit() {
  }

  /**
   * This function is called periodically when disabled.
   */
  @Override
  public void disabledPeriodic() {
  }

  /**
   * This function is called once when test mode is enabled.
   */
  @Override
  public void testInit() {
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
