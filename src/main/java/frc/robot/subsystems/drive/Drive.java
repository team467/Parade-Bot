package frc.robot.subsystems.drive;


import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;

public class Drive extends SubsystemBase {
private final DifferentialDrive differentialDrive;
    public Drive(DriveIO io){
        differentialDrive = new DifferentialDrive(io::setVoltageLeft, io::setVoltageRight);
    }
    public Command curvatureDrive(DoubleSupplier speedX, DoubleSupplier rotationZ, BooleanSupplier turnInPlace) {
     return differentialDrive.curvatureDrive(speedX.getAsDouble(), rotationZ.getAsDouble(), turnInPlace.getAsBoolean());
    }

}
