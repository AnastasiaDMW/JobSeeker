package com.example.jobseeker.view

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.jobseeker.MyApplication
import com.example.jobseeker.R
import com.example.jobseeker.adapter.MainScreenDelegates
import com.example.jobseeker.databinding.ActivityMainBinding
import com.example.jobseeker.view.fragments.search_fragment.SearchViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var searchViewModel: SearchViewModel

    private var binding: ActivityMainBinding? = null
    private var controller: NavController? = null
//    private var countFavorite = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as MyApplication).appComponent.inject(this)
        window.statusBarColor = Color.BLACK
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }

        controller = (supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment).navController

        binding?.run {

            val badge = bottomNavigation.getOrCreateBadge(R.id.favoriteFragment)

            searchViewModel.state.observe(this@MainActivity) {
                if (!it.isLoading) {
                    if (it.error.isNotBlank()) {
                        Log.d("ERROR", it.error)
                    } else {
//                        val countFavorite = searchViewModel.countFavorite
                        Log.d("NAVIGATION_MAIN", searchViewModel.countFavorite.toString())
                        if (searchViewModel.countFavorite != 0) {
                            badge.isVisible = true
                            badge.number = searchViewModel.countFavorite
                        } else {
                            badge.isVisible = false
                        }
                    }
                }
            }
//            MainScreenDelegates.setOnFavoriteClickListener { vacancy ->
//                searchViewModel.updateFavorite(vacancy)
//            }
            bottomNavigation.itemTextColor = ContextCompat.getColorStateList(this@MainActivity, R.color.bottom_nav_color)
            bottomNavigation.setOnItemSelectedListener { item ->
                when(item.itemId) {
                    R.id.searchFragment -> {
                        controller?.navigate(R.id.searchFragment2)
                        true
                    }
                    R.id.favoriteFragment -> {
                        controller?.navigate(R.id.favoriteFragment2)
                        true
                    }
                    R.id.responsesFragment -> {
                        controller?.navigate(R.id.responsesFragment2)
                        true
                    }
                    R.id.messageFragment -> {
                        controller?.navigate(R.id.messageFragment2)
                        true
                    }
                    R.id.profileFragment -> {
                        controller?.navigate(R.id.profileFragment2)
                        true
                    }
                    else -> false
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        controller?.navigateUp()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}