package frc.robot.subsystems.shooter;

import static com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor.kPrimaryEncoder;
import static frc.robot.subsystems.shooter.ShooterConstants.*;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.ClosedLoopConfig;
import com.revrobotics.spark.config.EncoderConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;

public class ShooterIOSparkMax implements ShooterIO {

    private final SparkMax motor;
    private final RelativeEncoder encoder;

    private final SparkClosedLoopController controller;

    private double setpointRPM;
    private SimpleMotorFeedforward feedforward;



    public ShooterIOSparkMax() {
        motor = new SparkMax(SHOOTER_MOTOR_ID, MotorType.kBrushless);
        controller = motor.getClosedLoopController();
        var config  = new SparkMaxConfig();
        config.inverted(false)
                .idleMode(IdleMode.kBrake)
                .voltageCompensation(12)
                .smartCurrentLimit(30);

        var loopConfig = new ClosedLoopConfig();
        loopConfig
                .feedbackSensor(kPrimaryEncoder)
                .positionWrappingEnabled(false)
                .pid(0.000009,0.0000003,0.0001);// TODO: tune PIDF values


        EncoderConfig enc = new EncoderConfig();
        enc.velocityConversionFactor(ENCODER_VELOCITY_CONVERSION);
        config.apply(enc);
        config.apply(loopConfig);

        motor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        encoder = motor.getEncoder();

    }

    @Override
    public void updateInputs(ShooterIOInputs inputs) {
        inputs.temperature = motor.getMotorTemperature();
        inputs.appliedVolts = motor.getBusVoltage() * motor.getAppliedOutput();
        inputs.currentAmps = motor.getOutputCurrent();
        inputs.velocity = encoder.getVelocity();// RPM
        inputs.setpointRPM = setpointRPM;
        inputs.atSetpoint = Math.abs(setpointRPM - inputs.velocity) < TOLERANCE; // TODO: change tolerance

    }

    public void setPercent(double percent) {
        motor.set(percent);
    }

    public void setVoltage(double volts) {
        motor.setVoltage(volts);
    }
    public double returnVelocity(){
        return this.encoder.getVelocity();
    }

    @Override
    public void setVelocity(double setpointRPM) {
        this.setpointRPM = setpointRPM;
    }

    @Override
    public void goToSetpoint(){
        controller.setReference(this.setpointRPM, SparkBase.ControlType.kVelocity);
    }

    public void stop() {
        motor.set(0);
    }
}
