package frc.robot.subsystems.drive;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkMax;

public class DriveIONeo implements DriveIO{
    private final SparkMax leftLeader;
    private final SparkMax leftFollower;
    private final SparkMax rightLeader;
    private final SparkMax rightFollower;

    private final RelativeEncoder leftEncoder;
    private final RelativeEncoder rightEncoder;

    private final SparkClosedLoopController leftController;
    private final SparkClosedLoopController rightController;

    public DriveIONeo(DriveIO io){
        leftLeader = new SparkMax(1, SparkLowLevel.MotorType.kBrushless);
        leftFollower = new SparkMax(2, SparkLowLevel.MotorType.kBrushless);
        rightLeader = new SparkMax(3, SparkLowLevel.MotorType.kBrushless);
        rightFollower = new SparkMax(4, SparkLowLevel.MotorType.kBrushless);


    }



}
