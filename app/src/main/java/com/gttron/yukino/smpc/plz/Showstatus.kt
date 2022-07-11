package com.gttron.yukino.smpc.plz

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Showstatus : AppCompatActivity() {

    companion object {
        var comid = "comid"
    }
    var name=""
    var useremail=""
    var addr=""
    var conatct=""
    var date=""
    var cid=""
    var subject=""
    var comlaint=""
    var status=""
    var reason=""
    var case="open"






    var cmid = ""
    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_showstatus)
        supportActionBar?.hide()
        var cid = intent.getStringExtra(comid)
        cmid = cid.toString()
        disdata()
        val homeback = findViewById<ImageButton>(R.id.homeback)
        homeback.setOnClickListener{
            val intent = Intent(this, statusactivity::class.java)
            startActivity(intent)
        }



    }

    private fun disdata() {
        var USEREMAIL : String?=""
        var NAME : String?=""
        var ADDR : String?=""
        var CONTACT : String?=""
        var DATE : String?=""
        var CID: String?=""
        var SUBJECT : String?=""
        var COMPLAINT : String?=""
        var STATUS : String?=""
        var REASON: String?=""

        db.collection("complaints")
            .whereEqualTo("cid", cmid)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {

                    NAME = document.getString("name")
                    USEREMAIL = document.getString("useremail")
                    ADDR = document.getString("addr")
                    CONTACT = document.getString("contact")
                    DATE = document.getString("date")
                    CID = document.getString("cid")
                    SUBJECT = document.getString("subject")
                    COMPLAINT = document.getString("complaint")
                    STATUS = document.getString("status")
                    REASON = document.getString("reason")


                }
                name=NAME.toString()
                useremail=USEREMAIL.toString()
                addr=ADDR.toString()
                conatct=CONTACT.toString()
                date=DATE.toString()
                cid=CID.toString()
                subject=SUBJECT.toString()
                comlaint=COMPLAINT.toString()
                status=STATUS.toString()
                reason=REASON.toString()
                setcard()

            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "DATA FETCH FAILED: NO SUCH DATA", Toast.LENGTH_SHORT).show()
                Log.w(ContentValues.TAG, "Error getting documents.", exception)
            }

    }

    private fun setcard() {

        if(name=="")
        {
            Toast.makeText(this, "DATA FETCH FAILED: NO SUCH DATA", Toast.LENGTH_SHORT).show()
        }
        val setuseremail=findViewById<TextView>(R.id.email)
        val setname=findViewById<TextView>(R.id.na)
        val setdate=findViewById<TextView>(R.id.date)
        val setaddr=findViewById<TextView>(R.id.addr)
        val setcontact=findViewById<TextView>(R.id.contact)
        val setid=findViewById<TextView>(R.id.complaintid)
        val setsubject=findViewById<TextView>(R.id.subject)
        val setcomplaint=findViewById<TextView>(R.id.complaint)
        val setstatus=findViewById<TextView>(R.id.status)
        val setreason=findViewById<TextView>(R.id.reason)
        val reasonlabel=findViewById<TextView>(R.id.reasonlabel)
        setname.setText(name)
        setuseremail.setText(useremail)
        setaddr.setText(addr)
        setdate.setText(date)
        setcontact.setText(conatct)
        setid.setText(cid)
        setsubject.setText(subject)
        setcomplaint.setText(comlaint)
        setstatus.setText(status)
        setreason.setText(reason)
        if(status != "Rejected")
        {
            setreason.visibility = View.INVISIBLE
            reasonlabel.visibility = View.INVISIBLE
        }





    }
}