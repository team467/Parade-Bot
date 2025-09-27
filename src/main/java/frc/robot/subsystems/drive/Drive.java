package frc.robot.subsystems.drive;


import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;
import org.littletonrobotics.junction.Logger;

public class Drive extends SubsystemBase {
    private final DriveIO io;
    private final DifferentialDrive differentialDrive;
    private DriveIOInputsAutoLogged inputs = new DriveIOInputsAutoLogged();
    public Drive(DriveIO io){
        this.io = io;
        differentialDrive = new DifferentialDrive(io::setVoltageLeft, io::setVoltageRight);
        differentialDrive.setSafetyEnabled(false);
    }
    @Override
    public void periodic() {
        io.updateInputs(inputs);
        Logger.processInputs("Drive", inputs);
    }
    public Command arcadeDrive(DoubleSupplier speedX, DoubleSupplier rotation) {
        return Commands.run(
            () -> {
                differentialDrive.arcadeDrive(speedX.getAsDouble() * 12, rotation.getAsDouble());
            }, this);
    }
}
