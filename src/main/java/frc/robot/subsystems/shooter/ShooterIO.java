package frc.robot.subsystems.shooter;

import org.littletonrobotics.junction.AutoLog;

public interface ShooterIO {

    @AutoLog
    class ShooterIOInputs {

        public double velocity; // RPM

        public double appliedVolts;

        public double currentAmps;

        public double temperature;

        public double setpointRPM = 0.0;

        public boolean readyToShoot = false;

        public boolean atSetpoint = false;
    }

    default void updateInputs(ShooterIOInputs inputs) {}

    default void setPercent(double percent) {}

    default void setVoltage(double voltage) {}

    default void setVelocity(double RPM) {}

    default void goToSetpoint() {}

    default void stop() {}
}