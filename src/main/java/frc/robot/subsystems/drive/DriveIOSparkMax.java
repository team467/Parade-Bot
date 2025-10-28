package frc.robot.subsystems.drive;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.ClosedLoopConfig;
import com.revrobotics.spark.config.EncoderConfig;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import frc.robot.RobotState;

import static com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor.kPrimaryEncoder;
import static frc.robot.subsystems.drive.DriveConstants.RPM_PER_RAD;

public class DriveIOSparkMax implements DriveIO{
    private final SparkMax leftLeader;
    private final SparkMax leftFollower;
    private final SparkMax rightLeader;
    private final SparkMax rightFollower;

    private final RelativeEncoder leftLeaderEncoder;
    private final RelativeEncoder rightLeaderEncoder;

    private SparkClosedLoopController leftLeaderController;
    private SparkClosedLoopController rightLeaderController;

    public DriveIOSparkMax(){
        leftLeader = new SparkMax(3, SparkLowLevel.MotorType.kBrushless);
        leftFollower = new SparkMax(4, SparkLowLevel.MotorType.kBrushless);
        leftLeaderEncoder = leftLeader.getEncoder();
        rightLeader = new SparkMax(5, SparkLowLevel.MotorType.kBrushless);
        rightFollower = new SparkMax(6, SparkLowLevel.MotorType.kBrushless);
        rightLeaderEncoder  = rightLeader.getEncoder();

        leftLeaderController = leftLeader.getClosedLoopController();
        rightLeaderController = rightLeader.getClosedLoopController();

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

        var loopConfig = new ClosedLoopConfig();
        loopConfig
                .feedbackSensor(kPrimaryEncoder)
                .positionWrappingEnabled(false)
                .pid(0.000009,0.0000003,0.0001);// TODO: tune PIDF values

//        EncoderConfig enc = new EncoderConfig();
//        enc.velocityConversionFactor(RPM_TO_RADS);
//        config.apply(enc);
//        config.apply(loopConfig);

        LeftLeaderConfig.apply(loopConfig);
        RightLeaderConfig.apply(loopConfig);
        leftLeader.configure(LeftLeaderConfig, SparkBase.ResetMode.kResetSafeParameters, SparkBase.PersistMode.kPersistParameters);
        leftFollower.configure(LeftFollowerConfig, SparkBase.ResetMode.kResetSafeParameters, SparkBase.PersistMode.kPersistParameters);
        rightLeader.configure(RightLeaderConfig, SparkBase.ResetMode.kResetSafeParameters, SparkBase.PersistMode.kPersistParameters);
        rightFollower.configure(RightFollowerConfig, SparkBase.ResetMode.kResetSafeParameters, SparkBase.PersistMode.kPersistParameters);
    }

    @Override
    public void updateInputs(DriveIOInputs inputs){
        inputs.leftAppliedVolts = leftLeader.getBusVoltage() * leftLeader.getAppliedOutput();
        inputs.leftCurrentAmps = leftLeader.getOutputCurrent();
        inputs.leftVelocityRadPerSec = leftLeader.get();

        inputs.rightAppliedVolts = rightLeader.getBusVoltage() * rightLeader.getAppliedOutput();
        inputs.rightCurrentAmps = rightLeader.getOutputCurrent();
        inputs.rightVelocityRadPerSec = rightLeader.get();
    }

    @Override
    public void setVoltageLeft(double leftAppliedVolts){
        leftLeader.setVoltage(leftAppliedVolts);
    }

    @Override
    public void setVoltageRight(double rightAppliedVolts){
        rightLeader.setVoltage(rightAppliedVolts);
    }

    @Override
    public void setVelocityRadPerSecL(double leftVelocityRadPerSec){
        leftLeader.set(leftVelocityRadPerSec);
    }

    @Override
    public void setVelocityRadPerSecR(double rightVelocityRadPerSec){
        rightLeader.set(rightVelocityRadPerSec);
    }

    @Override
    public void rotateToTag() {
        leftLeaderController.setReference(RobotState.get().vision_yaw * RPM_PER_RAD, SparkBase.ControlType.kVelocity);
        rightLeaderController.setReference(-RobotState.get().vision_yaw * RPM_PER_RAD, SparkBase.ControlType.kVelocity);
    }
}
