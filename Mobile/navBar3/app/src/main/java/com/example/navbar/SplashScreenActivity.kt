package com.example.navbar

import kotlinx.android.synthetic.main.activity_splash_screen.*

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.graphics.alpha


class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        iv_bh.alpha = 0f
        iv_bh.animate().setDuration(3000).alpha(1f).withEndAction{
            val i = Intent (this, MainActivity::class.java)
            startActivity(i)
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
            finish()
        }
    }
}