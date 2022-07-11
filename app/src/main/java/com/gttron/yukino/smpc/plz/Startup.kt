package com.gttron.yukino.smpc.plz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Startup : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_startup)

        supportActionBar?.hide()
        auth = Firebase.auth
        val currentuser=auth.currentUser
        if (currentuser!=null)
        {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()

        }

        val loginbt = findViewById<Button>(R.id.loginbutton)

       loginbt.setOnClickListener {
            val intent = Intent(this, login::class.java)
            startActivity(intent)
           finish()

        }

        val regbt = findViewById<Button>(R.id.registerbutton)

        regbt.setOnClickListener {
            val intent = Intent(this, register::class.java)
            startActivity(intent)
            finish()

        }


    }
}