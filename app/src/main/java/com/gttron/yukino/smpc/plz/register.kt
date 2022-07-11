package com.gttron.yukino.smpc.plz

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.Window
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class register : AppCompatActivity() {
    val pattername="^[A-Z a-z]+$"
    val namepatter=Regex(pattername)
    val passpattern="^(?=.*[0-9])(?=.*[a-z])(?=\\S+$).{8,}$"
    val passwordpattern=Regex(passpattern)
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        supportActionBar?.hide()

        auth = Firebase.auth
    val regb=findViewById<Button>(R.id.reg)
        regb.setOnClickListener{

            registeruser()
        }


    }

    private fun registeruser() {

        val usname = findViewById<TextInputEditText>(R.id.usrna)
        val namebox=findViewById<TextInputLayout>(R.id.namebox)
        val usremail = findViewById<TextInputEditText>(R.id.usremail)
        val emailbox=findViewById<TextInputLayout>(R.id.mailbox)
        val regpw = findViewById<TextInputEditText>(R.id.regpw)
        val passbox=findViewById<TextInputLayout>(R.id.passwordbox)
        if (usname.text?.length == 0) {

            usname.error="Field cannot be empty"
            namebox.helperText="Field cannot be empty"
        }
        else if(!namepatter.matches(usname.text.toString()))
        {
            usname.error="Invalid Name"
            namebox.helperText="Invalid Name"

        }
        else if (usremail.text?.length==0)
        {
            usremail.error="Field cannot be empty"
            emailbox.helperText="Field cannot be empty"
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(usremail.text.toString()).matches())
        {
            usremail.error="Invalid Email"
            emailbox.helperText="Invalid Email"
        }
        else if (regpw.text?.length==0)
        {
            regpw.error="Field cannot be empty"
            passbox.helperText="Field cannot be empty"
        }
        else if(regpw.text?.length!!< 8)
        {
            regpw.error="Password length must be greater than  8 characters"
            passbox.helperText="Password length must be greater than  8 characters"
        }
        else if(!passwordpattern.matches(regpw.text.toString()))
        {
            regpw.error="Password should contain a letter and digit combination"
            passbox.helperText="Password should contain a letter and digit combination"
        }




        else{
        val na = usname.text.toString()
        useremailaddr.usermail = usremail.text.toString()
        val pw = regpw.text.toString()

        auth.createUserWithEmailAndPassword(useremailaddr.usermail, pw)
            .addOnSuccessListener {
                val db = Firebase.firestore

                val user = hashMapOf(
                    "name" to na,
                    "email" to useremailaddr.usermail


                )

                Toast.makeText(this, "ACCOUNT SUCESSFULL CREATED, LOGGING IN.....", Toast.LENGTH_LONG)
                    .show()
// Add a new document with a generated ID
                db.collection("registeredusers")
                    .add(user)
                    .addOnSuccessListener { documentReference ->
                        Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")

                    }
                    .addOnFailureListener { e ->
                        Log.w(TAG, "Error adding document", e)
                        Toast.makeText(this, "ACCOUNT CREATION FAILED $e", Toast.LENGTH_SHORT)
                            .show()

                    }
                finish()

                val intent = Intent(this, Startup::class.java)
                startActivity(intent)

            }
            .addOnFailureListener { e ->

                Log.w(TAG, "Error creating account", e)
                Toast.makeText(this, "ACCOUNT CREATION FAILED $e", Toast.LENGTH_SHORT).show()
            }
    }


    }
}