package com.skoumal.teanity.logger

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_share_log.*
import java.io.File

open class ShareLogActivity : AppCompatActivity() {
    private lateinit var filename: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val fileName = intent?.getStringExtra(ARG_FILENAME)
        if (fileName == null) {
            finish()
            return
        }
        setContentView(R.layout.activity_share_log)
        vButtonShare.setOnClickListener {
            Logger.share(
                this,
                File(fileName)
            )
        }
    }

    companion object {
        const val ARG_FILENAME: String = "arg_filename"
        fun startActivity(context: Context, filename: String) {
            val intent = Intent(context, ShareLogActivity::class.java).apply {
                putExtra(ARG_FILENAME, filename)
            }
            context.startActivity(intent)
        }
    }
}
