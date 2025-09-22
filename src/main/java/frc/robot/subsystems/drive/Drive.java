package frc.robot.subsystems.drive;


import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;
import org.littletonrobotics.junction.Logger;

public class Drive extends SubsystemBase {
    private final DriveIO io;
    private final DifferentialDrive differentialDrive;
    public Drive(DriveIO io){
        this.io = io;
        differentialDrive = new DifferentialDrive(io::setVoltageLeft, io::setVoltageRight);
    }
    public Command curvatureDrive(DoubleSupplier speedX, DoubleSupplier rotationZ, BooleanSupplier turnInPlace) {
        return Commands.run(
            () -> differentialDrive.curvatureDrive(speedX.getAsDouble(), rotationZ.getAsDouble(), turnInPlace.getAsBoolean());
        );
    }
    @Override
    public void periodic() {
        io.updateInputs(inputs);
        Logger.processInputs("Drive", inputs);
    }
}
