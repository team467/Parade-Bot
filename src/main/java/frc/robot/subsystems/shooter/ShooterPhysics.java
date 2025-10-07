package frc.robot.subsystems.shooter;

/**
 * Distance->percent model for a single-wheel hooded shooter using basic projectile motion.
 * Assumptions: flat floor, constant launch angle, negligible drag.
 *
 * Adjust constants below to your robot.
 */
public final class ShooterPhysics {

    // ======= Field/Target Geometry (tweak these) =======

    /** Bin top height above floor (meters). Default 4 ft. */
    public static double BIN_TOP_HEIGHT_M = 1.2192; // 4.0 ft

    /** Tag vertical offset below the top of bin (meters). Default 2 ft. */
    public static double TAG_BELOW_TOP_M = 0.6096; // 2.0 ft

    /** If you want to aim a bit above/below the tag center (meters). Usually 0. */
    public static double TARGET_OFFSET_FROM_TAG_M = 0.00;

    /** **Effective** target opening diameter (meters). Default 1 ft bin (0.3048 m). */
    public static double BIN_DIAMETER_M = 0.3048; // 1.0 ft

    // ======= Shooter Geometry (tweak these) =======

    /** Shooter exit height above floor (meters). */
    public static double SHOOTER_EXIT_HEIGHT_M = 0.60;

    /** Shooter launch angle relative to floor (degrees). */
    public static double LAUNCH_ANGLE_DEG = 35.0;

    /** Fractional “energy efficiency” from motor->ball (losses, slip, etc.). */
    public static double EXIT_EFFICIENCY = 0.80;

    /** Wheel diameter (meters). */
    public static double WHEEL_DIAMETER_M = 0.100; // ~4 inches

    /** Gear ratio wheelRPM / motorRPM (1.0 if direct drive wheel on motor). */
    public static double WHEEL_TO_MOTOR_RATIO = 1.0;

    // ======= Motor model (tweak for your shooter motor) =======

    /** Max motor free speed (RPM) at 12V (NEO ~5676; Kraken/X ~6000). */
    public static double MOTOR_FREE_RPM_12V = 5676.0;

    /** Minimum / maximum percent clamps for safety & robustness. */
    public static double MIN_PERCENT = 0.15;
    public static double MAX_PERCENT = 1.00;

    // ======= Physics =======
    private static final double G = 9.80665; // m/s^2

    private ShooterPhysics() {}

    /**
     * Compute shooter percent from horizontal distance to the AprilTag on the bin.
     * Uses closed-form projectile kinematics:
     *
     * v^2 = g R^2 / ( 2 cos^2θ (R tanθ - Δy) )
     *
     * Where:
     *   R  = horizontal distance (meters)
     *   θ  = launch angle (radians)
     *   Δy = (targetHeight - shooterExitHeight)
     */
    public static double percentFromDistanceMeters(double distanceMeters) {
        // Derive target height from tag geometry
        double tagHeight = BIN_TOP_HEIGHT_M - TAG_BELOW_TOP_M;
        double targetHeight = tagHeight + TARGET_OFFSET_FROM_TAG_M;

        double deltaY = targetHeight - SHOOTER_EXIT_HEIGHT_M;
        double theta = Math.toRadians(LAUNCH_ANGLE_DEG);

        // Guard against invalid geometry (too steep/close such that denominator <= 0)
        double denom = (distanceMeters * Math.tan(theta) - deltaY);
        double cosTheta = Math.cos(theta);
        double cosTheta2 = cosTheta * cosTheta;

        if (denom <= 0.02) { // too close or angle too shallow for that target
            return clamp(MIN_PERCENT);
        }

        // Required ball exit speed
        double v_required = Math.sqrt((G * distanceMeters * distanceMeters) / (2.0 * cosTheta2 * denom));

        // Account for efficiency: motor must produce more surface speed than ideal
        double wheelSurfaceSpeed = v_required / EXIT_EFFICIENCY; // m/s

        // Wheel RPM from surface speed
        double wheelRPM = (wheelSurfaceSpeed / (Math.PI * WHEEL_DIAMETER_M)) * 60.0;

        // Motor RPM through gearing
        double motorRPM = wheelRPM / WHEEL_TO_MOTOR_RATIO;

        // Percent assuming linear RPM ~ percent * freeRPM
        double percent = motorRPM / MOTOR_FREE_RPM_12V;

        return clamp(percent);
    }

    private static double clamp(double p) {
        if (Double.isNaN(p) || Double.isInfinite(p)) return MIN_PERCENT;
        if (p < MIN_PERCENT) return MIN_PERCENT;
        if (p > MAX_PERCENT) return MAX_PERCENT;
        return p;
    }
}
