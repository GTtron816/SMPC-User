package com.gttron.yukino.smpc.plz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class statusactivity : AppCompatActivity() {
    var cmid =""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statusactivity)
        supportActionBar?.hide()

        val rep = findViewById<ImageButton>(R.id.btn1)

        rep.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }

        val acc = findViewById<ImageButton>(R.id.btn3)

        acc.setOnClickListener{
            val inten = Intent(this, accountactivity::class.java)
            startActivity(inten)

        }

        val srh=findViewById<Button>(R.id.srch)
        srh.setOnClickListener{
            val cmp=findViewById<TextInputEditText>(R.id.cmpid)
            val cmpidbox=findViewById<TextInputLayout>(R.id.compidbox)
            if (cmp.text?.length == 0)
            {
               cmp.error="Field cannot be empty"
                cmpidbox.helperText="Field cannot be empty"

            }
            else
            {
            cmid=cmp.text.toString()
            val intent = Intent(this, Showstatus::class.java)
            intent.putExtra(Showstatus.comid,cmid)
            startActivity(intent)}
        }





    }
}