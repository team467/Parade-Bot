// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Autos;
import frc.robot.commands.ExampleCommand;
import frc.robot.subsystems.drive.*;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.subsystems.indexer.Indexer;
import frc.robot.subsystems.indexer.IndexerIOSparkMax;
import frc.robot.subsystems.shooter.Shooter;
import frc.robot.subsystems.shooter.ShooterIOSparkMax;


/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final Drive drive;
    // The robot's subsystems and commands are defined here...
    private final Indexer indexer = new Indexer(new IndexerIOSparkMax());
    private final Shooter shooter = new Shooter(new ShooterIOSparkMax());
    private final Orchestrator orchestrator = new Orchestrator(indexer, shooter);
    private final CommandXboxController driverController = new CommandXboxController(0);
    private boolean fastMode = false;
    private final Trigger fastModeTrigger = new Trigger(() -> fastMode);


  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    drive = new Drive(new DriveIOSparkMax());
    configureBindings();
  }
    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer() {
        // Configure the trigger bindings
        configureBindings();
    }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    drive.setDefaultCommand(drive.arcadeDrive(
            driverController::getLeftY,
            driverController::getRightY));
  }
  public Command getAutonomousCommand() {
    return Commands.none();
  }
    /**
     * Use this method to define your trigger->command mappings. Triggers can be created via the
     * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
     * predicate, or via the named factories in {@link
     * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
     * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
     * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
     * joysticks}.
     */

    private void configureBindings() {
        driverController.a().onTrue(Commands.runOnce(() -> fastMode = !fastMode));

        fastModeTrigger.whileTrue(
                edu.wpi.first.wpilibj2.command.Commands.startEnd(
                        () -> driverController.getHID()
                                .setRumble(edu.wpi.first.wpilibj.GenericHID.RumbleType.kBothRumble, 1.0),
                        () -> driverController.getHID()
                                .setRumble(edu.wpi.first.wpilibj.GenericHID.RumbleType.kBothRumble, 0.0)
                )
        );

        driverController
                .rightTrigger()
                .whileTrue(orchestrator.shootCycle(() -> fastMode))
                .onFalse(orchestrator.stopAll());
        driverController.y().whileTrue(shooter.speedUp()).onFalse(shooter.stop());
        driverController
                .leftTrigger()
                .whileTrue(orchestrator.reverseAll())
                .onFalse(orchestrator.stopAll());

        driverController
                .rightBumper()
                .onTrue(orchestrator.shootOnce(() -> fastMode))
                .onFalse(orchestrator.stopAll());

    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */

    public Command getAutonomousCommand() {
        // An example command will be run in autonomous
        return Commands.none();
    }
}