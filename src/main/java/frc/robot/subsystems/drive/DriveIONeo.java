package frc.robot.subsystems.drive;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkMax;

public class DriveIONeo implements DriveIO{
    private final SparkMax spark;
    private final RelativeEncoder encoder;
    private final SparkClosedLoopController controller;

    public DriveIONeo(DriveIO io){
        spark = new SparkMax(1, SparkLowLevel.MotorType.kBrushed);
        encoder = spark.getAlternateEncoder();

        controller = spark.getClosedLoopController();

    }



}
