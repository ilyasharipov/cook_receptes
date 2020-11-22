package com.testproject.recipes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.testproject.recipes.ui.main.MainFragment
import com.testproject.recipes.ui.observe.ObserveFragment

class MainActivity : AppCompatActivity(), MainFragment.MainFragmentListener {

    private lateinit var mainFragment: MainFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        if (savedInstanceState == null) {
            mainFragment = MainFragment.newInstance()
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, mainFragment)
                    .commitNow()
        }
    }

    override fun setObserveFragment(uuid: String) {
        supportFragmentManager.beginTransaction()
            .hide(mainFragment)
            .add(R.id.container, ObserveFragment.newInstance(uuid), ObserveFragment.TAG)
            .commitNow()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.fragments.size > 1) {
            title = resources.getString(R.string.app_name)
            supportFragmentManager.findFragmentByTag(ObserveFragment.TAG)?.let {
                supportFragmentManager.beginTransaction()
                    .remove(it)
                    .show(mainFragment)
                    .commitNow()
            }
        } else {
            super.onBackPressed()
        }
    }
}