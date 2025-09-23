package frc.robot.subsystems.shooter;

import static frc.robot.subsystems.shooter.ShooterConstants.*;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLimitSwitch;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.EncoderConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

public class ShooterIOSparkMax implements ShooterIO {

    private final SparkMax motor;
    private final RelativeEncoder encoder;

    public ShooterIOSparkMax() {
        motor = new SparkMax(SHOOTER_MOTOR_ID, MotorType.kBrushless);

        var config = new SparkMaxConfig();
        config.inverted(false)
                .idleMode(IdleMode.kBrake)
                .voltageCompensation(12)
                .smartCurrentLimit(30);

        EncoderConfig enc = new EncoderConfig();
        enc.positionConversionFactor(ENCODER_POSITION_CONVERSION);
        enc.velocityConversionFactor(ENCODER_VELOCITY_CONVERSION);
        config.apply(enc);

        motor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        encoder = motor.getEncoder();

    }

    @Override
    public void updateInputs(ShooterIOInputs inputs) {
        inputs.temperature = motor.getMotorTemperature();
        inputs.appliedVolts = motor.getBusVoltage() * motor.getAppliedOutput();
        inputs.currentAmps = motor.getOutputCurrent();
        inputs.velocity = encoder.getVelocity();
    }

    public void setPercent(double percent) {
        motor.set(percent);
    }

    public void setVoltage(double volts) {
        motor.setVoltage(volts);
    }

    public void stop() {
        motor.set(0);
    }
}
