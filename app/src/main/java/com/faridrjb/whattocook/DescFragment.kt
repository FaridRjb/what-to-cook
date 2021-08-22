package com.faridrjb.whattocook

import android.animation.Animator
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.faridrjb.whattocook.adapters.InitsNeededRVAdapter
import com.faridrjb.whattocook.data.DatabaseHelper
import com.faridrjb.whattocook.databinding.FragmentDescBinding
import java.util.ArrayList

class DescFragment : Fragment() {

    private var _binding: FragmentDescBinding? = null
    private val binding get() = _binding!!

    val FAVORITE_PREF = "Favorite"
    val args: DescFragmentArgs by navArgs()
    var db: SQLiteDatabase? = null
    var dbHelper: DatabaseHelper? = null
    var FOOD_NAME: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDescBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dbHelper = DatabaseHelper(requireContext())
        dbHelper!!.openDataBase()
        db = dbHelper!!.readableDatabase

        binding.toolbar.backBtn.setOnClickListener {
            TODO("where did i come from???")
        }

        // Food Name
            FOOD_NAME = args.name
            binding.toolbar.tvFoodName.text = FOOD_NAME
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun refreshDisplay() {
        // Image
        binding.foodImageView.setImageResource(
            requireContext().resources.getIdentifier(
                "drawable/" + dbHelper!!.getFoodPhoto(
                    FOOD_NAME!!
                ), null, requireContext().packageName
            )
        )
        // RecyclerView - Initials List
        val initsList =
            ArrayList(listOf(*dbHelper!!.getFoodInits(FOOD_NAME!!).split("-").toTypedArray()))
        val initsAmountList = ArrayList(
            listOf(
                *dbHelper!!.getFoodInitsAmount(FOOD_NAME!!).split("-").toTypedArray()
            )
        )
        binding.recyclerView.adapter = InitsNeededRVAdapter(requireContext(), initsList, initsAmountList)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        if (ScreenUtility(requireActivity()).width >= 600) binding.recyclerView.layoutManager =
            GridLayoutManager(requireContext(), 2)
        //-----------------------------
        binding.txtInstruction.text = dbHelper!!.getFoodInstruc(FOOD_NAME!!)

        binding.webTV1.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://parsiday.com")))
        }
        binding.webTV2.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://chishi.ir")))
        }
    }

    private fun addToFavourite() {
        val preferences = requireContext().getSharedPreferences(
            FAVORITE_PREF,
            AppCompatActivity.MODE_PRIVATE
        )
        val editor = preferences.edit()
        if (preferences.getBoolean(FOOD_NAME, false)) {
            binding.likeBtn.setImageResource(R.drawable.ic_favorite_filled)
        }
        binding.likeBtn.setOnClickListener {
            if (!preferences.getBoolean(FOOD_NAME, false)) {
                startCircularReveal()
                editor.putBoolean(FOOD_NAME, true)
                binding.likeBtn.setImageResource(R.drawable.ic_favorite_filled)
            } else if (preferences.getBoolean(FOOD_NAME, false)) {
                editor.putBoolean(FOOD_NAME, false)
                binding.likeBtn.setImageResource(R.drawable.ic_favorite)
            }
            editor.apply()
        }
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
}