package frc.robot.subsystems.shooter;

import frc.robot.Constants;
import frc.lib.utils.TunableNumber;

public class ShooterConstants {
    public static final TunableNumber KS;
    public static final TunableNumber KV;
    public static final TunableNumber KP;
    public static final TunableNumber KI;
    public static final TunableNumber KD;

    public static final double TOLERANCE;

    public static final double ENCODER_POSITION_CONVERSION_FACTOR;
    public static final double ENCODER_VELOCITY_CONVERSION_FACTOR;

    public static final double REVERSE_SHOOTER_PERCENT = -0.5;
    public static final int SHOOTER_MOTOR_ID = 8;
    public static final double SPINUP_SECONDS = 0.6;


    static {
        switch (Constants.getRobot()) {
            case PARADE_DAY -> {
                KS = new TunableNumber("Shooter/KS", 0.0);
                KV = new TunableNumber("Shooter/KV", 0.0);
                KP = new TunableNumber("Shooter/KP", 0.0001);
                KI = new TunableNumber("Shooter/KI", 0.0);
                KD = new TunableNumber("Shooter/KD", 0.0);

                TOLERANCE = 0.0;
                ENCODER_POSITION_CONVERSION_FACTOR = 1.0;
                ENCODER_VELOCITY_CONVERSION_FACTOR = 1.0; // Already in RPM
            }
            default -> {
                KS = new TunableNumber("Shooter/KS", 0.0);
                KV = new TunableNumber("Shooter/KV", 0.0);
                KP = new TunableNumber("Shooter/KP", 0.0);
                KI = new TunableNumber("Shooter/KI", 0.0);
                KD = new TunableNumber("Shooter/KD", 0.0);

                TOLERANCE = 0.0;
                ENCODER_POSITION_CONVERSION_FACTOR = 0.0;
                ENCODER_VELOCITY_CONVERSION_FACTOR = 0.0;
            }
        }
    }
}