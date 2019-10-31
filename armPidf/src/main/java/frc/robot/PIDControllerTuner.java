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
        this.maxKChangePerSecond = 0.5;
    }
    public PIDControllerTuner(PIDController pid, XboxController joy, double maxKChangePerSecond) {
        this.pid = pid;
        this.joy = joy;
        this.maxKChangePerSecond = maxKChangePerSecond;
    }
    public void update() {
        double changeRequest = Math.pow(joy.getY(Hand.kLeft), 3) * maxKChangePerSecond;
        if(joy.getAButton()) { //User wants to set Kf
            pid.setF(pid.getF() + changeRequest);
        }
        if(joy.getBButton()) { //User wants to set Kp
            pid.setP(pid.getP() + changeRequest);
        }
        if(joy.getXButton()) { //User wants to set Kd
            pid.setD(pid.getD() + changeRequest);
        }
        if(joy.getYButton()) { //User wants to set Ki
            pid.setI(pid.getI() + changeRequest);
        }
        reportState();
    }
    private void reportState() {
        //Put all constants to the SmartDashboard. It's recommended that they are configured as graphs with respect to time
        SmartDashboard.putNumber("Kf", pid.getF());
        SmartDashboard.putNumber("Kp", pid.getP());
        SmartDashboard.putNumber("Kd", pid.getD());
        SmartDashboard.putNumber("Ki", pid.getI());

        //Put error to SmartDashboard. Recommended that they are graphs with respect to time
        SmartDashboard.putNumber("Error", pid.getError());
    }
}