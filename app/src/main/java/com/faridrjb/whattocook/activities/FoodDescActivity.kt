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
import java.util.*

class FoodDescActivity : AppCompatActivity() {

    var db: SQLiteDatabase? = null
    var dbHelper: DatabaseHelper? = null
    var bundle: Bundle? = null
    var FOOD_NAME: String? = null
    var recyclerView: RecyclerView? = null
    var likeButton: FloatingActionButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_desc)
        dbHelper = DatabaseHelper(this)
        dbHelper!!.openDataBase()
        db = dbHelper!!.readableDatabase

        // Toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val textView = findViewById<TextView>(R.id.tvFoodName)
        setSupportActionBar(toolbar)
        val back = findViewById<ImageButton>(R.id.icon_back)
        back.setOnClickListener { finish() }
        //--------

        // Food Name
        bundle = intent.extras
        if (bundle!!.containsKey("name")) {
            FOOD_NAME = bundle!!.getString("name")
            textView.text = FOOD_NAME
        }
        //----------
        refreshDisplay()

        // More
        val more = findViewById<TextView>(R.id.txtMore)
        more.setOnClickListener {
            recyclerView!!.layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT
            recyclerView!!.requestLayout()
            more.visibility = View.INVISIBLE
        }
        //-----
        addToFavourite()
    }

    private fun refreshDisplay() {
        // Image
        val image = findViewById<ImageView>(R.id.foodImageView)
        image.setImageResource(
            applicationContext.resources.getIdentifier(
                "drawable/" + dbHelper!!.getFoodPhoto(
                    FOOD_NAME!!
                ), null, applicationContext.packageName
            )
        )
        //------

        // RecyclerView - Initials List
        val initsList =
            ArrayList(Arrays.asList(*dbHelper!!.getFoodInits(FOOD_NAME!!).split("-").toTypedArray()))
        val initsAmountList = ArrayList(
            Arrays.asList(
                *dbHelper!!.getFoodInitsAmount(FOOD_NAME!!).split("-").toTypedArray()
            )
        )
        recyclerView = findViewById(R.id.recyclerView)
        val adapter = InitsNeededRVAdapter(this, initsList, initsAmountList)
        recyclerView!!.adapter = adapter
        recyclerView!!.layoutManager = LinearLayoutManager(this)
        if (findViewById<View?>(R.id.f_d_c_sw600dp) != null) recyclerView!!.layoutManager =
            GridLayoutManager(this, 2)
        //-----------------------------

        // Instruction
        val instruction = findViewById<TextView>(R.id.txtInstruction)
        instruction.text = dbHelper!!.getFoodInstruc(FOOD_NAME!!)
        //------------
    }

    private fun addToFavourite() {
        likeButton = findViewById(R.id.likeButton)
        val preferences = getSharedPreferences(FAVORITE_PREF, MODE_PRIVATE)
        val editor = preferences.edit()
        if (preferences.getBoolean(FOOD_NAME, false)) {
            likeButton!!.setImageResource(R.drawable.ic_favorite_filled)
        }
        likeButton!!.setOnClickListener(View.OnClickListener {
            if (!preferences.getBoolean(FOOD_NAME, false)) {
                startCircularReveal()
                editor.putBoolean(FOOD_NAME, true)
                likeButton!!.setImageResource(R.drawable.ic_favorite_filled)
            } else if (preferences.getBoolean(FOOD_NAME, false)) {
                editor.putBoolean(FOOD_NAME, false)
                likeButton!!.setImageResource(R.drawable.ic_favorite)
            }
            editor.apply()
        })
    }

    private fun startCircularReveal() {
        val changeableLayout = findViewById<RelativeLayout>(R.id.layoutChangeable)
        val centerX = (likeButton!!.right + likeButton!!.left) / 2
        val centerY = (likeButton!!.bottom + likeButton!!.top) / 2
        val endRadius =
            Math.hypot(changeableLayout.width.toDouble(), changeableLayout.height.toDouble())
                .toFloat()
        changeableLayout.visibility = View.VISIBLE
        changeableLayout.alpha = 0.0f
        changeableLayout.animate().alpha(1.0f).setDuration(350).start()
        val revealAnimator = ViewAnimationUtils.createCircularReveal(
            changeableLayout,
            centerX, centerY, endRadius, 0f
        )
        revealAnimator.duration = 500
        revealAnimator.start()
        revealAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}
            override fun onAnimationEnd(animation: Animator) {
                changeableLayout.visibility = View.INVISIBLE
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