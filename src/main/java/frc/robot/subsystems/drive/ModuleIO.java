package frc.robot.subsystems.drive;

import org.littletonrobotics.junction.AutoLog;

public interface ModuleIO{

    @AutoLog
    class ModuleIOInputs{
        public double volts = 0.0;
        public double current = 0.0;

    }
}

