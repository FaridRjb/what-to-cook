package com.faridrjb.whattocook.activities

import androidx.appcompat.app.AppCompatActivity
import com.faridrjb.whattocook.data.DatabaseHelper
import android.annotation.SuppressLint
import android.os.Bundle
import com.faridrjb.whattocook.R
import android.content.Intent
import android.os.Handler
import android.view.View
import android.view.View.GONE
import com.faridrjb.whattocook.fragments.FavoriteFragment
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.faridrjb.whattocook.fragments.PossibleFragment
import com.faridrjb.whattocook.databinding.ActivityMainBinding
import com.faridrjb.whattocook.databinding.DialogFragHelpBinding
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    var db: DatabaseHelper? = null

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // DB
        db = DatabaseHelper(this)
        try {
            db!!.createDataBase()
        } catch (e: IOException) {
            throw IOException()
        }
        db!!.openDataBase()
        //---

        // New Comer
        val newComerPref = getSharedPreferences("First Time", MODE_PRIVATE)
        if (newComerPref.getBoolean("First time", true)) {
            startActivity(Intent(this@MainActivity, IntroSliderActivity::class.java))
            finish()
        } else {
            Handler().postDelayed(
                { binding.splashFL.visibility = GONE },
                3000
            )
        }
        //----------

        // Toolbar
        setSupportActionBar(binding.toolbar.root)
        binding.toolbar.infoBtn.setOnClickListener {
            startActivity(
                Intent(
                    this@MainActivity,
                    AboutActivity::class.java
                )
            )
        }
        //--------

        // Fragments
        supportFragmentManager.beginTransaction().replace(R.id.posFragContainer, PossibleFragment())
            .commit()
        supportFragmentManager.beginTransaction().replace(R.id.favFragContainer, FavoriteFragment())
            .commit()
        //------------------
        binding.inputSearch.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                startActivity(Intent(this@MainActivity, SearchActivity::class.java))
                binding.inputSearch.clearFocus()
            }
        }
        binding.floatingButton.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this@MainActivity, StorageActivity::class.java))
        })

        // help btn
        binding.toolbar.helpBtn.setOnClickListener { showHelpDialog() }
        //----------
    }

    private fun showHelpDialog() {
        val builder = AlertDialog.Builder(this)
        val helpDialogBinding = DialogFragHelpBinding.inflate(layoutInflater)
        val view = helpDialogBinding.root
        val msg = resources.getStringArray(R.array.help_texts)[1]
        helpDialogBinding.helpTV.text = msg
        builder.setView(view)
        builder.create().show()
    }
}