package com.gttron.yukino.smpc.plz

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Color.parseColor
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.recreate
import androidx.core.content.ContextCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {
    var chk=0
    val pattername="^[A-Z a-z]+$"
    val namepatter=Regex(pattername)
    val mno="^[0-9]{10}$"
    val mnopattern=Regex(mno)
    private lateinit var auth: FirebaseAuth
    lateinit var etSubject: EditText
    lateinit var etMessage: EditText
    lateinit var send: Button
    lateinit var attachment: Button
    lateinit var tvAttachment: TextView
    val db = Firebase.firestore
    lateinit var subject: String
    lateinit var message: String
    lateinit var uri: Uri
    private val pickFromGallery: Int = 101
    lateinit var adr: EditText
    lateinit var na : EditText
    lateinit var cont : EditText


    var contact = ""
    var email = ""
    var mail = ""
    var useremail = ""
    var currentDate = ""
    var idtxt = ""
    var name=""
    var addr=""

    var c=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        auth = Firebase.auth
        val currentuser=auth.currentUser
        useremailaddr.usermail = currentuser?.email.toString()
        useremail=useremailaddr.usermail
        val spin = findViewById<Spinner>(R.id.drop)
        spin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                val selitem = parent!!.getItemAtPosition(position)
                if (selitem == "Pandalam(M)") {
                    mail = "pandalam@gmail.com"
                } else if (selitem == "Konni(P)") {
                    mail = "konni@gmail.com"
                } else if (selitem == "Thumpamon(P)") {
                    mail = "thumpamon@gmail.com"
                }

                else if (selitem == "Test Admin") {
                    mail = "abinak322@gmail.com"
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }













        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        currentDate = sdf.format(Date())






        na = findViewById<TextInputEditText>(R.id.name)
        val nabox=findViewById<TextInputLayout>(R.id.nabox)
        cont = findViewById<TextInputEditText>(R.id.contactbox)
        val contbox=findViewById<TextInputLayout>(R.id.contbox)
        adr= findViewById<TextInputEditText>(R.id.addr)
        val addrbox=findViewById<TextInputLayout>(R.id.addrbox)
        etSubject = findViewById(R.id.etSubject)
        val subbox=findViewById<TextInputLayout>(R.id.subbox)
        etMessage = findViewById(R.id.etMessage)
        val compbox=findViewById<TextInputLayout>(R.id.compbox)
        attachment = findViewById(R.id.btAttachment)
        tvAttachment = findViewById(R.id.tvAttachment)
        send = findViewById(R.id.btSend)



        send.setOnClickListener {
            if (na.text?.length == 0) {

                na.error="Field cannot be empty"
                nabox.helperText="Field cannot be empty"
            }
            else if(!namepatter.matches(na.text.toString()))
            {
               na.error="Invalid Name"
                nabox.helperText="Invalid Name"

            }
            else if (cont.text?.length ==0 )
            {
                cont.error="Field cannot be empty"
                contbox.helperText="Field cannot be empty"
            }
            else if(!mnopattern.matches(cont.text.toString()))
                {
                    cont.error="Invalind Mobile Number"
                    contbox.helperText="Invalid Mobile Number"
                }


              else if (adr.text?.length == 0) {

                adr.error="Field cannot be empty"


              }


            else if (etSubject.text?.length == 0) {

                etSubject.error="Field cannot be empty"
                subbox.helperText="Field cannot be empty"
            }


            else if (etMessage.text?.length == 0) {

                etMessage.error="Field cannot be empty"

            }


            else if (chk==0) {
                tvAttachment.setTextColor(ContextCompat.getColor(this,R.color.red))
               tvAttachment.setText("Attachment Required")
            }



            else{
            popupconfirm()}
        }

        attachment.setOnClickListener {
            openFolder()
        }


        val stat = findViewById<ImageButton>(R.id.btn2)

        stat.setOnClickListener {
            val intent = Intent(this, statusactivity::class.java)
            startActivity(intent)

        }

        val acc = findViewById<ImageButton>(R.id.btn3)

        acc.setOnClickListener {
            val inten = Intent(this, accountactivity::class.java)
            startActivity(inten)

        }


    }

    private fun popupconfirm() {

            val rejpop = LayoutInflater.from(this).inflate(R.layout.confirmsend, null)
            val builder = AlertDialog.Builder(this)
                .setView(rejpop)
                .setTitle("Confirm to Send Complaint")


           val confirm=rejpop.findViewById<ImageButton>(R.id.sendconfirm)
            val setid=rejpop.findViewById<TextView>(R.id.displayid)
            val cancel=rejpop.findViewById<ImageButton>(R.id.sendcancel)
        val length = 8
        idtxt = getRandomString(length)

        setid.setText(idtxt)
        val alertdialog = builder.show()
            confirm.setOnClickListener{
                alertdialog.dismiss()

                sendEmail()

            }
            cancel.setOnClickListener{
                alertdialog.dismiss()
            }






    }


    private fun openFolder() {
        val intent = Intent()
        intent.type = "*/*"
        intent.action = Intent.ACTION_GET_CONTENT
        intent.putExtra("return-data", true)
        startActivityForResult(
            Intent.createChooser(intent, "Complete action using"),
            pickFromGallery
        )

        chk=1
    }

    private fun sendEmail() {

        try {

            name=na.text.toString()
            addr=adr.text.toString()
            contact = cont.text.toString()
            email = mail
            subject = "<<ID: " + idtxt + ">>SUBJECT :" + etSubject.text.toString()
            message =
                 "Name: "+name+"\n\nAddress: "+addr+"\n\nPhone No.: " + contact + "\n\nComplaint: \n" + etMessage.text.toString()
            val emailIntent = Intent(Intent.ACTION_SEND)
            emailIntent.type = "plain/text"
            emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
            emailIntent.putExtra(Intent.EXTRA_STREAM, uri)
            emailIntent.putExtra(Intent.EXTRA_TEXT, message)
            this.startActivity(Intent.createChooser(emailIntent, "Sending email..."))
            insertdata()
            c=1
        } catch (t: Throwable) {
            Toast.makeText(this, "Request failed try again: $t", Toast.LENGTH_LONG).show()

        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == pickFromGallery && resultCode == RESULT_OK) {
            if (data != null) {
                uri = data.data!!
            }
            tvAttachment.setTextColor(ContextCompat.getColor(this,R.color.blue))
            tvAttachment.text = uri.lastPathSegment
            tvAttachment.visibility = View.VISIBLE
        }
    }


    fun getRandomString(length: Int): String {
        val charset = "ABCDEFGHIJKLMNOPQRSTUVWXTZ012345"
        return (1..length)
            .map { charset.random() }
            .joinToString("")
    }


    fun insertdata()
    {// Create a new user with a first and last name

        val user = hashMapOf(
            "useremail" to useremail,
            "date" to currentDate,
            "cid" to idtxt,
            "name" to name,
            "addr" to addr,
            "contact" to contact,
            "subject" to etSubject.text.toString(),
            "complaint" to etMessage.text.toString(),
            "status" to "pending",
            "reason" to "",
            "case" to "open"
        )

// Add a new document with a generated ID
        db.collection("complaints")
            .add(user)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }

    }

    public override fun onResume() {
        super.onResume()
        if(c==1)
        {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }




    }


}