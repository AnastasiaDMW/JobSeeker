package com.example.jobseeker.view

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.jobseeker.MyApplication
import com.example.jobseeker.R
import com.example.jobseeker.databinding.ActivityMainBinding
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var mainViewModel: MainViewModel

    private var binding: ActivityMainBinding? = null
    private var controller: NavController? = null

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

            mainViewModel.state.observe(this@MainActivity) {
                if (!it.isLoading) {
                    if (it.error.isNotBlank()) {
                        Log.d("ERROR", it.error)
                    } else {
                        Log.d("NAVIGATION_MAIN", mainViewModel.countFavorite.toString())
                        if (mainViewModel.countFavorite != 0) {
                            badge.isVisible = true
                            badge.number = mainViewModel.countFavorite
                        } else {
                            badge.isVisible = false
                        }
                    }
                }
            }
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