/*
 * Copyright (c) 2019. SCODEID
 */

package com.scodeid.scholarshipexpertscodeidev2019.service

import android.content.Intent
import android.widget.RemoteViewsService
import com.scodeid.scholarshipexpertscodeidev2019.widget.StackRemoteViewsFactory

class StackService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory {
        return StackRemoteViewsFactory(this.applicationContext)
    }
}