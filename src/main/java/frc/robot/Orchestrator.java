package frc.robot;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.indexer.Indexer;
import frc.robot.subsystems.shooter.Shooter;
import frc.robot.subsystems.shooter.ShooterConstants;


public class Orchestrator {
    private final Indexer indexer;
    private final Shooter shooter;

    public Orchestrator(Indexer indexer, Shooter shooter) {
        this.indexer = indexer;
        this.shooter = shooter;
    }

    public Command spinUp(BooleanSupplier fastMode) {
        return Commands.either(
                shooter.fullSpeed(),
                shooter.speedUp(),
                fastMode);
    }

    public Command intakeIfNeeded() {
        return Commands.either(
                indexer.indexUntilSwitch(),
                Commands.none(),
                () -> !indexer.hasBall());
    }

    public Command shootOnce(BooleanSupplier fastMode) {
        return Commands.parallel(
                spinUp(fastMode),
                Commands.sequence(
                        intakeIfNeeded(),
                        Commands.waitSeconds(ShooterConstants.SPINUP_SECONDS),
                        indexer.indexIntoShooter()));
    }

    public Command shootCycle(BooleanSupplier fastMode) {
        return Commands.parallel(
                spinUp(fastMode),
                Commands.sequence(
                                intakeIfNeeded(),
                                Commands.waitSeconds(ShooterConstants.SPINUP_SECONDS),
                                indexer.indexIntoShooter())
                        .repeatedly());
    }

    public Command reverseAll() {
        return Commands.parallel(shooter.reverse(), indexer.reverse());
    }

    public Command stopAll() {
        return Commands.parallel(shooter.stop(), indexer.stop());
    }
}
