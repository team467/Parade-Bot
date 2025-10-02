package frc.robot.subsystems.vision;
import edu.wpi.first.math.geometry.*;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.photonvision.PhotonCamera;
public class VisionIOPhotonVision implements VisionIO{
    protected final PhotonCamera camera;
    protected final Transform3d robotToCamera;

    public VisionIOPhotonVision(String name, Transform3d robotToCamera){
        camera = new PhotonCamera(name);
        this.robotToCamera = robotToCamera;
    }


    @Override
    public void updateInputs (VisionIOInputs inputs) {
        inputs.connected = camera.isConnected();
        var result = camera.getLatestResult();
        if (result.hasTargets()) {
            inputs.latestTargetObservation = new PoseObservation(result.getBestTarget().fiducialId,result.getBestTarget().bestCameraToTarget.getTranslation(), result.getBestTarget().getYaw());
        }else{
            inputs.latestTargetObservation = new PoseObservation(0,new Translation3d(),0);
            }



        }

}