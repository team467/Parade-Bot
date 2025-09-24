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

    public Command stop() {
        return Commands.runOnce(() ->{io.stop();}, this);
    }

    public Command speedUp() {
        return Commands.run(
                () -> {
                    io.setPercent(ShooterConstants.SPEED);
                },
                this
        );
    }

    //added to reverse motors if ball is jammed
    public Command reverse() {
        return Commands.startEnd(
                ()->io.setPercent(-0.25),
                ()->io.setPercent(0),
                this
        );
    }


}

