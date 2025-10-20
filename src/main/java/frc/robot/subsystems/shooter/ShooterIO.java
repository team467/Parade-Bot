package frc.robot.subsystems.shooter;

import org.ejml.dense.row.factory.DecompositionFactory_CDRM;
import org.littletonrobotics.junction.AutoLog;

public interface ShooterIO {

    @AutoLog
    class ShooterIOInputs {

        public double velocity;

        public double setspeed = 0.0;

        public double appliedVolts;

        public double currentAmps;

        public double angularVelocity;

        public double temperature;

        public boolean readyToShoot = false;

        public boolean atSetspeed = false;

        public boolean isCalibrated = false;
    }

    default void updateInputs(ShooterIOInputs inputs) {}

    default void setPercent(double percent) {}

    default void setVoltage(double voltage) {}

    default void gotoSetspeed() {}

    default void stop() {}
}