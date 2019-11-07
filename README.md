# pid-tuner
We should be able to modify this code and use it to tune PID for any PID system.

Keep in mind that the ending values you get will probably be between 0 and 1 for all 4 values.
The order in which you tune matters. This order is recommended:
1. Tune F. Hold down the A button and adjust Kf using the left joystick Y axis.
You are done when you can move the arm to any position and have it just stay there, unsupported.
2. Tune P. Hold down the B button and adjust Kp using the left joystick Y axis.
You are done when you can move to a new setpoint fairly quickly, but not so quickly that it oscillates very severely.
3. Tune D. Hold down the X button and adjust Kd using the left joystick Y axis.
You are done when you can move to a new setpoint without significant oscillation or erratic motion.
4. Tune I. Hold down the Y button and adjust Ki using the left joystick Y axis.
You are done when you can reach a desired setpoint quickly enough, and you do not have too much steady state error.
Note that you may not need a nonzero Ki.

When the system works to your satisfaction, record the Kf values in a comment in pid-tuner.
