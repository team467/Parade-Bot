package frc.robot.subsystems.shooter;

import static frc.robot.subsystems.shooter.ShooterConstants.*;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.*;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.ClosedLoopConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

public class ShooterIOSparkMax implements ShooterIO {

    private final SparkMax motor;
    private final RelativeEncoder encoder;
    private final SparkClosedLoopController controller;

    private double targetVelocityRPM = 0.0;

    public ShooterIOSparkMax() {
        motor = new SparkMax(SHOOTER_MOTOR_ID, MotorType.kBrushless);
        encoder = motor.getEncoder();
        controller = motor.getClosedLoopController();

        var config = new SparkMaxConfig();
        config.inverted(false)
                .idleMode(IdleMode.kCoast)
                .voltageCompensation(12)
                .smartCurrentLimit(30);

        config
                .encoder
                .positionConversionFactor(ENCODER_POSITION_CONVERSION_FACTOR)
                .velocityConversionFactor(ENCODER_VELOCITY_CONVERSION_FACTOR);

        config
                .closedLoop
                .feedbackSensor(ClosedLoopConfig.FeedbackSensor.kPrimaryEncoder)
                .pidf(0.0001, 0.0, 0.0, 0.0);

        config
                .signals
                .primaryEncoderVelocityAlwaysOn(true)
                .primaryEncoderVelocityPeriodMs(20)
                .appliedOutputPeriodMs(20)
                .busVoltagePeriodMs(20)
                .outputCurrentPeriodMs(20);

        motor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    @Override
    public void updateInputs(ShooterIOInputs inputs) {
        inputs.temperature = motor.getMotorTemperature();
        inputs.appliedVolts = motor.getBusVoltage() * motor.getAppliedOutput();
        inputs.currentAmps = motor.getOutputCurrent();
        inputs.velocityRPM = encoder.getVelocity();
        inputs.targetVelocityRPM = targetVelocityRPM;

        inputs.atTargetVelocity =
                targetVelocityRPM > 0 &&
                        Math.abs(targetVelocityRPM - inputs.velocityRPM) < TOLERANCE;
    }

    @Override
    public void setPercent(double percent) {
        motor.set(percent);
    }

    @Override
    public void setVelocity(double velocityRPM) {
        this.targetVelocityRPM = velocityRPM;
        controller.setReference(velocityRPM, ControlType.kVelocity);
    }

    @Override
    public void setVoltage(double volts) {
        motor.setVoltage(volts);
    }

    @Override
    public void stop() {
        targetVelocityRPM = 0.0;
        motor.set(0);
    }
}