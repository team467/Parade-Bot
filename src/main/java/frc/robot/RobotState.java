package frc.robot;

public class RobotState {
    private static final RobotState state;
    public double vision_yaw;

    static {
        state = new RobotState();
    }

    public RobotState() {
        vision_yaw = 0.0;
    }

    public static RobotState get() {
        return state;
    }
}
