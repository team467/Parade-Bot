package frc.robot.subsystems.indexer;

import static frc.robot.subsystems.indexer.IndexerConstants.*;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLimitSwitch;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.EncoderConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

public class IndexerIOSparkMax implements IndexerIO {

    private final SparkMax motor;
    private final RelativeEncoder encoder;
    private final SparkLimitSwitch limitSwitch;

    public IndexerIOSparkMax() {
        motor = new SparkMax(INDEXER_MOTOR_ID, MotorType.kBrushless);

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

        limitSwitch = motor.getForwardLimitSwitch();
    }

    @Override
    public void updateInputs(IndexerIOInputs inputs) {
        inputs.percentOutput = motor.get();
        inputs.volts = motor.getBusVoltage() * motor.getAppliedOutput();
        inputs.amps = motor.getOutputCurrent();
        inputs.position = encoder.getPosition();
        inputs.velocity = encoder.getVelocity();
        inputs.ballAtSwitch = limitSwitch.isPressed();
    }

    @Override
    public void setPercent(double percent) {
        motor.set(percent);
    }

    public void setVoltage(double volts) {
        motor.setVoltage(volts);
    }
    @Override
    public void stop() {
        motor.set(0);
    }

    public boolean isSwitchPressed() {
        return limitSwitch.isPressed();
    }
}

