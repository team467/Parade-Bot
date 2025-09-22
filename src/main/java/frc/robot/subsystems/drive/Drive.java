package frc.robot.subsystems.drive;


import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drive extends SubsystemBase {
private final DifferentialDrive differentialDrive;
    public Drive(DriveIO io, DoubleSupplier joystickX, DoubleSupplier joystickY){
        differentialDrive = new DifferentialDrive(io::setVoltageLeft, io::setVoltageRight);
    }
}
