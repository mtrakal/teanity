package com.skoumal.teanity.logger

import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
import java.io.File

/**
 * Log output to specified file in internal app storage
 */
open class Logger(private val context: Context, _filename: String) {
    private val file: File
    val filename: String = composeFileName(context, _filename)

    init {
        file = File(filename).apply {
            parentFile.mkdirs()
            createNewFile()
        }
    }

    private fun composeFileName(context: Context, filename: String) =
        context.filesDir.absolutePath + "/logs/" + filename

    /**
     * Add next line with log to log file.
     */
    fun log(logMessage: String) {
        file.appendText("$logMessage\r\n")
    }

    /**
     * Show android chooser dialog, with [title].
     */
    fun share(title: String = "Share") {
        Companion.share(context, file, title)
    }

    /**
     * Delete log file.
     */
    fun deleteFile() {
        file.delete()
    }

    companion object {

        /**
         * Show android chooser dialog, with [title].
         */
        fun share(context: Context, file: File, title: String = "Share") {
            if (!file.exists() || !file.canRead()) {
                return
            }

            val shareIntent = Intent().apply {
                val uri = FileProvider.getUriForFile(
                    context,
                    context.packageName + ".logger.LoggerProvider",
                    file
                )
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_STREAM, uri)
                type = "application/octet-stream"
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            context.startActivity(Intent.createChooser(shareIntent, title))
        }
    }
}
