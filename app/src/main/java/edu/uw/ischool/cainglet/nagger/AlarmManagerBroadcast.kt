package edu.uw.ischool.cainglet.nagger

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class AlarmManagerBroadcast : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Toast.makeText(context, "(425) 555-1212: Are we there yet?", Toast.LENGTH_SHORT).show()
    }
}
