package com.gttron.yukino.smpc.plz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Forgotpw : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        auth = Firebase.auth
        setContentView(R.layout.activity_forgotpw)
        val resetpw=findViewById<Button>(R.id.forgotbt)
        val pwresetemail=findViewById<TextInputEditText>(R.id.pwresetemail)
        val respw=findViewById<TextInputLayout>(R.id.respw)
        resetpw.setOnClickListener {

            if(pwresetemail.text?.length == 0)
            {
                pwresetemail.error="Field cannot be empty"
                respw.helperText="Field cannot be empty"

            }
            else if (!Patterns.EMAIL_ADDRESS.matcher(pwresetemail.text.toString()).matches())
            {
                pwresetemail.error="Invalid Email"
                respw.helperText="Invalid Email"
            }


            else {
                resetpass()
            }


        }
    }

    private fun resetpass() {
        val pwresetemail=findViewById<TextInputEditText>(R.id.pwresetemail)
        val resemail=pwresetemail.text.toString()
        auth.sendPasswordResetEmail(resemail)
            .addOnSuccessListener {

                Toast.makeText(this, "PASSWORD RESET MAIL SENT", Toast.LENGTH_LONG).show()
                val intent = Intent(this, Startup::class.java)
                startActivity(intent)
                finish()

            }
            .addOnFailureListener {e->

                Toast.makeText(this, "PASSWORD RESET FAILED $e", Toast.LENGTH_LONG).show()


            }



    }
}