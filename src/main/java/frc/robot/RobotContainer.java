// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Autos;
import frc.robot.commands.ExampleCommand;
import frc.robot.subsystems.ExampleSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.subsystems.indexer.Indexer;
import frc.robot.subsystems.indexer.IndexerIOSparkMax;
import frc.robot.subsystems.shooter.Shooter;
import frc.robot.subsystems.shooter.ShooterConstants;
import frc.robot.subsystems.shooter.ShooterIOSparkMax;


/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final Indexer indexer = new Indexer(new IndexerIOSparkMax());
  private final Shooter shooter = new Shooter(new ShooterIOSparkMax());

  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandXboxController driverController = new CommandXboxController(0);

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
      driverController
          .rightTrigger()
          .whileTrue(
              Commands.parallel(
                   shooter.speedUp(),
                   Commands.sequence(
                        Commands.either(
                            indexer.indexUntilSwitch(),
                            Commands.none(),
                                () -> !indexer.hasBall()
                        ),
                        Commands.waitSeconds(ShooterConstants.SPINUP_SECONDS),
                        indexer.indexIntoShooter(),
                        Commands.either(
                            indexer.indexUntilSwitch(),
                            Commands.none(),
                            () -> !indexer.hasBall()
                        )
                    )
                     .repeatedly()
              )
          )
              .onFalse(Commands.parallel(shooter.stop(), indexer.stop()));

      
      /*
        Hold left trigger → run both shooter + indexer backwards.

        Release → stop both immediately.
       */
      driverController
            .leftTrigger()
              .whileTrue(
                      Commands.parallel(
                      shooter.reverse(),
                      indexer.reverse()))
              .onFalse(Commands.parallel(
                      shooter.stop(),
                      indexer.stop()
              ));





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
