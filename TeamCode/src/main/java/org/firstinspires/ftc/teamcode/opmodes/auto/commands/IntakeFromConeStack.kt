package org.firstinspires.ftc.teamcode.opmodes.auto.commands

import com.arcrobotics.ftclib.command.*
import org.firstinspires.ftc.teamcode.Cons
import org.firstinspires.ftc.teamcode.Cons.EASING
import org.firstinspires.ftc.teamcode.commands.claw.SetClawPosition
import org.firstinspires.ftc.teamcode.commands.commandgroups.MinimalStowFromStackToPole
import org.firstinspires.ftc.teamcode.commands.elbow.SetElbowAngle
import org.firstinspires.ftc.teamcode.commands.extension.SetExtensionLinkage
import org.firstinspires.ftc.teamcode.commands.general.ConfigurableCommandBase
import org.firstinspires.ftc.teamcode.commands.scoring.SetAligner
import org.firstinspires.ftc.teamcode.commands.turret.SetTurretAngle
import org.firstinspires.ftc.teamcode.commands.wrist.SetWristAngles
import org.firstinspires.ftc.teamcode.subsystems.*
import org.firstinspires.ftc.teamcode.subsystems.ArmStatePositionData.Companion.ARM_HOME
import org.firstinspires.ftc.teamcode.subsystems.Robot

class IntakeFromConeStack(
    val robot: Robot,
    val alliancePosition: AlliancePosition,
    val coneNumber: Int
) : ConfigurableCommandBase() {
    override fun configure(): CommandBase {
        val turretState = TurretStateData(-93.0)
        val postPickupBendAngleOffset = -75.0
        val armAndTurretState = when (alliancePosition) {

            AlliancePosition.LEFT -> when (coneNumber) {

                5 -> ArmAndTurretStateData(
                    ArmStateData(
                        WristStateData(-5.0, 0.0, 0.0),
                        ElbowStateData(-19.0),
                        ExtensionStateData(11.85),
                        PoleAlignerStateData(ARM_HOME.aligner.angle),
                        ArmStatePositionData.CLAW_OPEN_FOR_INTAKE
                    ),
                    turretState
                )

                4 -> ArmAndTurretStateData(
                    ArmStateData(
                        WristStateData(-10.0, 0.0, 0.0),
                        ElbowStateData(-23.0),
                        ExtensionStateData(12.0),
                        PoleAlignerStateData(ARM_HOME.aligner.angle),
                        ArmStatePositionData.CLAW_OPEN_FOR_INTAKE
                    ),
                    turretState
                )

                3 -> ArmAndTurretStateData(
                    ArmStateData(
                        WristStateData(-12.0, 0.0, 0.0),
                        ElbowStateData(-26.0),
                        ExtensionStateData(12.45),
                        PoleAlignerStateData(ARM_HOME.aligner.angle),
                        ArmStatePositionData.CLAW_OPEN_FOR_INTAKE
                    ),
                    turretState
                )

                2 -> ArmAndTurretStateData(
                    ArmStateData(
                        WristStateData(-12.0, 0.0, 0.0),
                        ElbowStateData(-28.0),
                        ExtensionStateData(12.75),
                        PoleAlignerStateData(ARM_HOME.aligner.angle),
                        ArmStatePositionData.CLAW_OPEN_FOR_INTAKE
                    ),
                    turretState
                )

                1 -> ArmAndTurretStateData(
                    ArmStateData(
                        WristStateData(-15.0, 0.0, 0.0),
                        ElbowStateData(-32.0),
                        ExtensionStateData(13.0),
                        PoleAlignerStateData(ARM_HOME.aligner.angle),
                        ArmStatePositionData.CLAW_OPEN_FOR_INTAKE
                    ),
                    turretState
                )

                else -> throw IllegalStateException()
            }
            AlliancePosition.RIGHT -> when (coneNumber) {

                5 -> ArmAndTurretStateData(
                        ArmStateData(
                                WristStateData(-5.0, 0.0, 0.0),
                                ElbowStateData(-19.2),
                                ExtensionStateData(11.85),
                                PoleAlignerStateData(ARM_HOME.aligner.angle),
                                ArmStatePositionData.CLAW_OPEN_FOR_INTAKE
                        ),
                        TurretStateData(-turretState.angle)
                )

                4 -> ArmAndTurretStateData(
                        ArmStateData(
                                WristStateData(-10.0, 0.0, 0.0),
                                ElbowStateData(-23.0),
                                ExtensionStateData(12.0),
                                PoleAlignerStateData(ARM_HOME.aligner.angle),
                                ArmStatePositionData.CLAW_OPEN_FOR_INTAKE
                        ),
                        TurretStateData(-turretState.angle)
                )

                3 -> ArmAndTurretStateData(
                        ArmStateData(
                                WristStateData(-12.0, 0.0, 0.0),
                                ElbowStateData(-26.0),
                                ExtensionStateData(12.45),
                                PoleAlignerStateData(ARM_HOME.aligner.angle),
                                ArmStatePositionData.CLAW_OPEN_FOR_INTAKE
                        ),
                        TurretStateData(-turretState.angle)
                )

                2 -> ArmAndTurretStateData(
                        ArmStateData(
                                WristStateData(-12.0, 0.0, 0.0),
                                ElbowStateData(-28.0),
                                ExtensionStateData(12.75),
                                PoleAlignerStateData(ARM_HOME.aligner.angle),
                                ArmStatePositionData.CLAW_OPEN_FOR_INTAKE
                        ),
                        TurretStateData(-turretState.angle)
                )

                1 -> ArmAndTurretStateData(
                        ArmStateData(
                                WristStateData(-15.0, 0.0, 0.0),
                                ElbowStateData(-32.0),
                                ExtensionStateData(13.0),
                                PoleAlignerStateData(ARM_HOME.aligner.angle),
                                ArmStatePositionData.CLAW_OPEN_FOR_INTAKE
                        ),
                        TurretStateData(-turretState.angle)
                )

                else -> throw IllegalStateException()
            }



            AlliancePosition.CENTER_RIGHT, AlliancePosition.CENTER_LEFT -> throw NotImplementedError()

            else -> throw IllegalStateException()
        }

        val extensionPickupOffset = 2.5
        val elbowAnglePickupOffset = 15.0

        //SetClawPosition(robot.claw, ClawPositions.OPEN_FOR_INTAKE)
//            SetElbowAngle(robot.elbow, armAndTurretState.arm.elbow.angle + elbowAnglePickupOffset),
//            SetExtensionLinkage(robot.extension, ArmStatePositionData.INTERMEDIATE.extension.length, Cons     .EASING),
        return SequentialCommandGroup(
            ParallelCommandGroup(
                SetClawPosition(robot.claw, ClawPositions.OPEN_FOR_INTAKE),
                SetTurretAngle(robot.turret, armAndTurretState.turret.angle),
                SetExtensionLinkage(robot.extension, (armAndTurretState.arm.extension.length-extensionPickupOffset)),
                SetWristAngles(robot.wrist, armAndTurretState.arm.wrist.bendAngle, armAndTurretState.arm.wrist.twistAngle),
            ),
            WaitCommand(75),
            SetElbowAngle(robot.elbow, armAndTurretState.arm.elbow.angle),
            WaitCommand(125),
            ConditionalCommand(
                SetExtensionLinkage(robot.extension, armAndTurretState.arm.extension.length),
                WaitCommand(50)
            ) { !robot.claw.holdingCone },
            CloseClawOnBeamBreak(robot),
            WaitCommand(75),
            ParallelCommandGroup(
                SetWristAngles(robot.wrist, armAndTurretState.arm.wrist.bendAngle+postPickupBendAngleOffset, 0.0),
                SetElbowAngle(robot.elbow, 0.0)
                    ),
//            WaitCommand(75)
        )

    }
}