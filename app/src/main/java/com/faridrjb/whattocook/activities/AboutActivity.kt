package com.faridrjb.whattocook.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.faridrjb.whattocook.R
import android.widget.TextView
import android.content.Intent
import android.net.Uri
import android.view.View
import com.faridrjb.whattocook.BuildConfig
import com.faridrjb.whattocook.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.versionTV.text = "نسخه " + BuildConfig.VERSION_NAME
    }

    fun onClick(view: View) {
        val intent: Intent
        when (view.id) {
            binding.backBtn.id -> finish()
            binding.emailBtn.id -> {
                intent = Intent(Intent.ACTION_SENDTO)
                intent.data = Uri.parse("mailto:" + "faridrajabi7@gmail.com")
                startActivity(intent)
            }
            binding.linkedinBtn.id -> {
                intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse("https://www.linkedin.com/in/faridrjb/")
                startActivity(intent)
            }
            binding.twitterBtn.id -> {
                intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse("https://twitter.com/FaridRjb/")
                startActivity(intent)
            }
        }
    }
}