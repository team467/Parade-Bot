package frc.robot.subsystems.shooter;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import java.util.function.DoubleSupplier;
import org.littletonrobotics.junction.AutoLogOutput;
import org.littletonrobotics.junction.Logger;
import frc.robot.subsystems.shooter.ShooterConstants;

public class Shooter extends SubsystemBase {
    private ShooterIO io;
    private ShooterIOInputsAutoLogged inputs = new ShooterIOInputsAutoLogged();

    public Shooter(ShooterIO io) {
        this.io = io;
        this.inputs = new ShooterIOInputsAutoLogged();
    }

    @Override
    public void periodic() {
        io.updateInputs(inputs);
        Logger.processInputs("Shooter", inputs);
    }

    public Command stop() {
        return Commands.runOnce(() ->{io.stop();}, this);
    }


    public Command reverse() {
        return Commands.startEnd(
            () -> io.setPercent(ShooterConstants.REVERSE_SHOOTER_PERCENT),
            () -> io.setPercent(0),
            this
         );
}


    public Command speedUp_60Percent() {
        return Commands.run(
                () -> {
                    io.setPercent(0.6);
                },
                this
        );
    }

    public Command speedUp_80Percent() {
        return Commands.run(
                () -> {
                    io.setPercent(0.8);
                },
                this
        );
    }

    public Command speedUp_40Percent() {
        return Commands.run(
                () -> {
                    io.setPercent(0.4);
                },
                this
        );
    }

    public Command speedUp_20Percent() {
        return Commands.run(
                () -> {
                    io.setPercent(0.2);
                },
                this
        );
    }

    public Command fullSpeed() {
        return Commands.run(
                () -> {
                    io.setPercent(1);
                },
                this
        );
    }


}
