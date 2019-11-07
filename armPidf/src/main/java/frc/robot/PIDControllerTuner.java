package frc.robot;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.*;

public class PIDControllerTuner {
    private PIDController pid;
    private XboxController joy;
    private double maxKChangePerSecond;
    public PIDControllerTuner(PIDController pid, XboxController joy) {
        this.pid = pid;
        this.joy = joy;
        this.maxKChangePerSecond = 0.15;
    }
    public PIDControllerTuner(PIDController pid, XboxController joy, double maxKChangePerSecond) {
        this.pid = pid;
        this.joy = joy;
        this.maxKChangePerSecond = maxKChangePerSecond;
    }
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
    private double curve(double input) {
        return Math.pow(input, 2) * input / Math.abs(input);
    }
}
