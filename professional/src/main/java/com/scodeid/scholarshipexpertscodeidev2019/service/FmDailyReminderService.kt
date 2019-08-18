/*
 * Copyright (c) 2019. SCODEID
 */

package com.scodeid.scholarshipexpertscodeidev2019.service

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.bumptech.glide.Glide
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.scodeid.scholarshipexpertscodeidev2019.R
import com.scodeid.scholarshipexpertscodeidev2019.api.ApiEndPoint
import com.scodeid.scholarshipexpertscodeidev2019.homeInitView.MovieCatalogueMainActivity
import com.scodeid.scholarshipexpertscodeidev2019.model.MoviesApiData
import com.scodeid.scholarshipexpertscodeidev2019.notification.ComingSoonActivity
import com.scodeid.yomoviecommon.utils.POSTER_IMAGE
import com.scodeid.yomoviecommon.utils.debuggingMyScode
import org.json.JSONObject
import java.text.SimpleDateFormat


class FmDailyReminderService : FirebaseMessagingService() {

    companion object {

        private val TAG_LOG: String = FmDailyReminderService::class.java.simpleName
        val arrayListMovRelease = ArrayList<MoviesApiData>()

        val origTitle = mutableListOf<String>()
        val imageMovie = mutableListOf<String>()

    }

    override fun onNewToken(token: String?) {
        super.onNewToken(token)
        debuggingMyScode(TAG_LOG, "Refreshed token: " + token!!)
    }

    // receive notif
    @SuppressLint("SimpleDateFormat")
    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        super.onMessageReceived(remoteMessage)

        debuggingMyScode(TAG_LOG, "message From: ${remoteMessage?.from}")

        if (remoteMessage?.from == "/topics/token") {
            // by default run this function if apps did not response from where topic {sometimes} dunno why
            if (remoteMessage.notification != null) {
                debuggingMyScode(
                    TAG_LOG,
                    "message ${remoteMessage.notification?.body.toString()}"
                )
                sendNotificationToken(remoteMessage.notification!!.body)
            }
        } else if (remoteMessage?.from == "/topics/release") {
            if (remoteMessage.notification != null) {
                debuggingMyScode(
                    TAG_LOG, """

                    message
                    ${remoteMessage.notification?.imageUrl.toString()}
                    ${remoteMessage.notification?.body.toString()}
                    
                """.trimIndent()
                )

                val date = System.currentTimeMillis() //currentTime
                val yMdFormat = SimpleDateFormat("yyyy-MM-dd") // format to
                val dateNow = yMdFormat.format(date) //2019-08-14

                reqApiMovie(dateNow)
            }
        } else {
            debuggingMyScode(
                TAG_LOG,
                "maybe the topic is confused or maybe not found, please contact admin to request token for notification, bug topic fcm"
            )
        }
    }

    private fun reqApiMovie(dateNow: String) {

        AndroidNetworking.get(ApiEndPoint.RELEASE_MOVIE)
            .addPathParameter("API_KEY", ApiEndPoint.API_KEY_V3_AUTH)
            .addPathParameter("LANGUAGE", applicationContext.resources.getString(R.string.app_language))
            .addPathParameter("TODAY_DATE_GTE", dateNow)
            .addPathParameter("TODAY_DATE_LTE", dateNow)
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    arrayListMovRelease.clear()

                    val jsonArray = response.optJSONArray("results")

                    if (jsonArray?.length() == 0) {
                        Toast.makeText(
                            applicationContext,
                            "result data is empty, Add the data first",
                            Toast.LENGTH_LONG
                        ).show()
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
                                jsonObject.getString("poster_path"),
                                jsonObject.getString("original_language"),
                                jsonObject.getString("original_title"),
                                arrayListOf(jsonObject.getString("genre_ids")),
                                jsonObject.getString("backdrop_path"),
                                jsonObject.getBoolean("adult"),
                                jsonObject.getString("overview"),
                                jsonObject.getString("release_date")
                            )
                        )
                        origTitle.add(arrayListMovRelease[i].title)
                        imageMovie.add(arrayListMovRelease[i].posterPath)

                        if (jsonArray.length() - 1 == i) {

                            debuggingMyScode(
                                TAG_LOG, """
                                
                                message req $origTitle
                                \n
                                message image : $imageMovie
                                
                                ${POSTER_IMAGE}w185${imageMovie[1]}
                            """.trimIndent()
                            )

                            /**
                             * ThreadReleaseNotification
                             */
                            val myImageGlide = Thread {
                                Thread.sleep(100)

                                val channelId = getString(R.string.notification_channel_id_2)
                                val channelName = getString(R.string.notification_channel_name_2)
                                val messageRelease = origTitle.toString()

                                val intent = Intent(applicationContext, MovieCatalogueMainActivity::class.java)
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

                                val pendingIntent = PendingIntent.getActivity(
                                    applicationContext, 0, intent,
                                    PendingIntent.FLAG_ONE_SHOT
                                )
                                val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                                val notificationManager =
                                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

                                messageRelease.replace("[", "")
                                messageRelease.replace("]", "")

                                val notificationBuilder = NotificationCompat.Builder(applicationContext, channelId)
                                    .setSmallIcon(R.drawable.ic_menu_notifications_black_24dp)
                                    .setContentText("News Release movies : $messageRelease")
                                    .setAutoCancel(true)
                                    .setSound(defaultSoundUri)
                                    .setContentIntent(pendingIntent)

                                val posterToBitmap = Glide.with(applicationContext)
                                    .asBitmap()
                                    .load("${POSTER_IMAGE}w185${imageMovie[1]}")
                                    .submit()

                                val bitmapMovie = posterToBitmap.get()

                                notificationBuilder.setLargeIcon(bitmapMovie)
                                Glide.with(applicationContext).clear(posterToBitmap)

                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    /* Create or update. */
                                    val channel = NotificationChannel(
                                        channelId,
                                        channelName,
                                        NotificationManager.IMPORTANCE_DEFAULT
                                    )
                                    notificationBuilder.setChannelId(channelId)
                                    notificationManager.createNotificationChannel(channel)
                                }

                                val notification = notificationBuilder.build()
                                notificationManager.notify(0, notification)

                            }
                            /**
                             * ThreadReleaseNotification
                             */
                            sendNotificationRelease(myImageGlide)

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

    private fun sendNotificationRelease(
        notificationThread: Thread
    ) {
        notificationThread.start()
    }

    private fun sendNotificationToken(messageBody: String?) {
        val channelId = getString(R.string.notification_channel_id_1)
        val channelName = getString(R.string.notification_channel_name_1)
        val intent = Intent(this, ComingSoonActivity::class.java)

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_ONE_SHOT
        )

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_launcher_movie)
            .setContentText(messageBody)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        val mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channelRelease = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            notificationBuilder.setChannelId(channelId)
            mNotificationManager.createNotificationChannel(channelRelease)

        }

        val notification = notificationBuilder.build()
        mNotificationManager.notify(0, notification)

    }
}
