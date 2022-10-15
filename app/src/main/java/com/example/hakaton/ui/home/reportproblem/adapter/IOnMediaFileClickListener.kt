package com.example.hakaton.ui.home.reportproblem.adapter

import android.net.Uri

interface IOnMediaFileClickListener {
    fun onMediaFileClick(position: Int, uri: Uri)
}