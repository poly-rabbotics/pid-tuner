package frc.robot;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDOutput;


class ArmPIDController extends PIDController {
    public ArmPIDController(double Kp, double Ki, double Kd, double Kf, PIDSource source, PIDOutput output) {
        super(Kp, Ki, Kd, Kf, source, output);
    }

    /**
     * Returns the motor value needed to keep the arm in current position, against gravity.
     */
    @Override
    protected double calculateFeedForward() {
        double degrees = (getSetpoint() + getError() - 20); //total distance should be setpoint + error. Since 
                                //initial distance is already about 20 degrees from vertical, 
                                //and we want degrees from vertical not degrees from initial, 
                                //we must adjust for that as well.
        double radians = degrees / 180 * Math.PI; //TODO: change to a faster polynomial approximation after it is tested and working.
        return -Math.sin(radians) * getF(); //return radians - Math.pow(radians, 3)/6 + Math.pow(radians, 5)/120;
    }
}
