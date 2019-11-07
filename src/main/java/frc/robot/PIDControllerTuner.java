package frc.robot;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.*;

public class PIDControllerTuner {
    private PIDController pid;
    private XboxController joy;
    private double maxKChangePerSecond;
    /**
     * Constructor for PIDControllerTuner with default maxKChangePerSecond of 0.15
     * @param pid the pidController whose constants are to be adjusted
     * @param joy the XboxController that will be used to adjust the constants
     */
    public PIDControllerTuner(PIDController pid, XboxController joy) {
        this.pid = pid;
        this.joy = joy;
        this.maxKChangePerSecond = 0.15;
    }
    /**
     * Constructor in which one can set maxChangePerSecond
     * @param pid the pidController whose constants are to be adjusted
     * @param joy the XboxController that will be used to adjust the constants
     * @param maxKChangePerSecond the most you would like to be able to change a PID F constant each second
     */
    public PIDControllerTuner(PIDController pid, XboxController joy, double maxKChangePerSecond) {
        this.pid = pid;
        this.joy = joy;
        this.maxKChangePerSecond = maxKChangePerSecond;
    }
    /**
     * Update the PID value whose corresponding button is pressed. No change if user does not push the analog y-axis of the joystick.
     */
    public void update() {
        double changeRequest = - curve(joy.getY(Hand.kLeft)) * maxKChangePerSecond / 50;
        if(joy.getAButton()) { //A is pressed, so user wants to set Kf
            pid.setF(pid.getF() + changeRequest);
        }
        if(joy.getBButton()) { //B is pressed, so user wants to adjust Kp
            pid.setP(pid.getP() + changeRequest);
        }
        if(joy.getXButton()) { //X is pressed, so user wants to adjust Kd
            pid.setD(pid.getD() + changeRequest);
        }
        if(joy.getYButton()) { //Y is pressed, so user wants to adjust Ki
            pid.setI(pid.getI() + changeRequest);
        }
    }
    /**
     * Reports the values of the PID F constants, the setpoint, and the error to the SmartDashboard.
     */
    public void reportState() {
        //Put all constants to the SmartDashboard.
        SmartDashboard.putNumber("Kf", pid.getF());
        SmartDashboard.putNumber("Kp", pid.getP());
        SmartDashboard.putNumber("Kd", pid.getD());
        SmartDashboard.putNumber("Ki", pid.getI());
        //Put current setpoint to the SmartDashboard.
        SmartDashboard.putNumber("Setpoint", pid.getSetpoint());
        //Put error to SmartDashboard.
        SmartDashboard.putNumber("Error", pid.getError());
    }
    /**
     * Allows finer control by making small values smaller, but still maintains the range from -1 to +1
     * @param input some input value between -1 and 1
     * @return the input transformed such that less extreme inputs result in even less extreme outputs
     */
    private double curve(double input) {
        return Math.pow(input, 2) * input / Math.abs(input);
    }
}
