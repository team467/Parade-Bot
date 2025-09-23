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

    public boolean readyToShoot(){
        return inputs.readyToShoot;
    }

    public boolean hasBall(){
        return inputs.hasBall;
    }

    public Command stop() {
        return Commands.runOnce(
                () -> {
                    io.setSpeed(0);
                },
                this);
    }

    public Command speedUp() {
        return Commands.runOnce(
                () -> {
                    io.setSpeed(ShooterConstants.SPEED);
                },
                this
        );
    }

}

