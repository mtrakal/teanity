package com.skoumal.teanity.extensions

import android.content.Intent
import android.provider.Settings
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import androidx.core.net.toUri
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener

fun AppCompatActivity.requestPermission(
    permission: String,
    explanation: String,
    actionTitle: String,
    view: View,
    onGranted: () -> Unit,
    onDenied: () -> Unit
) {
    Dexter.withActivity(this)
        .withPermission(permission)
        .withListener(object : PermissionListener {
            override fun onPermissionRationaleShouldBeShown(
                permission: PermissionRequest,
                token: PermissionToken
            ) = token.continuePermissionRequest()

            override fun onPermissionDenied(response: PermissionDeniedResponse) {
                onDenied()
                if (response.isPermanentlyDenied) {
                    snackbar(view, explanation, Snackbar.LENGTH_LONG) {
                        action(actionTitle) {
                            val appSettings = Intent(
                                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                "package:$packageName".toUri()
                            ).apply {
                                addCategory(Intent.CATEGORY_DEFAULT)
                                flags = Intent.FLAG_ACTIVITY_NEW_TASK
                            }

                            startActivity(appSettings)
                        }
                    }
                }
            }

            override fun onPermissionGranted(response: PermissionGrantedResponse?) = onGranted()

        })
        .check()
}

fun AppCompatActivity.requestPermissionSoft(permission: String, onGranted: () -> Unit, onDenied: () -> Unit) {
    Dexter.withActivity(this)
        .withPermission(permission)
        .withListener(object : PermissionListener {
            override fun onPermissionGranted(response: PermissionGrantedResponse) {
                onGranted()
            }

            override fun onPermissionDenied(response: PermissionDeniedResponse) {
                onDenied()
            }

            override fun onPermissionRationaleShouldBeShown(
                permission: PermissionRequest,
                token: PermissionToken
            ) {
                token.cancelPermissionRequest()
            }
        })
        .check()
}