package frc.robot.subsystems.vision;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import org.littletonrobotics.junction.AutoLog;

public interface VisionIO {
  @AutoLog
  class VisionIOInputs {
    public boolean connected = false;
    public PoseObservation latestTargetObservation =
        new PoseObservation(0, new Translation2d(), 0);
    public int lastestTagID = 0 ;
    public double distanceFromTarget = 0;
    public double yaw = 0;
  }


  /** Represents the angle to a simple target, not used for pose estimation. */
  record TargetObservation(Rotation2d tx, Rotation2d ty) {}

  /** Represents a robot pose sample used for pose estimation. */
  record PoseObservation (
          int tagID,
          Translation2d translation2d,
          double yaw
  ){}

  enum PoseObservationType {
    PHOTONVISION
  }

   default void updateInputs(VisionIOInputs inputs) {}
}