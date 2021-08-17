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
import com.faridrjb.whattocook.databinding.ActivityIntroSliderBinding
import com.faridrjb.whattocook.databinding.ActivityMainBinding
import com.faridrjb.whattocook.databinding.DialogFragHelpBinding
import java.io.IOException
import java.util.ArrayList

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

        // Favorite Fragment
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
        binding.posMore.setOnClickListener {
            val intent = Intent(this@MainActivity, PosFavActivity::class.java)
            intent.putExtra("IntentToPosFav", "PossibleFoods")
            startActivity(intent)
        }

        // help btn
        binding.toolbar.helpBtn.setOnClickListener { showHelpDialog() }
        //----------
    }

    override fun onResume() {
        loadPossibleList()
        super.onResume()
    }

    private fun loadPossibleList() {
        binding.posNotFoundTV.visibility = GONE
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
        if (firstFivePossibleFoods.size == 0) binding.posNotFoundTV.visibility = View.VISIBLE
        binding.possibleRV.adapter = PossibleRVAdapter(this, firstFivePossibleFoods)
        binding.possibleRV.layoutManager = LinearLayoutManager(this)
    }

    private fun startCircularReveal() {
        val fB = binding.floatingButton
        val rL = binding.revealLayout
        val centerX = (fB.right + fB.left) / 2
        val centerY = (fB.bottom + fB.top) / 2
        val endRadius = Math.hypot(rL.width.toDouble(), rL.height.toDouble())
            .toFloat()
        rL.visibility = View.VISIBLE
        val revealAnimator = ViewAnimationUtils.createCircularReveal(
            rL,
            centerX, centerY, 0f, endRadius
        )
        revealAnimator.setDuration(200).start()
        revealAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}
            override fun onAnimationEnd(animation: Animator) {
                rL.visibility = View.INVISIBLE
            }

            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        })
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