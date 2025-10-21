package frc.robot.subsystems.shooter;

import org.littletonrobotics.junction.AutoLog;

public interface ShooterIO {

    @AutoLog
    class ShooterIOInputs {
        public double velocityRPM;
        public double appliedVolts;
        public double currentAmps;
        public double temperature;
        public double targetVelocityRPM = 0.0;
        public boolean atTargetVelocity;
        public boolean readyToShoot = false;
    }

    default void updateInputs(ShooterIOInputs inputs) {}

    default void setPercent(double percent) {}

    default void setVelocity(double velocityRPM) {}

    default void setVoltage(double voltage) {}

    default void stop() {}
}