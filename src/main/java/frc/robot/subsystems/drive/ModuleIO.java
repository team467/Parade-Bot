package frc.robot.subsystems.drive;
import edu.wpi.first.math.geometry.Rotation2d;

import org.littletonrobotics.junction.AutoLog;

public interface ModuleIO{

    @AutoLog
    class ModuleIOInputs{
        public double volts = 0.0;
        public double current = 0.0;

        public boolean driveConnected = false;
        public double drivePositionRad = 0.0;
        public double driveVelocityRadPerSec = 0.0;
        public double driveAppliedVolts = 0.0;
        public double driveCurrentAmps = 0.0;

    }

    /** Updates the set of loggable inputs. */
    public default void updateInputs(ModuleIOInputs inputs) {}

    /** Run the drive motor at the specified open loop value. */
    public default void setDriveOpenLoop(double output) {}

    /** Run the drive motor at the specified velocity. */
    public default void setDriveVelocity(double velocityRadPerSec) {}



}


