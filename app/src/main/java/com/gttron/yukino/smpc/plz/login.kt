package com.gttron.yukino.smpc.plz
import android.util.*
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.gttron.yukino.smpc.plz.useremailaddr.usermail
import java.util.regex.Pattern

class login : AppCompatActivity() {
    val passpattern="^(?=.*[0-9])(?=.*[a-z])(?=\\S+$).{8,}$"
    val passwordpattern=Regex(passpattern)
    private lateinit var auth: FirebaseAuth
    private val db = Firebase.firestore
      var email=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()


        val loginb = findViewById<Button>(R.id.loginbt)


        val loginemail=findViewById<TextInputEditText>(R.id.loginemail)
        val emailbox=findViewById<TextInputLayout>(R.id.emailbox)

        loginb.setOnClickListener {
          if(loginemail.text?.length==0)
          {
              loginemail.error="Field cannot be empty"
              emailbox.helperText="Field cannot be empty"
            }
            else if (!Patterns.EMAIL_ADDRESS.matcher(loginemail.text.toString()).matches())
          {
              loginemail.error="Invalid Email"
              emailbox.helperText="Invalid Email"
          }
            else
          {
            usermail=loginemail.text.toString()
            loginuser()}
        }
        val frgpw = findViewById<TextView>(R.id.pwresert)

        frgpw.setOnClickListener {
            val intent = Intent(this, Forgotpw::class.java)
            startActivity(intent)

        }
    }

    private fun loginuser() {


            var USEREMAIL : String?=""


            db.collection("registeredusers")
                .whereEqualTo("email", usermail)
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {


                        USEREMAIL = document.getString("email")



                    }

                   email=USEREMAIL.toString()

                  login()

                }
                .addOnFailureListener { exception ->
                    Toast.makeText(this, "LOGIN FAILED", Toast.LENGTH_SHORT).show()
                    Log.w(ContentValues.TAG, "Error getting documents.", exception)
                }

        }





fun login()
{

    auth = Firebase.auth
if(usermail==email)
{
    val pass=findViewById<TextInputEditText>(R.id.loginpw)
    val passbox=findViewById<TextInputLayout>(R.id.passbox)
    if (pass.text?.length==0)
    {
        pass.error="Field cannot be empty"
        passbox.helperText="Field cannot be empty"
    }
    else if(pass.text?.length!!< 8)
    {
        pass.error="Password length must be greater than  8 characters"
        passbox.helperText="Password length must be greater than  8 characters"
    }
    else if(!passwordpattern.matches(pass.text.toString()))
    {
        pass.error="Password should contain a letter and digit combination"
        passbox.helperText="Password should contain a letter and digit combination"

    }

    else
    {
    val password=pass.text.toString()
    auth.signInWithEmailAndPassword(usermail,password)
        .addOnSuccessListener {

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }


        .addOnFailureListener {

            Toast.makeText(this, "LOGIN FAILED", Toast.LENGTH_LONG).show()


        }}}

    else{
    Toast.makeText(this, "LOGIN FAILED", Toast.LENGTH_LONG).show()
    }


}

























}


