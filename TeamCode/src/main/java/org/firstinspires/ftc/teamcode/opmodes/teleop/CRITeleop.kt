package org.firstinspires.ftc.teamcode.opmodes.teleop

import com.acmerobotics.roadrunner.geometry.Vector2d
import com.arcrobotics.ftclib.command.CommandOpMode
import com.arcrobotics.ftclib.command.button.Trigger
import com.arcrobotics.ftclib.gamepad.GamepadEx
import com.arcrobotics.ftclib.gamepad.GamepadKeys
import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.commands.claw.SetClawPosition
import org.firstinspires.ftc.teamcode.commands.commandgroups.*
import org.firstinspires.ftc.teamcode.commands.drivetrain.DriveMecanumSnap
import org.firstinspires.ftc.teamcode.commands.general.MonitorRobotTelemetry
import org.firstinspires.ftc.teamcode.commands.turret.ControlTurretAngle
import org.firstinspires.ftc.teamcode.subsystems.ArmStatePositionData
import org.firstinspires.ftc.teamcode.subsystems.ClawPositions
import org.firstinspires.ftc.teamcode.subsystems.Robot
import kotlin.math.pow
import kotlin.math.sign

@TeleOp
@Disabled

class CRITeleop : CommandOpMode() {
    override fun initialize() {
        val robot = Robot(hardwareMap, telemetry)
        val driver = GamepadEx(gamepad1)
        val gunner = GamepadEx(gamepad2)

//        robot.elbow.isTelemetryEnabled = true

        schedule(MoveToTravel(robot))

        // drive with left joystick snapped to 0 degree heading
        robot.drivetrain.defaultCommand = DriveMecanumSnap(
            robot.drivetrain, 0.0,
            { driver.leftY.pow(2) * sign(driver.leftY) },
            { driver.leftX.pow(2) * sign(driver.leftX) },
        )

        // Move the turret to the angle to the right joystick
        val turretController = ControlTurretAngle(robot) {
            Vector2d(-driver.rightY, driver.rightX)
        }
        turretController.schedule(false)

        // turn on the telemetry monitoring of the robot
        MonitorRobotTelemetry(robot).schedule(false)

        // Intake position based on the DPAD
        val beamBreakTrigger = Trigger { robot.claw.holdingCone }

        beamBreakTrigger.whenActive(
            SetClawPosition(
                robot.claw,
                ClawPositions.HOLD_CONE
            ))

        // REGULAR SCORING

        // Score high with right trigger
        val triggerThreshold = 0.2

        val driverRightTrigger = Trigger { driver.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) > triggerThreshold }
        val driverLeftTrigger = Trigger { driver.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) > triggerThreshold }

        val driverRightBumper = driver.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
        val driverLeftBumper = driver.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)

        val driverTriangleButton = driver.getGamepadButton(GamepadKeys.Button.Y)
        val driverCircleButton = driver.getGamepadButton(GamepadKeys.Button.B)

        driverRightTrigger.whenActive(MoveToScoreHighJunction(robot)).whenInactive(DepositCone(robot))
        driverRightBumper.whenActive(MoveToScoreMediumJunction(robot)).whenInactive(DepositCone(robot))
        driverTriangleButton.whenActive(MoveToScoreLowJunction(robot)).whenInactive(DepositCone(robot))
        driverCircleButton.whenActive(MoveToScoreGroundJunction(robot)).whenInactive(DepositCone(robot))

        driverLeftBumper.whenActive(IntakeCone(robot, ArmStatePositionData.INTAKE))
        beamBreakTrigger.whenActive(PickupCone(robot))


        // CAPPING

//        Trigger { driver.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) > 0.2 }
//            .and(driverRightTrigger)
//            .whenActive(ScoreHighJunction(robot)).whenInactive(DepositTSEUnderCone(robot))
//
//        driver.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
//            .and(driverRightTrigger)
//            .whenActive(ScoreMediumJunction(robot)).whenInactive(DepositCone(robot))
//            .whenInactive(DepositTSEUnderCone(robot).interruptOn { driver.wasJustPressed(GamepadKeys.Button.LEFT_BUMPER) })
//
//        driver.getGamepadButton(GamepadKeys.Button.Y).whenHeld(ScoreLowJunction(robot))
//            .and(driverRightTrigger)
//            .whenInactive(DepositTSEUnderCone(robot).interruptOn { driver.wasJustPressed(GamepadKeys.Button.LEFT_BUMPER) })
//
//        driver.getGamepadButton(GamepadKeys.Button.B).whenHeld(ScoreGroundJunction(robot))
//            .and(driverRightTrigger)
//            .whenInactive(DepositTSEUnderCone(robot).interruptOn { driver.wasJustPressed(GamepadKeys.Button.LEFT_BUMPER) })
//


    }
}