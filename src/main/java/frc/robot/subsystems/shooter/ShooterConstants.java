package frc.robot.subsystems.shooter;

import edu.wpi.first.math.MathShared;
import edu.wpi.first.math.MathUtil;

public class ShooterConstants {

    public static final double REVERSE_SHOOTER_PERCENT = -0.5;

    public static final int SHOOTER_MOTOR_ID = 8;

    public static final double ENCODER_VELOCITY_CONVERSION = 1;

    public static final double SPINUP_SECONDS = 0.6;

    public static final double TOLERANCE = 0.1; // TODO: change tolerance

    public static final double PID_P = 0.000009;
    public static final double PID_I = 0.0000003;
    public static final double PID_D = 0.0001;
}