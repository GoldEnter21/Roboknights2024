package org.firstinspires.ftc.teamcode.opmodes.auto

import com.acmerobotics.roadrunner.geometry.Pose2d
import com.arcrobotics.ftclib.command.CommandOpMode
import com.arcrobotics.ftclib.command.ParallelCommandGroup
import com.arcrobotics.ftclib.command.SequentialCommandGroup
import com.arcrobotics.ftclib.command.WaitCommand
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import org.firstinspires.ftc.teamcode.commands.claw.ClawRegripCone
import org.firstinspires.ftc.teamcode.commands.commandgroups.MoveToTravel
import org.firstinspires.ftc.teamcode.commands.drivetrain.TrajectoryCommand
import org.firstinspires.ftc.teamcode.commands.general.ConfigurableCommand
import org.firstinspires.ftc.teamcode.commands.general.UpdateTelemetry
import org.firstinspires.ftc.teamcode.commands.turret.SetTurretAngle
import org.firstinspires.ftc.teamcode.commands.vision.DetectSignalCone
import org.firstinspires.ftc.teamcode.opmodes.auto.commands.AlliancePosition
import org.firstinspires.ftc.teamcode.opmodes.auto.commands.DepositMidPoleAuto
import org.firstinspires.ftc.teamcode.opmodes.auto.commands.IntakeFromConeStack
import org.firstinspires.ftc.teamcode.opmodes.auto.commands.MoveToAutoScoringPosition
import org.firstinspires.ftc.teamcode.opmodes.auto.commands.Park
import org.firstinspires.ftc.teamcode.subsystems.ArmStatePositionData
import org.firstinspires.ftc.teamcode.subsystems.ClawPositions
import org.firstinspires.ftc.teamcode.subsystems.OpModeType
import org.firstinspires.ftc.teamcode.subsystems.Robot

@Autonomous
class LokiAutoRightSide : CommandOpMode() {

    override fun initialize() {


        val robot = Robot(hardwareMap, telemetry, OpModeType.AUTONOMOUS)
        val alliancePosition = AlliancePosition.RIGHT

        // Coordinate System: +x is forward (away from driver station), +y is left, +theta is counter-clockwise
        // (0,0) is the center of the field.  0.0 radians heading is directly away from the driver station along the +x axis
        // this is where we map the robot coordinate system to the field coordinate system
        val startPose = Pose2d(-62.0, -31.0, 0.0)
        robot.drivetrain.poseEstimate = startPose

        // Move the wrist/claw/extension to the right starting position
        // open the claw waiting for a preloaded cone.  The claw will close when the cone is detected and open if is not detected
//        robot.extension.targetLength = ArmStatePositionData.ARM_HOME.extension.length
        robot.wrist.bendAngleDegrees = ArmStatePositionData.ARM_HOME.wrist.bendAngle
        robot.wrist.twistAngleDegrees = ArmStatePositionData.ARM_HOME.wrist.twistAngle
        robot.claw.position = ClawPositions.OPEN_FOR_INTAKE
        schedule(
                DetectSignalCone(robot)
                        .andThen(UpdateTelemetry(robot){ telemetry ->
                            telemetry.addData("Detected Cone", robot.detectedSignalCone)
                        })
        )
        // schedule the commands for Auto

//        ParallelCommandGroup(
//            MoveToTravel(robot),
//            SetTurretAngle(robot.turret,135.0)
//        ).schedule(false)

        val autoCommands = SequentialCommandGroup(
                ParallelCommandGroup(
                        MoveToAutoScoringPosition(robot, alliancePosition),
                        MoveToTravel(robot),
                        SetTurretAngle(robot.turret, 135.0),
                ),

                ClawRegripCone(robot),
                WaitCommand(125),
                DepositMidPoleAuto(robot, alliancePosition),
                IntakeFromConeStack(robot, alliancePosition, 5),
                DepositMidPoleAuto(robot, alliancePosition),
                IntakeFromConeStack(robot, alliancePosition, 4),
                DepositMidPoleAuto(robot, alliancePosition),
                IntakeFromConeStack(robot, alliancePosition, 3),
                DepositMidPoleAuto(robot, alliancePosition),
                IntakeFromConeStack(robot, alliancePosition, 2),
                DepositMidPoleAuto(robot, alliancePosition),
                IntakeFromConeStack(robot, alliancePosition, 1),
                DepositMidPoleAuto(robot, alliancePosition),
                ParallelCommandGroup(
                        Park(robot, alliancePosition),
                        MoveToTravel(robot),
                        SetTurretAngle(robot.turret, 180.0),
                )
        )


        schedule(autoCommands)

        while (robot.claw.position != ClawPositions.HOLD_CONE) {
            if(robot.claw.holdingCone)
                robot.claw.position = ClawPositions.HOLD_CONE
            sleep(100)
        }
    }
}