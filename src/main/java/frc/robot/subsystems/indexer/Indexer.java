package frc.robot.subsystems.indexer;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import org.littletonrobotics.junction.Logger;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Indexer extends SubsystemBase {
    private final IndexerIO io;
    private final IndexerIOInputsAutoLogged inputs = new IndexerIOInputsAutoLogged();

    public Indexer(IndexerIO io) {
        this.io = io;
    }

    @Override
    public void periodic() {
        io.updateInputs(inputs);
        Logger.processInputs("Indexer", inputs);
    }
    public boolean hasBall() {
        return inputs.ballAtSwitch;
    }

    public Command stop() {
        return Commands.runOnce(() ->{io.stop();}, this);
    }

    public Command indexUntilSwitch() {
        return Commands.run(() -> io.setPercent(IndexerConstants.INDEX_PERCENT), this)
                .until(io::isSwitchPressed)
                .finallyDo(interrupted -> io.stop());
    }

    public Command indexIntoShooter() {
        double targetPosition = inputs.position + IndexerConstants.SHOOTER_ROTATIONS;
        return Commands.run(() -> io.setPercent(IndexerConstants.INDEX_PERCENT), this)
                .until(() -> inputs.position >= targetPosition)
                .finallyDo(interrupted -> io.stop());
    }
    /*
    public edu.wpi.first.wpilibj2.command.Command reverse() {
        return edu.wpi.first.wpilibj2.command.Commands.startEnd(
                () -> io.setPercent(-0.4),   // reverse at 40% (tweak if needed)
                () -> io.setPercent(0),
                this
        );
    }
   */
}