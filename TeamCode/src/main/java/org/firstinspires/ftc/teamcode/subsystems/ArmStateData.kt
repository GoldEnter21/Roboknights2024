package org.firstinspires.ftc.teamcode.subsystems

data class ArmStateData(
    val wrist: WristStateData,
    val elbow: ElbowStateData,
    val extension: ExtensionStateData,
    val aligner: PoleAlignerStateData,
    val claw: ClawStateData)
data class ArmAndTurretStateData(
    val arm: ArmStateData,
    val turret: TurretStateData)
data class TurretStateData(
    val angle: Double)
data class WristStateData(
    val bendAngle: Double,
    val twistAngle: Double,
    val depositBendAngle : Double = 0.0)

data class ElbowStateData(
    val angle: Double)

data class ExtensionStateData(
    val length: Double)

data class PoleAlignerStateData(
    val angle: Double)

data class ClawStateData(
    val angle : Double)
public class ArmStatePositionData() {
    companion object {
        val CLAW_OPEN_FOR_INTAKE = ClawStateData(0.0)
        val CLAW_HOLD_CONE = ClawStateData(-25.0)
        val CLAW_RELEASE_CONE_BUT_HOLD_TSE = ClawStateData(-18.0)

        val ARM_HOME = ArmStateData(
            WristStateData(0.0, 0.0),
            ElbowStateData(0.0),
            ExtensionStateData(0.0),
            PoleAlignerStateData(-90.0),
            CLAW_OPEN_FOR_INTAKE
        )
        val START_LEFT = ArmAndTurretStateData(
            ArmStateData(
                ARM_HOME.wrist,
                ElbowStateData(-45.0),
                ARM_HOME.extension,
                ARM_HOME.aligner,
                CLAW_HOLD_CONE
            ),
            TurretStateData(-53.0)
        )
        val START_RIGHT = ArmAndTurretStateData(
            ArmStateData(
                ARM_HOME.wrist,
                ElbowStateData(-50.5),
                ARM_HOME.extension,
                ARM_HOME.aligner,
                CLAW_HOLD_CONE
            ),
            TurretStateData(55.0)
        )
        val SCORE_HIGH = ArmStateData(
            WristStateData(-2.0, 0.0, 34.0),

            ElbowStateData(58.0),
            ExtensionStateData(11.6),
            PoleAlignerStateData(8.0),
            CLAW_HOLD_CONE
        )
        val SCORE_MEDIUM = ArmStateData(
            WristStateData(-12.0, 0.0, 27.0),
            ElbowStateData(35.0),
            ExtensionStateData(0.0),
            PoleAlignerStateData(37.0),
            CLAW_HOLD_CONE
        )
        val SCORE_LOW = ArmStateData(
            WristStateData(-10.0, 0.0, 7.0),
            ElbowStateData(3.0),
            ExtensionStateData(0.0),
            ARM_HOME.aligner,
            CLAW_HOLD_CONE
        )
        var INTAKE = ArmStateData(
            WristStateData(-37.0, 0.0),
            ElbowStateData(-45.0),
            ExtensionStateData(5.25),
            PoleAlignerStateData(ARM_HOME.aligner.angle),
            CLAW_OPEN_FOR_INTAKE
        )
        val SCORE_GROUND = ArmStateData(
            WristStateData(-37.0, 0.0, -37.0),
            ElbowStateData(-49.0),
            ExtensionStateData(4.25),
            PoleAlignerStateData(ARM_HOME.aligner.angle),
            CLAW_HOLD_CONE
        )
        val TRAVEL = ArmStateData(
            WristStateData(-110.0, 0.0),
            ElbowStateData(45.0),
            ExtensionStateData(0.0),
            PoleAlignerStateData(ARM_HOME.aligner.angle),
            CLAW_HOLD_CONE
        )
        val INTERMEDIATE = ArmStateData(
            WristStateData(-110.0, 0.0),
            ElbowStateData(45.0),
            ExtensionStateData(8.0),
            PoleAlignerStateData(ARM_HOME.aligner.angle),
            CLAW_HOLD_CONE
        )
        val INTAKE_REAR = ArmAndTurretStateData(
            INTAKE,
            TurretStateData(180.0)
        )
        val INTAKE_FRONT = ArmAndTurretStateData(
            INTAKE,
            TurretStateData(0.0)
        )
        val INTAKE_RIGHT = ArmAndTurretStateData(
            INTAKE,
            TurretStateData(90.0)
        )
        val INTAKE_LEFT = ArmAndTurretStateData(
            INTAKE,
            TurretStateData(-90.0)
        )
    }
}