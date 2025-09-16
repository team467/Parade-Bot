package frc.robot.subsystems.drive;

import org.littletonrobotics.junction.AutoLog;

public interface DriveIO {

    @AutoLog
    class DriveIOInputs{
        public double leftAppliedVolts = 0.0;
        public double leftCurrentAmps = 0.0;
        public double leftVelocityRadPerSec = 0.0;

        public double rightAppliedVolts = 0.0;
        public double rightCurrentAmps = 0.0;
        public double rightVelocityRadPerSec = 0.0;


    }
    default void updateInputs(DriveIOInputs inputs){}

    default void setVoltageLeft(double leftAppliedVolts){}

    default void setVoltageRight(double rightAppliedVolts){}



}
