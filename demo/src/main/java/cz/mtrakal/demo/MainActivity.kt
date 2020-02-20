package cz.mtrakal.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.skoumal.teanity.logger.Logger
import com.skoumal.teanity.logger.ShareLogActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    private val logger: Logger by lazy {
        Logger(this, "app.log")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        vButtonLogger.setOnClickListener { ShareLogActivity.startActivity(this, logger.filename) }
        vButtonAddLog.setOnClickListener { logger.log("Random log: ${Date()}") }
        vButtonDeleteLog.setOnClickListener { logger.deleteFile() }
    }
}
