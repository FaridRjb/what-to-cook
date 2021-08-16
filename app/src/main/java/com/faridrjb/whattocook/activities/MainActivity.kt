package com.faridrjb.whattocook.activities

import android.animation.Animator
import androidx.appcompat.app.AppCompatActivity
import com.faridrjb.whattocook.data.DatabaseHelper
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.annotation.SuppressLint
import android.os.Bundle
import com.faridrjb.whattocook.R
import android.content.SharedPreferences
import android.content.Intent
import android.os.Handler
import android.view.View
import android.view.View.GONE
import com.faridrjb.whattocook.activities.IntroSliderActivity
import com.faridrjb.whattocook.activities.AboutActivity
import com.faridrjb.whattocook.fragments.FavoriteFragment
import android.view.View.OnFocusChangeListener
import com.faridrjb.whattocook.activities.SearchActivity
import com.faridrjb.whattocook.activities.StorageActivity
import com.faridrjb.whattocook.activities.PosFavActivity
import com.faridrjb.whattocook.Food
import com.faridrjb.whattocook.FoodsChecker
import androidx.recyclerview.widget.RecyclerView
import com.faridrjb.whattocook.recyclerviewadapters.PossibleRVAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.ViewAnimationUtils
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import java.io.IOException
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    var db: DatabaseHelper? = null
    var inputSearch: TextInputEditText? = null
    var floatingButton: FloatingActionButton? = null
    var toolbar: Toolbar? = null
    var infoBtn: ImageButton? = null
    var posMore: TextView? = null

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // DB
        db = DatabaseHelper(this)
        try {
            db!!.createDataBase()
        } catch (e: IOException) {
            Toast.makeText(this, "Wrong 1", Toast.LENGTH_SHORT).show()
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
                { findViewById<FrameLayout>(R.id.splashFL).visibility = GONE },
                3000
            )
        }
        //----------

        // Toolbar
        toolbar = findViewById(R.layout.app_bar_main)
        setSupportActionBar(toolbar)
        infoBtn = findViewById(R.id.infoBtn)
        infoBtn!!.setOnClickListener(View.OnClickListener {
            startActivity(
                Intent(
                    this@MainActivity,
                    AboutActivity::class.java
                )
            )
        })
        //--------

        // Init
        inputSearch = findViewById(R.id.inputSearch)
        floatingButton = findViewById(R.id.floatingButton)
        posMore = findViewById(R.id.posMore)
        //-----

        // Favorite Fragment
        supportFragmentManager.beginTransaction().replace(R.id.favFragContainer, FavoriteFragment())
            .commit()
        //------------------
        inputSearch!!.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                startActivity(Intent(this@MainActivity, SearchActivity::class.java))
                inputSearch!!.clearFocus()
            }
        }
        floatingButton!!.setOnClickListener(View.OnClickListener {
            startCircularReveal()
            floatingButton!!.startAnimation(
                AnimationUtils.loadAnimation(
                    this@MainActivity,
                    R.anim.zoom_out
                )
            )
            startActivity(Intent(this@MainActivity, StorageActivity::class.java))
        })
        posMore!!.setOnClickListener {
            val intent = Intent(this@MainActivity, PosFavActivity::class.java)
            intent.putExtra("IntentToPosFav", "PossibleFoods")
            startActivity(intent)
        }

        // help btn
        val helpBtn = findViewById<ImageButton>(R.id.helpBtn)
        helpBtn.setOnClickListener { showHelpDialog() }
        //----------
    }

    override fun onResume() {
        loadPossibleList()
        super.onResume()
    }

    private fun loadPossibleList() {
        val posNotFoundTV = findViewById<TextView>(R.id.posNotFoundTV)
        posNotFoundTV.visibility = View.GONE
        val allInits = ArrayList<String>()
        val a = resources.getStringArray(R.array.hoboobaat_names)
        val b = resources.getStringArray(R.array.ghallaat_names)
        val c = resources.getStringArray(R.array.labaniaat_names)
        val d = resources.getStringArray(R.array.miveh_sabzi_names)
        val e = resources.getStringArray(R.array.chaashni_names)
        val f = resources.getStringArray(R.array.kareh_roghan_names)
        val g = resources.getStringArray(R.array.protein_names)
        val h = resources.getStringArray(R.array.others_names)
        val j = resources.getStringArray(R.array.khoshkbar_names)
        val total = arrayOf(a, b, c, d, e, f, g, h, j)
        for (category in total) {
            for (item in category) {
                allInits.add(item)
            }
        }
        val preferences = getSharedPreferences("Storage", MODE_PRIVATE)
        val initsInStorage = ArrayList<String>()
        for (item in allInits) {
            if (preferences.getBoolean(item, false)) {
                initsInStorage.add(item)
            }
        }
        var possibleFoods = ArrayList<Food>()
        val firstFivePossibleFoods = ArrayList<Food>()
        possibleFoods = FoodsChecker().possibleFoods(this, initsInStorage, db!!.getFood(""))
        for (i in possibleFoods.indices) { // just show first 5 items
            firstFivePossibleFoods.add(possibleFoods[i])
            if (firstFivePossibleFoods.size == 5) break
        }
        if (firstFivePossibleFoods.size == 0) posNotFoundTV.visibility = View.VISIBLE
        val resList = findViewById<RecyclerView>(R.id.possibleList)
        val possiblervAdapter = PossibleRVAdapter(this, firstFivePossibleFoods)
        resList.adapter = possiblervAdapter
        resList.layoutManager = LinearLayoutManager(this)
    }

    private fun startCircularReveal() {
        val revealLayout = findViewById<RelativeLayout>(R.id.layoutReveal)
        val centerX = (floatingButton!!.right + floatingButton!!.left) / 2
        val centerY = (floatingButton!!.bottom + floatingButton!!.top) / 2
        val endRadius = Math.hypot(revealLayout.width.toDouble(), revealLayout.height.toDouble())
            .toFloat()
        revealLayout.visibility = View.VISIBLE
        val revealAnimator = ViewAnimationUtils.createCircularReveal(
            revealLayout,
            centerX, centerY, 0f, endRadius
        )
        revealAnimator.setDuration(200).start()
        revealAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}
            override fun onAnimationEnd(animation: Animator) {
                revealLayout.visibility = View.INVISIBLE
            }

            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        })
    }

    private fun showHelpDialog() {
        val builder = AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.dialog_frag_help, null)
        val helpTV = view.findViewById<TextView>(R.id.helpTV)
        val s = resources.getStringArray(R.array.help_texts)[1]
        helpTV.text = s
        builder.setView(view)
        builder.create().show()
    }
}