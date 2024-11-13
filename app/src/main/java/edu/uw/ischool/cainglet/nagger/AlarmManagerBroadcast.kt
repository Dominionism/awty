package edu.uw.ischool.cainglet.nagger

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class AlarmManagerBroadcast : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val message = intent.getStringExtra("messageText")
        val phoneNumber = intent.getStringExtra("phoneNumber")

        if (message != null && phoneNumber != null) {
            Toast.makeText(context, "$phoneNumber: $message", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Missing data", Toast.LENGTH_SHORT).show()
        }
    }
}
