// Copyright 2021-2024 FRC 6328
// http://github.com/Mechanical-Advantage
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// version 3 as published by the Free Software Foundation or
// available in the root directory of this project.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.

package frc.robot.subsystems.drive;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.ADIS16470_IMU;

public class DriveIOSpark implements DriveIO {
    private static final double GEAR_RATIO = 10.0;

    private final SparkMax leftLeader = new SparkMax(5, SparkLowLevel.MotorType.kBrushless);
    private final SparkMax rightLeader = new SparkMax(3, SparkLowLevel.MotorType.kBrushless);
    private final SparkMax leftFollower = new SparkMax(6, SparkLowLevel.MotorType.kBrushless);
    private final SparkMax rightFollower = new SparkMax(4, SparkLowLevel.MotorType.kBrushless);
    private final RelativeEncoder leftEncoder = leftLeader.getEncoder();
    private final RelativeEncoder rightEncoder = rightLeader.getEncoder();

    private final ADIS16470_IMU imu = new ADIS16470_IMU();

    public DriveIOSpark() {
        var LeftLeaderConfig = new SparkMaxConfig();
        LeftLeaderConfig.inverted(false)
                .idleMode(SparkBaseConfig.IdleMode.kBrake)
                .voltageCompensation(12)
                .smartCurrentLimit(60);

        var RightLeaderConfig = new SparkMaxConfig();
        RightLeaderConfig.inverted(false)
                .idleMode(SparkBaseConfig.IdleMode.kBrake)
                .voltageCompensation(12)
                .smartCurrentLimit(60);

        var LeftFollowerConfig = new SparkMaxConfig();
        LeftFollowerConfig.inverted(false)
                .idleMode(SparkBaseConfig.IdleMode.kBrake)
                .voltageCompensation(12)
                .smartCurrentLimit(60)
                .follow(3, false);

        var RightFollowerConfig = new SparkMaxConfig();
        RightFollowerConfig.inverted(false)
                .idleMode(SparkBaseConfig.IdleMode.kBrake)
                .voltageCompensation(12)
                .smartCurrentLimit(60)
                .follow(5, false);
        leftLeader.configure(LeftLeaderConfig, SparkBase.ResetMode.kResetSafeParameters, SparkBase.PersistMode.kPersistParameters);
        leftFollower.configure(LeftFollowerConfig, SparkBase.ResetMode.kResetSafeParameters, SparkBase.PersistMode.kPersistParameters);
        rightLeader.configure(RightLeaderConfig, SparkBase.ResetMode.kResetSafeParameters, SparkBase.PersistMode.kPersistParameters);
        rightFollower.configure(RightFollowerConfig, SparkBase.ResetMode.kResetSafeParameters, SparkBase.PersistMode.kPersistParameters);
    }

    @Override
    public void updateInputs(DriveIOInputs inputs) {
        inputs.leftPositionRad = Units.rotationsToRadians(leftEncoder.getPosition() / GEAR_RATIO);
        inputs.leftVelocityRadPerSec =
                Units.rotationsPerMinuteToRadiansPerSecond(leftEncoder.getVelocity() / GEAR_RATIO);
        inputs.leftAppliedVolts = leftLeader.getAppliedOutput() * leftLeader.getBusVoltage();
        inputs.leftCurrentAmps =
                new double[] {leftLeader.getOutputCurrent(), leftFollower.getOutputCurrent()};

        inputs.rightPositionRad = Units.rotationsToRadians(rightEncoder.getPosition() / GEAR_RATIO);
        inputs.rightVelocityRadPerSec =
                Units.rotationsPerMinuteToRadiansPerSecond(rightEncoder.getVelocity() / GEAR_RATIO);
        inputs.rightAppliedVolts = rightLeader.getAppliedOutput() * rightLeader.getBusVoltage();
        inputs.rightCurrentAmps =
                new double[] {rightLeader.getOutputCurrent(), rightFollower.getOutputCurrent()};

        inputs.gyroYaw = Rotation2d.fromDegrees(imu.getAngle());
    }

    @Override
    public void setVoltage(double leftVolts, double rightVolts) {
        leftLeader.setVoltage(leftVolts);
        rightLeader.setVoltage(rightVolts);
    }
}
