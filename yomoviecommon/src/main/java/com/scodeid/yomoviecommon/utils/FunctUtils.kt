/*
 * Copyright (c) 2019. SCODEID
 */

package com.scodeid.yomoviecommon.utils

import android.content.Context
import android.util.Log
import android.widget.Toast

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
fun toastAllActivity(context : Context, justShowUrCode: String){
    Toast.makeText(context, justShowUrCode, Toast.LENGTH_SHORT).show()
}

fun debuggingMyScode(tagScode: String, invokerString: String){
    Log.d(tagScode, invokerString)
}