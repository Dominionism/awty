package edu.uw.ischool.cainglet.nagger

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsManager
import android.widget.Toast

@Suppress("DEPRECATION")
class AlarmManagerBroadcast : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val message = intent.getStringExtra("messageText")
        val phoneNumber = intent.getStringExtra("phoneNumber")

        if (message != null && phoneNumber != null) {
            try {
                val smsManager = SmsManager.getDefault()
                smsManager.sendTextMessage(phoneNumber, null, message, null, null)
                Toast.makeText(context, "Message sent to $phoneNumber: $message", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(context, "Failed to send message", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(context, "Missing data", Toast.LENGTH_SHORT).show()
        }
    }
}
