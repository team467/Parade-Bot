package frc.robot.subsystems.shooter;

import edu.wpi.first.math.MathShared;
import edu.wpi.first.math.MathUtil;
import frc.lib.utils.TunableNumber;

public class ShooterConstants {

    public static final double REVERSE_SHOOTER_PERCENT = -0.5;

    public static final int SHOOTER_MOTOR_ID = 8;

    public static final double ENCODER_VELOCITY_CONVERSION = 1;

    public static final double SPINUP_SECONDS = 0.6;

    public static final double TOLERANCE = 3; // TODO: change tolerance


    public static final TunableNumber PID_P = new TunableNumber("Shooter/P", 0.00005); //0.0000009
    public static final TunableNumber PID_I = new TunableNumber("Shooter/I",0.0000001); //0.0000003
    public static final TunableNumber PID_D = new TunableNumber("Shooter/D", 0.0000001); // 0.0001

}
// 0.00005
//0.0000001
//0.0000001