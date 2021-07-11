package com.example.beragenda.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.beragenda.R

class SettingActivity : AppCompatActivity() {

    companion object {
        private const val IS_CHECK = "is_check"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        val SWITCH_ON = "switch_on"
        val sharedPreferences = getSharedPreferences("alarmClick", Context.MODE_PRIVATE)
        val boolean = sharedPreferences.getBoolean(SWITCH_ON, false)

        val language = findViewById<ConstraintLayout>(R.id.firstButton)
        language.setOnClickListener {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
        }

        val imageView = findViewById<ImageView>(R.id.back)
        imageView.setOnClickListener {
            finish()
        }
        
        val darkSwitch = findViewById<SwitchCompat>(R.id.darkSwitch)
        darkSwitch.isChecked = boolean

        darkSwitch.setOnCheckedChangeListener { _, isChecked ->
            val editor = sharedPreferences.edit()
            if (isChecked) {
                editor.apply {
                    putBoolean(SWITCH_ON, true)
                }.apply()
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                delegate.applyDayNight()
            } else {
                editor.apply {
                    putBoolean(SWITCH_ON, false)
                }.apply()
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                delegate.applyDayNight()
            }
        }

    }

}