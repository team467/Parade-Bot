package frc.robot;

public class RobotState {
    static private RobotState state;
    public double vision_yaw;

    public static void init() {
    }

    public static RobotState get() {
        return state;
    }
}
