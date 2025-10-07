package frc.robot.subsystems.vision;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation3d;
import org.littletonrobotics.junction.AutoLog;

public interface VisionIO {
  @AutoLog
  public static class VisionIOInputs {
    public boolean connected = false;
    public PoseObservation latestTargetObservation =
        new PoseObservation(0, new Translation3d(), 0);
    public int lastestTagID = 0 ;
    public double distanceFromTarget = 0;
    public double yaw = 0;
  }


  /** Represents the angle to a simple target, not used for pose estimation. */
  public static record TargetObservation(Rotation2d tx, Rotation2d ty) {}

  /** Represents a robot pose sample used for pose estimation. */
  public static record PoseObservation (
          int tagID,
          Translation3d translation3d,
          double yaw
  ){}

  public static enum PoseObservationType {
    PHOTONVISION
  }

   public default void updateInputs(VisionIOInputs inputs) {}
}
