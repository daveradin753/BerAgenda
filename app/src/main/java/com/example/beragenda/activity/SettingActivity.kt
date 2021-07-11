package com.example.beragenda.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.beragenda.R

class SettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        setSupportActionBar(findViewById(R.id.settingPageToolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}