package edu.uw.ischool.cainglet.nagger

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.Manifest
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
// 4256865518
class MainActivity : AppCompatActivity() {
    private lateinit var message: EditText
    private lateinit var phoneNumber: EditText
    private lateinit var nagTimeInput: EditText
    private lateinit var startStopButton: Button
    private lateinit var alarmManager: AlarmManager
    private var isAlarmSet = false
    private val permissionCode = 123

    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        message = findViewById(R.id.message_box)
        phoneNumber = findViewById(R.id.phone_number)
        nagTimeInput = findViewById(R.id.nag_timer)
        startStopButton = findViewById(R.id.start_stop_button)

        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.SEND_SMS), permissionCode)
        }

        startStopButton.setOnClickListener {
            val messageText = message.text.toString()
            val phoneNumberText = phoneNumber.text.toString()
            val timeText = nagTimeInput.text.toString()

            if (messageText.isNotBlank() && phoneNumberText.isNotBlank() && timeText.isNotBlank()) {
                val time = timeText.toIntOrNull()

                if (time != null && time > 0) {
                    val intent = Intent(this, AlarmManagerBroadcast::class.java)
                    intent.putExtra("phoneNumber", phoneNumberText)
                    intent.putExtra("messageText", messageText)

                    val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

                    if (isAlarmSet) {
                        alarmManager.cancel(pendingIntent)
                        Toast.makeText(this, "Alarm stopped", Toast.LENGTH_SHORT).show()
                        startStopButton.text = "Start"
                        isAlarmSet = false
                    } else {
                        alarmManager.setRepeating(
                            AlarmManager.RTC_WAKEUP,
                            System.currentTimeMillis() + (time * 60 * 1000),
                            (time * 60 * 1000).toLong(),
                            pendingIntent
                        )
                        Toast.makeText(this, "Alarm set to repeat every $time minutes", Toast.LENGTH_SHORT).show()
                        startStopButton.text = "Stop"
                        isAlarmSet = true
                    }
                } else {
                    Toast.makeText(this, "Please enter a valid time", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == permissionCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "SMS permission granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "SMS permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
