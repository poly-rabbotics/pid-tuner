package frc.robot;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDOutput;


class ArmPIDController extends PIDController {
    public ArmPIDController(double Kp, double Ki, double Kd, double Kf, PIDSource source, PIDOutput output) {
        super(Kp, Ki, Kd, Kf, source, output);
    }

    @Override
    protected double calculateFeedForward() {
        double radians = getSetpoint() / 180 * Math.PI; //TODO: change to a faster polynomial approximation
        return -Math.sin(radians) * getF(); //return radians - Math.pow(radians, 3)/6 + Math.pow(radians, 5)/120;
    }
}
