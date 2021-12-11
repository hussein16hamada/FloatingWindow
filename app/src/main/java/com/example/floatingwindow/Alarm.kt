package com.example.floatingwindow

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build

fun setAlarm(
    context: Context ,
    timeToTrigger: Int,
    requestId: Int = 24,
) {

    if (timeToTrigger==-1){
        cancelReminderAlarm(context,requestId)

        return
    }
    val manager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val receiver = ComponentName(context, TestAlarmReceiver::class.java)
    val pm = context.packageManager



    // Set up the Notification Broadcast Intent.

    pm.setComponentEnabledSetting(
        receiver,
        PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
        PackageManager.DONT_KILL_APP
    )
    // TODO fore multi alarms use different id for etch one
    val pendingIntent = PendingIntent.getBroadcast(
        context, requestId, Intent(context, TestAlarmReceiver::class.java)
            .addFlags(Intent.FLAG_RECEIVER_FOREGROUND)
        // TODO change hte flag to one shot
        , PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )
    //
    when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ->
            manager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP
                ,  System.currentTimeMillis()+(timeToTrigger*60*1000), pendingIntent)


//                Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP -> manager.setAlarmClock(AlarmManager.AlarmClockInfo(
//                    System.currentTimeMillis()+(timeToTrigger*60*1000), pendingIntent), pendingIntent)
//                Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP -> manager.setInexactRepeating(
//                    AlarmManager.RTC_WAKEUP,
//                    timeToTrigger,
//                    repeat,
//                    pendingIntent
//                )
//                Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT -> manager.setRepeating(
//                    AlarmManager.RTC_WAKEUP,
//                    timeToTrigger,
//                    repeat,
//                    pendingIntent
//                )
        else -> manager.setExact(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis()+(timeToTrigger*60*1000),
            pendingIntent
        )
    }
}


fun cancelReminderAlarm(context: Context, id :Int) {
    var pIntent: PendingIntent?=null
    val intent = Intent(context, TestAlarmReceiver::class.java)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        pIntent = PendingIntent.getBroadcast(
            context,
            id,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
    } else {
        pIntent = PendingIntent.getBroadcast(
            context,
            id,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    val manager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    manager.cancel(pIntent!!)
}
