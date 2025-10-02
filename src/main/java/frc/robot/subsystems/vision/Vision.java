package frc.robot.subsystems.vision;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.littletonrobotics.junction.Logger;

public class Vision extends SubsystemBase {

    private final VisionIO io;
    private final VisionIOInputsAutoLogged inputs;
    public Vision( VisionIO io) {
        this.io = io;

        // Initialize inputs
        this.inputs = new VisionIOInputsAutoLogged();
        io.updateInputs(inputs);
        }



    @Override
    public void periodic() {
        io.updateInputs(inputs);
        Logger.processInputs("Vision", inputs);
    }

    public double distanceFromTarget(){
        return inputs.distanceFromTarget;
    }
    public double getYaw(){
        return inputs.yaw;
    }
    public double getTagID(){
        return inputs.lastestTagID;
    }

}

