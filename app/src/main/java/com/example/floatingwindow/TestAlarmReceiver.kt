package com.example.floatingwindow

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.net.Uri
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.eslam.efficientalarm.utils.Constants






class TestAlarmReceiver() : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val newIntent = Intent(context, FloatingWindowService::class.java)

        if (intent.action == Constants.BOOT_COMPLETED || intent.action==Constants.QUICKBOOT_POWERON) {
            intent.putExtra("t","t")
            Log.d("sdsdsdsdsdsds", "onReceive: ")
//            NewAlarmActivity.setAlarm()
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                context.startForegroundService(newIntent)
            } else {
                context.startService(newIntent)
            }
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            context.startForegroundService(newIntent)
        } else {
            context.startService(newIntent)
        }
    }
}