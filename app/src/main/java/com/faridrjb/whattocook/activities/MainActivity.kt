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
import android.view.animation.Animation
import android.view.animation.AnimationUtils

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    var db: DatabaseHelper? = null

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
    }
}