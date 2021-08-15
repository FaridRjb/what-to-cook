package com.faridrjb.whattocook.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.faridrjb.whattocook.R
import android.widget.TextView
import android.content.Intent
import android.net.Uri
import android.view.View
import com.faridrjb.whattocook.BuildConfig

class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        val versionTV = findViewById<TextView>(R.id.versionTV)
        versionTV.text = "نسخه " + BuildConfig.VERSION_NAME
    }

    fun onClick(view: View) {
        val intent: Intent
        when (view.id) {
            R.id.icon_back -> finish()
            R.id.emailBtn -> {
                intent = Intent(Intent.ACTION_SENDTO)
                intent.data = Uri.parse("mailto:" + "faridrajabi7@gmail.com")
                startActivity(intent)
            }
            R.id.linkedinBtn -> {
                intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse("https://www.linkedin.com/in/faridrjb/")
                startActivity(intent)
            }
            R.id.twitterBtn -> {
                intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse("https://twitter.com/FaridRjb/")
                startActivity(intent)
            }
        }
    }
}