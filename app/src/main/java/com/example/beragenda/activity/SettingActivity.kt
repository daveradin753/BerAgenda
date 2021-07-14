package com.example.beragenda.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
        val DARK_MODE = "dark_mode"
        val sharedPreferences = getSharedPreferences("modeClick", Context.MODE_PRIVATE)
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
                    putInt(DARK_MODE, 1)
                }.apply()
                setDarkMode(true)
            } else {
                editor.apply {
                    putBoolean(SWITCH_ON, false)
                    putInt(DARK_MODE, 0)
                }.apply()
                setDarkMode(false)
            }
        }

    }

    private fun setDarkMode(b: Boolean) {
        if (b) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            delegate.applyDayNight()
            Toast.makeText(
                this,
                "Dark Mode On",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            delegate.applyDayNight()
            Toast.makeText(
                this,
                "Dark Mode Off",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

}