/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Encoder;
import frc.robot.ArmPIDController;
import edu.wpi.first.wpilibj.XboxController;
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();


  VictorSP arm;
  Joystick joy0;
  XboxController joy1; //It is only guaranteed that it will work as expected if this one is XboxController
  DigitalInput topSwitch, bottomSwitch;

  Encoder armEncoder;
  ArmPIDController armPid;
  PIDControllerTuner tuner;
  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    //Construct the arm (up/down) motor
    arm = new VictorSP(4);
    //Construct the two joysticks
    joy0 = new Joystick(0);      //This joystick is for changing the setpoint
    joy1 = new XboxController(1);//This joystick is for tuning the PID constants

    armEncoder = new Encoder(5, 7); //set to actual port numbers A and B
    armEncoder.setDistancePerPulse(0.26); //TODO: set to actual distance

    //Start by setting all constants to 0 except for Kf. Kf is set to 0.2 initially.
    armPid = new ArmPIDController(0, 0, 0, 0.2, armEncoder, arm); //This PIDController takes input from armEncoder to control arm
    tuner = new PIDControllerTuner(armPid, joy1); //This tuner uses joy1 to adjust constants Kf, Kp, Kd, Ki
    armPid.enable(); //let the PIDController start controlling the arm
  }

  @Override
  public void robotPeriodic() {
  }

  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // autoSelected = SmartDashboard.getString("Auto Selector",
    // defaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  @Override
  public void autonomousPeriodic() {
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    tuner.update(); //adjust motor constants as requested
    tuner.reportState(); //report to SmartDashboard, which is viewable in Shuffleboard.
// ARM ANGLE
    if(topSwitch.get()) {
      armEncoder.reset(); //reset the total distance measurement to zero if top limit switch is pressed
    }
    if(joy0.getRawButton(4)){ //button Y is pressed, so raise arm
      armPid.setSetpoint(0);
    }
    else if(joy0.getRawButton(1)){ //button A is pressed, so bring arm down
      armPid.setSetpoint(-90);
    }
  }
  @Override
  public void testPeriodic() {
  }
}
