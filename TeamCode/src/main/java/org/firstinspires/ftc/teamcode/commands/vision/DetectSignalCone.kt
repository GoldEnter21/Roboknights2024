package org.firstinspires.ftc.teamcode.commands.vision

import android.util.Size
import com.arcrobotics.ftclib.command.CommandBase
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.apriltags.ConeNumber
import org.firstinspires.ftc.teamcode.subsystems.Robot

class DetectSignalCone(val robot : Robot) : CommandBase() {
    private val timer = ElapsedTime()
    init {
        addRequirements(robot.signalSleeve)
        robot.signalSleeve.start(Size( 1920, 1080))
    }
    override fun initialize() {
        robot.detectedSignalCone = ConeNumber.NONE
        timer.reset()
    }
    override fun execute() {
        val detected = robot.signalSleeve.getSignalConeDetected()
        robot.telemetry.addData("Cone Detected", detected.toString())
        robot.telemetry.update()
        if (detected != ConeNumber.NONE)
            robot.detectedSignalCone = detected
    }
    override fun isFinished(): Boolean {
        return if(robot.detectedSignalCone != ConeNumber.NONE) {
            robot.signalSleeve.shutdown()
            true
        } else if(timer.seconds() > 120.0){
            robot.detectedSignalCone = ConeNumber.ONE
            robot.signalSleeve.shutdown()
            true
        } else false
    }
}