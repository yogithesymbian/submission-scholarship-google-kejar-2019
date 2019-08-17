/*
 * Copyright (c) 2019. SCODEID
 */

package com.scodeid.scholarshipexpertscodeidev2019.broadcast

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.scodeid.scholarshipexpertscodeidev2019.R
import com.scodeid.scholarshipexpertscodeidev2019.homeInitView.MovieCatalogueMainActivity
import com.scodeid.scholarshipexpertscodeidev2019.homeInitView.MovieCatalogueMainActivity.Companion.bitmap
import com.scodeid.scholarshipexpertscodeidev2019.homeInitView.MovieCatalogueMainActivity.Companion.posterToBitmap
import com.scodeid.scholarshipexpertscodeidev2019.notification.ComingSoonActivity
import com.scodeid.scholarshipexpertscodeidev2019.utils.*
import com.scodeid.yomoviecommon.utils.debuggingMyScode
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author
 * Created by scode on 17,August,2019
 * Yogi Arif Widodo
 * www.dicoding.com/users/297307
 * www.github.com/yogithesymbian
 * SCODEID company,
 * Indonesia, East Borneo.
 * ==============================================================
 * Android Studio 3.4.2
 * Build #AI-183.6156.11.34.5692245, built on June 27, 2019
 * JRE: 1.8.0_152-release-1343-b16-5323222 amd64
 * JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
 * Linux 4.19.0-kali5-amd64
 * ==============================================================
_               _         _               ____
___ _   _| |__  _ __ ___ (_)___ ___(_) ___  _ __   | ___|
/ __| | | | '_ \| '_ ` _ \| / __/ __| |/ _ \| '_ \  |___ \
\__ \ |_| | |_) | | | | | | \__ \__ \ | (_) | | | |  ___) |
|___/\__,_|_.__/|_| |_| |_|_|___/___/_|\___/|_| |_| |____/

 */

class NotificationReceiver : BroadcastReceiver() {

    companion object {
        private const val TAG_LOG: String = "NotReceiver"
        const val TYPE_RELEASE_MOVIE = "RepeatingAlarmRelease"
        const val TYPE_TOKEN_BACK_APP = "Daily Reminder"

        const val EXTRA_MESSAGE = "message"
        const val EXTRA_TYPE = "extra_type"

        private const val ID_RELEASE_MOVIE = 100
        private const val ID_TOKEN_BACK_APP = 101

    }

    private val timeFormat = "HH:mm"

    override fun onReceive(context: Context, intent: Intent?) {

        val type = intent?.getStringExtra(EXTRA_TYPE)
        val message = intent?.getStringExtra(EXTRA_MESSAGE)
        val title = if (type.equals(TYPE_RELEASE_MOVIE, ignoreCase = true)) TYPE_RELEASE_MOVIE else TYPE_TOKEN_BACK_APP
        val notifId = if (type.equals(TYPE_RELEASE_MOVIE, ignoreCase = true)) ID_RELEASE_MOVIE else ID_TOKEN_BACK_APP

        showToast(context, title, message)
        message?.let { showNotification(context, title, it, notifId) }

    }

    private fun showToast(context: Context, title: String, message: String?) {
        Toast.makeText(context, "$title : $message", Toast.LENGTH_LONG).show()
    }

    // measure what checkUtilsTimes
    private fun isDateInvalid(date: String, format: String): Boolean {
        return try {
            val df = SimpleDateFormat(format, Locale.getDefault())
            df.isLenient = false
            df.parse(date)
            false
        } catch (e: ParseException) {
            true
        }
    }

    private fun showNotification(context: Context, title: String, message: String, notifId: Int) {

        val channelId: String
        val channelName: String

        val intent: Intent
        if (notifId == ID_TOKEN_BACK_APP) {
            channelId = CHANNEL_DAILY_ID_TOKEN
            channelName = CHANNEL_DAILY_NAME_TOKEN
            intent = Intent(context, ComingSoonActivity::class.java)
            debuggingMyScode(TAG_LOG, "1 $channelId")
        } else {
            channelId = CHANNEL_DAILY_ID_RELEASE
            channelName = CHANNEL_DAILY_NAME_RELEASE
            intent = Intent(context, MovieCatalogueMainActivity::class.java)
            debuggingMyScode(TAG_LOG, "2 $channelId")
        }

        debuggingMyScode(TAG_LOG, channelId)

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent,
            PendingIntent.FLAG_ONE_SHOT
        )

        val notificationManagerCompat = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_menu_notifications_black_24dp)
            .setContentTitle(title)
            .setContentText(message)
            .setChannelId(channelId)
            .setColor(ContextCompat.getColor(context, android.R.color.transparent))
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setSound(alarmSound)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        if (notifId == ID_RELEASE_MOVIE) {
            builder.setLargeIcon(bitmap)
            Glide.with(context).clear(posterToBitmap)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)
            builder.setChannelId(channelId)
            notificationManagerCompat.createNotificationChannel(channel)
        }
        val notification = builder.build()
        notificationManagerCompat.notify(notifId, notification)
    }

    fun setRepeatingNotification(context: Context, type: String, time: String, message: String) {

        if (isDateInvalid(time, timeFormat)) return
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, NotificationReceiver::class.java)
        intent.putExtra(EXTRA_MESSAGE, message)
        intent.putExtra(EXTRA_TYPE, type)

        val timeArray = time.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        debuggingMyScode(
            TAG_LOG,
            "${Integer.parseInt(timeArray[0])}:${Integer.parseInt(timeArray[1])}"
        )
        val calendarNotification = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]))
            set(Calendar.MINUTE, Integer.parseInt(timeArray[1]))
            set(Calendar.SECOND, 0)
        }

        if (System.currentTimeMillis() > calendarNotification.timeInMillis)
            calendarNotification.add(Calendar.DAY_OF_YEAR, 1)   ///to avoid firing the alarm immediately

        val pendingIntent =
            if (type == TYPE_TOKEN_BACK_APP) PendingIntent.getBroadcast(context, ID_TOKEN_BACK_APP, intent, 0)
            else PendingIntent.getBroadcast(context, ID_RELEASE_MOVIE, intent, 0)

        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendarNotification.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )

    }

    fun unSubscribeNotification(context: Context, type: String) {

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, NotificationReceiver::class.java)
        val requestCode =
            if (type.equals(TYPE_RELEASE_MOVIE, ignoreCase = true)) ID_RELEASE_MOVIE else ID_TOKEN_BACK_APP
        val pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0)
        pendingIntent.cancel()
        alarmManager.cancel(pendingIntent)
        Toast.makeText(context, "Repeating alarm has cancel", Toast.LENGTH_SHORT).show()

    }
}
