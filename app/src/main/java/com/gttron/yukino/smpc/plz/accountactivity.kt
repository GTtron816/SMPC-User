package com.gttron.yukino.smpc.plz

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.gttron.yukino.smpc.plz.useremailaddr.usermail
class accountactivity : AppCompatActivity() {
    var name=""
    var email=""
    var prfvalue=1
    private lateinit var auth: FirebaseAuth
    private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accountactivity)
        supportActionBar?.hide()
        loadprf()
        auth = Firebase.auth
       val currentuser=auth.currentUser
        usermail= currentuser?.email.toString()
        val rep = findViewById<ImageButton>(R.id.btn1)

        rep.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }

        val stat = findViewById<ImageButton>(R.id.btn2)

        stat.setOnClickListener{
            val inten = Intent(this, statusactivity::class.java)
            startActivity(inten)

        }
        val logoutbt=findViewById<Button>(R.id.logoutbt)
        logoutbt.setOnClickListener {
            auth.signOut()
            val i = Intent(applicationContext, Startup::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            i.putExtra("EXIT", true)
            startActivity(i)
            finish()

        }



        val left=findViewById<TextView>(R.id.left)
        val right=findViewById<TextView>(R.id.right)
        val prfimg=findViewById<ImageView>(R.id.prf)
        left.setOnClickListener {
            prfimg.setImageResource(R.drawable.prf2)
            saveprf2()
        }
        right.setOnClickListener {
            prfimg.setImageResource(R.drawable.prf1)
           saveprf1()

        }

getprofile()

    }

    private fun loadprf() {
        val sharedPreferences=getSharedPreferences("sharedPref",Context.MODE_PRIVATE)
        val prfimage=sharedPreferences.getInt("profilekey",1)
        val prfimg=findViewById<ImageView>(R.id.prf)
        if (prfimage==1)
        {
            prfimg.setImageResource(R.drawable.prf1)
        }

        if(prfimage==2){

            prfimg.setImageResource(R.drawable.prf2)
        }

    }

    private fun saveprf1() {
      prfvalue=1
        val sharedPreferences=getSharedPreferences("sharedPref",Context.MODE_PRIVATE)
        val editor=sharedPreferences.edit()
        editor.apply{
            putInt("profilekey",prfvalue)
        }.apply()
    }

    private fun saveprf2() {
        prfvalue=2
        val sharedPreferences=getSharedPreferences("sharedPref",Context.MODE_PRIVATE)
        val editor=sharedPreferences.edit()
        editor.apply{
            putInt("profilekey",prfvalue)
        }.apply()

    }

    private fun getprofile() {
        var USEREMAIL : String?=""
        var NAME : String?=""


        db.collection("registeredusers")
            .whereEqualTo("email",usermail)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {

                    NAME = document.getString("name")
                    USEREMAIL = document.getString("email")



                }
                name=NAME.toString()
                email=USEREMAIL.toString()

                setprofile()

            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "DATA FETCH FAILED: NO SUCH DATA", Toast.LENGTH_SHORT).show()
                Log.w(ContentValues.TAG, "Error getting documents.", exception)
            }


    }

    private fun setprofile() {
        val setname=findViewById<TextView>(R.id.nametext)
        val setemail=findViewById<TextView>(R.id.emailtext)
        setname.setText(name)
        setemail.setText(usermail)
    }
}