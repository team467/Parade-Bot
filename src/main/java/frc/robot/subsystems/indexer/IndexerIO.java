package frc.robot.subsystems.indexer;

import org.littletonrobotics.junction.AutoLog;

public interface IndexerIO {
    @AutoLog
    class IndexerIOInputs {
        public double percentOutput = 0.0;
        public double volts = 0.0;
        public double amps = 0.0;
        public double position = 0.0; 
        public double velocity = 0.0; 
        public boolean ballAtSwitch = false; 
    }

    default void updateInputs(IndexerIOInputs inputs) {}

    default void setPercent(double percent) {}

    default void setVoltage(double volts) {}

    default void stop() {}

    default boolean isSwitchPressed() {
        return false;
      }
}
