package org.firstinspires.ftc.teamcode.utilities

import com.qualcomm.robotcore.hardware.AnalogInput
import com.qualcomm.robotcore.hardware.CRServoImplEx
import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.hardware.PwmControl.PwmRange
import kotlin.math.abs
import kotlin.math.sign


class AxonCRServo(
    val servo: CRServoImplEx,
    val analogInput: AnalogInput,
    val min: Double,
    val max: Double
) {
    constructor(
        hw: HardwareMap,
        servoName: String?,
        analogName: String?,
        min: Double,
        max: Double):this(
        servo = hw.get(CRServoImplEx::class.java, servoName),
        analogInput = hw.get(AnalogInput::class.java, analogName), min, max)

    init {
        servo.pwmRange = PwmRange(min, max)
    }

    val analogOutput: Double
        get() = analogInput.voltage / 3.3 * 360
    var target = 0.0
    var position = analogOutput
    var lastPosition = 0.0
        private set
    var lastReading = position
        private set
    fun setPower(power: Double) {
        servo.power = power
    }

    fun update(){
        val encoderReading = analogOutput
        var positionDelta = encoderReading - lastReading
        if(abs(positionDelta) >= 180){
            positionDelta = (360 - abs(positionDelta)) * -sign(positionDelta)
        }
        position += positionDelta
        lastReading = encoderReading
        lastPosition = position
    }

}