package frc.robot.subsystems.vision;

import org.littletonrobotics.junction.AutoLog

public interface VisionIO {
  @AutoLog
  public static class VisionIOInputs {
    public boolean connected = false;
    public int distanceFromTarget = 0;
    public double yaw = 0.0;
    public int tagId = 0;
  }

  public static enum PoseObservationType {
    PHOTONVISION
  }

  default void getDistanceFromTarget(double targetX, double targetY) {}
  default void getYaw(double yaw) {}

  public default void updateInputs(VisionIOInputs inputs) {}
}
// yaw
//distance from target
// tags
//connected