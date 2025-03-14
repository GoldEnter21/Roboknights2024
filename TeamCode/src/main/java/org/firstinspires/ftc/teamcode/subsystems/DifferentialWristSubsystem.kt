package org.firstinspires.ftc.teamcode.subsystems

import com.arcrobotics.ftclib.command.SubsystemBase
import com.qualcomm.robotcore.hardware.Servo
import org.firstinspires.ftc.teamcode.utilities.AxonServo

class DifferentialWristSubsystem( val robot: Robot, private val leftServo: AxonServo, private val rightServo: AxonServo) : SubsystemBase() {

    val twistAngleRange = AngleRange(-90.0, 90.0)
    var twistAngleDegrees : Double = ArmStatePositionData.ARM_HOME.wrist.twistAngle
        set(value) {
            field = value.coerceIn(twistAngleRange.minimumAngle, twistAngleRange.maximumAngle)
        }

    val bendAngleRange = AngleRange(-135.0, 80.0)
    var bendAngleDegrees : Double = ArmStatePositionData.ARM_HOME.wrist.bendAngle
        set(value) {
            field = value.coerceIn(bendAngleRange.minimumAngle, bendAngleRange.maximumAngle)
        }

    var isTelemetryEnabled = false


    init {
        // reverse the right servo
        register()
    }

    override fun periodic() {
        var leftServoAngle = bendAngleDegrees - twistAngleDegrees / 2.0
        var rightServoAngle = bendAngleDegrees + twistAngleDegrees /2.0

        leftServo.angle = leftServoAngle
        rightServo.angle = rightServoAngle

        if(isTelemetryEnabled) {
            robot.telemetry.addLine("Differential Wrist: Telemetry Enabled")
            robot.telemetry.addData("Bend Angle:", bendAngleDegrees)
            robot.telemetry.addData("Twist Angle:", twistAngleDegrees)

            robot.telemetry.addData("Left Servo Angle:", leftServoAngle)
            robot.telemetry.addData("Right Servo Angle:", rightServoAngle)

            robot.telemetry.update()
        }
    }

}

data class AngleRange(val minimumAngle: Double, val maximumAngle: Double) {
    fun clampAngle(angle: Double) : Double {
        return angle.coerceIn(minimumAngle, maximumAngle)
    }
}