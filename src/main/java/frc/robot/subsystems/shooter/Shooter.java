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
        io.goToSetpoint();
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

    public boolean atSetpoint() {
        return inputs.atSetpoint;
    }

    public double getVelocity() {
        return inputs.velocity;
    }

    public Command toSetpoint(double setpointRPM) {
        return Commands.run(
                () -> {
                    io.setVelocity(setpointRPM);
                },
                this
        );
    }

    public Command toSetpoint(DoubleSupplier setpointRPM) {
        return Commands.run(
                () -> {
                    io.setVelocity(setpointRPM.getAsDouble());
                },
                this);
    }

    public Command fullSpeed() {
        return Commands.run(
                () -> {
                    io.setPercent(1);
                },
                this
        );
    }
        public Command runPercent(double percent){
            return Commands.run(() -> {
                io.setPercent(percent);
            },
                    this);
            }


}
