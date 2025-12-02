package frc.robot.subsystems.vision;

import edu.wpi.first.math.geometry.Translation2d;
import org.photonvision.PhotonCamera;
public class VisionIOPhotonVision implements VisionIO{
    protected final PhotonCamera camera;

    public VisionIOPhotonVision(String name){
        camera = new PhotonCamera(name);
    }


    @Override
    public void updateInputs (VisionIOInputs inputs) {
        inputs.connected = camera.isConnected();
        var result = camera.getLatestResult();
        if (result.hasTargets()) {
            inputs.latestTargetObservation =
                new PoseObservation(result.getBestTarget().fiducialId,result.getBestTarget().bestCameraToTarget.getTranslation().toTranslation2d(), result.getBestTarget().getYaw());
        }else{
            inputs.latestTargetObservation = new PoseObservation(0, new Translation2d(), 0);
            }
        inputs.lastestTagID = inputs.latestTargetObservation.tagID();
        inputs.distanceFromTarget = inputs.latestTargetObservation.translation2d().getNorm();
        inputs.yaw = inputs.latestTargetObservation.yaw();
        }

}