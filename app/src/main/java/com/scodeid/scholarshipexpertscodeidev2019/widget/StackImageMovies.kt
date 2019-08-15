/*
 * Copyright (c) 2019. SCODEID
 */

package com.scodeid.scholarshipexpertscodeidev2019.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.RemoteViews
import android.widget.Toast
import com.scodeid.scholarshipexpertscodeidev2019.R
import com.scodeid.scholarshipexpertscodeidev2019.service.StackService


/**
 * @author
 * Created by scode on 15,August,2019
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

class StackImageMovies : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // soon
    }

    override fun onDisabled(context: Context) {
        // soon
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        if (intent.action != null) {
            if (intent.action == TOAST_ACTION) {
                val indexImage = intent.getIntExtra(EXTRA_ITEM, 0)
                Toast.makeText(context, "Touched View $indexImage", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    companion object {

        private const val TOAST_ACTION = "com.scodeid.scholarshipexpertscodeidev2019.TOAST_ACTION"
        internal const val EXTRA_ITEM = "com.scodeid.scholarshipexpertscodeidev2019.EXTRA_ITEM"

        internal fun updateAppWidget(
            context: Context, appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {

            val intentStackWidgetService = Intent(context, StackService::class.java)
            intentStackWidgetService.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            intentStackWidgetService.data = Uri.parse(intentStackWidgetService.toUri(Intent.URI_INTENT_SCHEME))

            val remoteViews = RemoteViews(context.packageName, R.layout.image_movies_widget)
            remoteViews.setRemoteAdapter(R.id.stack_view, intentStackWidgetService)
            remoteViews.setEmptyView(R.id.stack_view, R.id.text_empty)

            val intentImageMoviesWidget = Intent(context, StackImageMovies::class.java)
            intentImageMoviesWidget.action = TOAST_ACTION
            intentImageMoviesWidget.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)

            intentStackWidgetService.data = Uri.parse(intentStackWidgetService.toUri(Intent.URI_INTENT_SCHEME))

            val pendingIntent =
                PendingIntent.getBroadcast(context, 0, intentImageMoviesWidget, PendingIntent.FLAG_UPDATE_CURRENT)
            remoteViews.setPendingIntentTemplate(R.id.stack_view, pendingIntent)

            appWidgetManager.updateAppWidget(appWidgetId, remoteViews)
        }
    }
}

