package frc.robot;

public class RobotState {
    private static final RobotState state;
    public double visionYaw;

    static {
        state = new RobotState();
    }

    public RobotState() {
        visionYaw = 0.0;
    }

    public static RobotState get() {
        return state;
    }
}
