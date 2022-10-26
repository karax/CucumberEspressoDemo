package com.emmasuzuki.cucumberespressodemo

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.annotation.VisibleForTesting
import android.support.v7.app.AppCompatActivity
import android.util.AttributeSet
import android.util.Patterns
import android.view.View
import android.widget.EditText
import com.emmasuzuki.cucumberespressodemo.databinding.LoginBinding

class LoginKotlinActivity : AppCompatActivity() {

    private val DEMO_EMAIL = "espresso@spoon.com"
    private val DEMO_PASSWORD = "lemoncake"

    lateinit var emailEditText: EditText
    private  lateinit var passwordEditText:EditText
    private var errorView: View? = null
    private var blockView: View? = null
    private var loginView: View? = null
    private val loginBusinessRules = LoginBusinessRules()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        emailEditText = findViewById(R.id.email)
        passwordEditText = findViewById(R.id.password)
        blockView = findViewById(R.id.block)
        val submitButton = findViewById<View>(R.id.submit)
        submitButton.setOnClickListener {
            validateFields()
            loginBusinessRules.checkIsUserBlocked()
        }
        findViewById<View>(R.id.signup).setOnClickListener {
            val intent = Intent(this@LoginKotlinActivity, SignupKotlinActivity::class.java)
            intent.flags =
                Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }

        loginBusinessRules.isUserBlocked.observe(this){
            if (emailEditText.error == null && passwordEditText.error == null) {
                validateAccount(it!!)
            }
        }
    }

    private fun validateFields() {
        if (!Patterns.EMAIL_ADDRESS.matcher(emailEditText.text.toString()).matches()) {
            emailEditText.error = getString(R.string.msg_email_error)
        } else {
            emailEditText.error = null
        }
        if (passwordEditText.text.toString().isEmpty()) {
            passwordEditText.error = getString(R.string.msg_password_error)
        } else {
            passwordEditText.error = null
        }
    }

    private fun validateAccount(isUserBlocked: Boolean) {
        if (errorView == null) {
            errorView = findViewById(R.id.error)
        }
        if (loginView == null) {
            loginView = findViewById(R.id.logged)
        }
        if (emailEditText.text.toString() != DEMO_EMAIL || passwordEditText.text
                .toString() != DEMO_PASSWORD
        ) {
            if (isUserBlocked) {
                errorView!!.visibility = View.VISIBLE
                loginBusinessRules.increaseTryNumber()
            } else {
                errorView!!.visibility = View.GONE
                blockView!!.visibility = View.VISIBLE
            }
        } else {
            errorView!!.visibility = View.GONE
            loginView!!.visibility = View.VISIBLE
        }
    }
}
