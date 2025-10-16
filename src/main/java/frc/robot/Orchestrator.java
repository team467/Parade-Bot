package frc.robot;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.indexer.Indexer;
import frc.robot.subsystems.shooter.Shooter;
import frc.robot.subsystems.shooter.ShooterConstants;
import org.littletonrobotics.junction.AutoLogOutput;
import org.littletonrobotics.junction.Logger;


public class Orchestrator {
    private final Indexer indexer;
    private final Shooter shooter;
    @AutoLogOutput
    private double Distance  = 300.0;
    @AutoLogOutput private double shootingPower = 0.0;

    public Orchestrator(Indexer indexer, Shooter shooter) {
        this.indexer = indexer;
        this.shooter = shooter;
    }
    public Command spinUpDistance(Double distance) {
        Distance = distance;
        double percent = (8.331 * Math.pow(10, -5) * Math.pow(distance, 2) + 0.0734 * distance + 15.709) / 100;
        Logger.recordOutput("Shooter/Percent", percent);
        return shooter.runPercent(percent);
    }
    public Command shootCycleDistance() {
        return Commands.parallel(
                spinUpDistance(420.0),
                Commands.sequence(
                                intakeIfNeeded(),
                                Commands.waitSeconds(ShooterConstants.SPINUP_SECONDS),
                                indexer.indexIntoShooter())
                        .repeatedly());
    }

    public Command spinUp(BooleanSupplier fastMode) {
        return Commands.either(
                shooter.fullSpeed(),
                shooter.runPercent(0.8),
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
