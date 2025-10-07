package frc.robot.subsystems.vision;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.littletonrobotics.junction.Logger;

public class Vision extends SubsystemBase {
    private final VisionIO io;
    public final VisionIOInputsAutoLogged inputs;

    public Vision(VisionIO io) {
        this.io = io;
        this.inputs = new VisionIOInputsAutoLogged();
    }
    @Override
    public void periodic() {
        io.updateInputs(inputs);
        Logger.processInputs("Vision/Camera", inputs);
    }
}
