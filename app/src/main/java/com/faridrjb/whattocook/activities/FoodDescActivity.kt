package com.faridrjb.whattocook.activities

import android.animation.Animator
import androidx.appcompat.app.AppCompatActivity
import android.database.sqlite.SQLiteDatabase
import com.faridrjb.whattocook.data.DatabaseHelper
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.faridrjb.whattocook.R
import com.faridrjb.whattocook.recyclerviewadapters.InitsNeededRVAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.GridLayoutManager
import android.content.SharedPreferences
import com.faridrjb.whattocook.activities.FoodDescActivity
import android.view.ViewAnimationUtils
import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import com.faridrjb.whattocook.ScreenUtility
import com.faridrjb.whattocook.databinding.ActivityFoodDescBinding
import java.util.*

class FoodDescActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFoodDescBinding

    var db: SQLiteDatabase? = null
    var dbHelper: DatabaseHelper? = null
    var FOOD_NAME: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoodDescBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        dbHelper = DatabaseHelper(this)
        dbHelper!!.openDataBase()
        db = dbHelper!!.readableDatabase

        // Toolbar
        setSupportActionBar(binding.toolbar.root)
        binding.toolbar.backBtn.setOnClickListener { finish() }
        //--------

        // Food Name
        val bundle = intent.extras
        if (bundle!!.containsKey("name")) {
            FOOD_NAME = bundle.getString("name")
            binding.toolbar.tvFoodName.text = FOOD_NAME
        }
        //----------
        refreshDisplay()

        // More
        binding.txtMore.setOnClickListener {
            binding.recyclerView.layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT
            binding.recyclerView.requestLayout()
            binding.txtMore.visibility = View.INVISIBLE
        }
        //-----
        addToFavourite()
    }

    private fun refreshDisplay() {
        // Image
        binding.foodImageView.setImageResource(
            applicationContext.resources.getIdentifier(
                "drawable/" + dbHelper!!.getFoodPhoto(
                    FOOD_NAME!!
                ), null, applicationContext.packageName
            )
        )
        //------

        // RecyclerView - Initials List
        val initsList =
            ArrayList(listOf(*dbHelper!!.getFoodInits(FOOD_NAME!!).split("-").toTypedArray()))
        val initsAmountList = ArrayList(
            listOf(
                *dbHelper!!.getFoodInitsAmount(FOOD_NAME!!).split("-").toTypedArray()
            )
        )
        binding.recyclerView.adapter = InitsNeededRVAdapter(this, initsList, initsAmountList)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        if (ScreenUtility(this@FoodDescActivity).width >= 600) binding.recyclerView.layoutManager =
            GridLayoutManager(this, 2)
        //-----------------------------

        // Instruction
        binding.txtInstruction.text = dbHelper!!.getFoodInstruc(FOOD_NAME!!)
        //------------
    }

    private fun addToFavourite() {
        val preferences = getSharedPreferences(FAVORITE_PREF, MODE_PRIVATE)
        val editor = preferences.edit()
        if (preferences.getBoolean(FOOD_NAME, false)) {
            binding.likeBtn.setImageResource(R.drawable.ic_favorite_filled)
        }
        binding.likeBtn.setOnClickListener(View.OnClickListener {
            if (!preferences.getBoolean(FOOD_NAME, false)) {
                startCircularReveal()
                editor.putBoolean(FOOD_NAME, true)
                binding.likeBtn.setImageResource(R.drawable.ic_favorite_filled)
            } else if (preferences.getBoolean(FOOD_NAME, false)) {
                editor.putBoolean(FOOD_NAME, false)
                binding.likeBtn.setImageResource(R.drawable.ic_favorite)
            }
            editor.apply()
        })
    }

    private fun startCircularReveal() {
        val cL = binding.layoutChangeable
        val centerX = (binding.likeBtn.right + binding.likeBtn.left) / 2
        val centerY = (binding.likeBtn.bottom + binding.likeBtn.top) / 2
        val endRadius =
            Math.hypot(cL.width.toDouble(), cL.height.toDouble())
                .toFloat()
        cL.visibility = View.VISIBLE
        cL.alpha = 0.0f
        cL.animate().alpha(1.0f).setDuration(350).start()
        val revealAnimator = ViewAnimationUtils.createCircularReveal(
            cL,
            centerX, centerY, endRadius, 0f
        )
        revealAnimator.duration = 500
        revealAnimator.start()
        revealAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}
            override fun onAnimationEnd(animation: Animator) {
                cL.visibility = View.INVISIBLE
            }

            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        })
    }

    fun onClickWeb(view: View) {
        val browserIntent: Intent = when (view.id) {
            R.id.webTV2 -> Intent(Intent.ACTION_VIEW, Uri.parse("https://parsiday.com"))
            R.id.webTV3 -> Intent(Intent.ACTION_VIEW, Uri.parse("https://chishi.ir"))
            else -> return
        }
        startActivity(browserIntent)
    }

    companion object {
        const val FAVORITE_PREF = "Favorite"
    }
}