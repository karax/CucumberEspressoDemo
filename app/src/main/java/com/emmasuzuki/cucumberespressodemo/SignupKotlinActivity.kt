package com.emmasuzuki.cucumberespressodemo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View

class SignupKotlinActivity : Activity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup)
        findViewById<View>(R.id.login).setOnClickListener {
            val intent = Intent(this@SignupKotlinActivity, LoginKotlinActivity::class.java)
            intent.flags =
                Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }
}
