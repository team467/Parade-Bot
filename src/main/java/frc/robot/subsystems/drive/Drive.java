package frc.robot.subsystems.drive;


import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drive extends SubsystemBase {
DoubleConsumer setLeftDriveVoltage;
DoubleConsumer setRightDriveVoltage 

    public Drive(DriveIO io){
        setLeftDriveVoltage = (voltage) -> DriveIO.setVoltageLeft(voltage);
        setRightDriveVoltage = (voltage) -> DriveIO.setVoltageRight(voltage);
    }
}
