package com.waljasov.alexander.sonsordatacollector

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import android.util.Log
import android.widget.TextView

class MainActivity : WearableActivity() {
    private lateinit var gyroscopeSensorTextView: TextView;
    private lateinit var accelerometerSensorTextView: TextView;
    private lateinit var sensorManager: SensorManager
    private lateinit var gyroscopeSensor: Sensor
    private lateinit var accelerometerSensor: Sensor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Enables Always-on
        setAmbientEnabled()

        accelerometerSensorTextView = findViewById<TextView>(R.id.text);
        gyroscopeSensorTextView = findViewById<TextView>(R.id.text2);

        // Get instance of the sensor manager
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager;

        val deviceSensors: List<Sensor> = sensorManager.getSensorList(Sensor.TYPE_ALL)
        Log.v("sensors:","" + deviceSensors.size)
        deviceSensors.forEach{
            Log.v("sensor name","" + it )
        }

        // Use the accelerometer
        if(sensorExists(Sensor.TYPE_ACCELEROMETER)) {
            accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
            sensorManager.registerListener(accelerometerListener, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL)
        }

        // Use the gyroscope
        if(sensorExists(Sensor.TYPE_GYROSCOPE)) {
            gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
            sensorManager.registerListener(gyroscopeListener, gyroscopeSensor, SensorManager.SENSOR_DELAY_NORMAL)
        }

        // Use the gravity sensor
        //gravitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY)

        // Use the linear accelerometer
        //linearAccelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)

        // Use the rotation vector sensor
        //vectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)
    }

    private fun sensorExists(sensorId: Int): Boolean {
        if (sensorManager.getDefaultSensor(sensorId) != null) {
            // Success!
            Log.v("success","yes")
            return true
        }
        Log.v("Failure","No sensor found")
        return false
    }

    private val accelerometerListener: SensorEventListener = object : SensorEventListener {
        override fun onAccuracyChanged(arg0: Sensor, arg1: Int) {}
        override fun onSensorChanged(event: SensorEvent) {
            val reading = ("accelerometer\n" + "X: " + String.format("%1.4f", event.values[0]) + " m/s²\nY: "
                    + String.format("%1.4f", event.values[1]) + " m/s²\nZ: "
                    + String.format("%1.4f", event.values[2]) + " m/s²")

            accelerometerSensorTextView.text = reading;
        }
    }

    private val gyroscopeListener: SensorEventListener = object : SensorEventListener {
        override fun onAccuracyChanged(arg0: Sensor, arg1: Int) {}
        override fun onSensorChanged(event: SensorEvent) {
            val reading = ("gyroscope\n" + "Z: " + String.format("%1.4f", event.values[0]) + " m/s²\nY: "
                    + String.format("%1.4f", event.values[1]) + " m/s²\nX: "
                    + String.format("%1.4f", event.values[2]) + " m/s²")

            gyroscopeSensorTextView.text = reading;
        }
    }
}
