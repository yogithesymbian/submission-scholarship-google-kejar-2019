/*
 * Copyright (c) 2019. SCODEID
 */

package com.scodeid.scholarshipexpertscodeidev2019.broadcast

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.media.RingtoneManager
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.request.FutureTarget
import com.scodeid.scholarshipexpertscodeidev2019.R
import com.scodeid.scholarshipexpertscodeidev2019.api.ApiEndPoint
import com.scodeid.scholarshipexpertscodeidev2019.homeInitView.MovieCatalogueMainActivity
import com.scodeid.scholarshipexpertscodeidev2019.model.MoviesApiData
import com.scodeid.scholarshipexpertscodeidev2019.notification.ComingSoonActivity
import com.scodeid.scholarshipexpertscodeidev2019.utils.CHANNEL_DAILY_ID_RELEASE
import com.scodeid.scholarshipexpertscodeidev2019.utils.CHANNEL_DAILY_ID_TOKEN
import com.scodeid.scholarshipexpertscodeidev2019.utils.CHANNEL_DAILY_NAME_RELEASE
import com.scodeid.scholarshipexpertscodeidev2019.utils.CHANNEL_DAILY_NAME_TOKEN
import com.scodeid.yomoviecommon.utils.POSTER_IMAGE
import com.scodeid.yomoviecommon.utils.debuggingMyScode
import com.scodeid.yomoviecommon.utils.toastAllActivity
import org.json.JSONObject
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
        const val TYPE_RELEASE_MOVIE = "Daily Release"
        const val TYPE_TOKEN_BACK_APP = "Daily Reminder"

        const val EXTRA_MESSAGE = "message"
        const val EXTRA_TYPE = "extra_type"

        private const val ID_RELEASE_MOVIE = 100
        private const val ID_TOKEN_BACK_APP = 101

        val arrayListMovRelease = ArrayList<MoviesApiData>()

        val origTitleForBodyMsg = mutableListOf<String>()
        val imageMovie = mutableListOf<String>()
        private var bitmap: Bitmap? = null
        private var posterToBitmap: FutureTarget<Bitmap>? = null
    }

    private val timeFormat = "HH:mm"

    override fun onReceive(context: Context, intent: Intent?) {

        val type = intent?.getStringExtra(EXTRA_TYPE)
        val message = intent?.getStringExtra(EXTRA_MESSAGE)
        val title: String
        val notifId: Int

        if (type.equals(TYPE_RELEASE_MOVIE, ignoreCase = true)) {
            notifId = ID_RELEASE_MOVIE
            title = TYPE_RELEASE_MOVIE
            message?.let {
                showNotificationRelease(context, notifId, title)
            }
        } else {
            notifId = ID_TOKEN_BACK_APP
            title = TYPE_TOKEN_BACK_APP
            message?.let {
                showNotificationToken(context, title, it, notifId)
            }
        }

    }

    @SuppressLint("SimpleDateFormat")

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

    private fun showNotificationToken(context: Context, title: String, message: String, notifId: Int) {

        val channelId: String = CHANNEL_DAILY_ID_TOKEN
        val channelName: String = CHANNEL_DAILY_NAME_TOKEN

        val intent = Intent(context, ComingSoonActivity::class.java)

        debuggingMyScode(TAG_LOG, "1 $channelId")

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

    @SuppressLint("SimpleDateFormat")
    private fun showNotificationRelease(context: Context, notifId: Int, title: String) {

        val date = System.currentTimeMillis() //currentTime
        val yMdFormat = SimpleDateFormat("yyyy-MM-dd") // format to
        val dateNow = yMdFormat.format(date) //2019-08-14

        reqApiMovieRelease(context, notifId, dateNow, title)

    }

    private fun reqApiMovieRelease(
        context: Context,
        notifId: Int,
        dateNow: String,
        title: String
    ) {

        AndroidNetworking.get(ApiEndPoint.RELEASE_MOVIE)
            .addPathParameter("API_KEY", ApiEndPoint.API_KEY_V3_AUTH)
            .addPathParameter("LANGUAGE", context.resources.getString(R.string.app_language))
            .addPathParameter("TODAY_DATE_GTE", dateNow)
            .addPathParameter("TODAY_DATE_LTE", dateNow)
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                @SuppressLint("PrivateResource")
                override fun onResponse(response: JSONObject) {

                    val jsonArray = response.optJSONArray("results")

                    if (jsonArray?.length() == 0) {
                        toastAllActivity(
                            context,
                            "result data is empty, Add the data first"
                        )
                    }

                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.optJSONObject(i)

                        arrayListMovRelease.add(
                            MoviesApiData(
                                jsonObject.getInt("vote_count"),
                                jsonObject.getInt("id"),
                                jsonObject.getBoolean("video"),
                                jsonObject.getInt("vote_average"),
                                jsonObject.getString("title"),
                                jsonObject.getInt("popularity"),
                                jsonObject.optString("poster_path"),
                                jsonObject.getString("original_language"),
                                jsonObject.getString("original_title"),
                                arrayListOf(jsonObject.getString("genre_ids")),
                                jsonObject.optString("backdrop_path"),
                                jsonObject.getBoolean("adult"),
                                jsonObject.getString("overview"),
                                jsonObject.getString("release_date")
                            )
                        )
                        origTitleForBodyMsg.add(arrayListMovRelease[i].title)
                        imageMovie.add(arrayListMovRelease[i].posterPath)

                        if (jsonArray.length() - 1 == i) {

                            debuggingMyScode(
                                TAG_LOG, """

                                message req $origTitleForBodyMsg
                                \n
                                message image : $imageMovie

                                ${POSTER_IMAGE}w185${imageMovie[0]}
                            """.trimIndent()
                            )


                            val myImageGlide = Thread {
                                Thread.sleep(100)

                                posterToBitmap = if (imageMovie[0].isNotEmpty()) {
                                    Glide.with(context)
                                        .asBitmap()
                                        .load("${POSTER_IMAGE}w185${imageMovie[0]}")
                                        .error(R.color.error_color_material_light)
                                        .format(DecodeFormat.PREFER_ARGB_8888)
                                        .submit()
                                } else {
                                    Glide.with(context)
                                        .asBitmap()
                                        .load("${POSTER_IMAGE}w185${imageMovie[2]}")
                                        .error(R.color.error_color_material_light)
                                        .format(DecodeFormat.PREFER_ARGB_8888)
                                        .submit()
                                }
                                bitmap = posterToBitmap?.get()

                                // start notification
                                pushNotificationRelease(notifId, context, title, bitmap, posterToBitmap)
                            }
                            myImageGlide.start()


                        }
                    }
                }

                override fun onError(anError: ANError?) {

                    debuggingMyScode("ON_ERROR", anError?.errorDetail.toString())

                    if (anError?.errorCode != 0) {

                        debuggingMyScode(
                            TAG_LOG,
                            "onError errorCode : ${anError?.errorCode}"
                        ) // error.getErrorCode() - the error code from server
                        debuggingMyScode(
                            TAG_LOG,
                            "onError errorBody : ${anError?.errorBody}"
                        ) // error.getErrorBody() - the error body from server
                        debuggingMyScode(
                            TAG_LOG,
                            "onError errorDetail : ${anError?.errorDetail}"
                        ) // error.getErrorDetail() - just an error detail

                    } else {
                        // error.getErrorDetail() : connectionError, parseError, requestCancelledError
                        debuggingMyScode(
                            TAG_LOG,
                            "onError errorDetail : " + anError.errorDetail
                        )
                    }
                }
            })


    }

    private fun pushNotificationRelease(
        notifId: Int,
        context: Context,
        title: String,
        bitmap: Bitmap?,
        posterToBitmap: FutureTarget<Bitmap>?
    ) {

        val channelId: String = CHANNEL_DAILY_ID_RELEASE
        val channelName: String = CHANNEL_DAILY_NAME_RELEASE
        title.replace("[", "")
        title.replace("]", "")

        val intent = Intent(context, MovieCatalogueMainActivity::class.java)

        debuggingMyScode(TAG_LOG, "1 $channelId")

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent,
            PendingIntent.FLAG_ONE_SHOT
        )

        val notificationManagerCompat =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_menu_notifications_black_24dp)
            .setContentTitle(title)
            .setContentText(origTitleForBodyMsg.toString())
            .setChannelId(channelId)
            .setColor(ContextCompat.getColor(context, android.R.color.transparent))
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setSound(alarmSound)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        builder.setLargeIcon(bitmap)
        Glide.with(context).clear(posterToBitmap)

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
