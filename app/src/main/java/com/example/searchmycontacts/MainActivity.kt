package com.example.searchmycontacts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    // declare DBHandler as mutable field using null safety
    var dbHandler: DBHandler? = null

    // declare RecyclerView as mutable field using null safety
    var MyContactsRecyclerView: RecyclerView? = null

    // declare a MyContactAdapter as a mutable field
    // specify that it will be initialized later
    lateinit var MyContactAdapter: MyContactAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // fully initialize dbHandler
        dbHandler = DBHandler(this, null)

        // make RecyclerView refer to actual RecyclerView in activity_main layout resource
        MyContactsRecyclerView = findViewById<View>(R.id.MyContactsRecyclerView) as RecyclerView

        // initialize a MutableList of Students
        var contacts: MutableList<MyContact> = ArrayList()

        // initialize the StudentAdapter
        MyContactAdapter = MyContactAdapter(contacts)

        // tell Kotlin that RecyclerView isn't null and set the StudentAdapter on it
        MyContactsRecyclerView!!.adapter = MyContactAdapter

        // tell Kotlin that the RecylerView isn't null and apply a LinearLayout to it
        MyContactsRecyclerView!!.layoutManager = LinearLayoutManager(this)

        // populate the student table in the database
        // these lines of code should be commented out after the
        // app is run the first time
        addContact("Family Contact 1", "fam1@chc.edu", "Family")
        addContact("Family Contact 2", "fam2@chc.edu", "Family")
        addContact("Friend Contact 1", "friend1@chc.edu", "Friend")
        addContact("Friend Contact 2", "friend2@chc.edu", "Friend")
        addContact("Coworker Contact 1", "coworker1@chc.edu", "Coworker")
        addContact("Coworker Contact 2", "coworker2@chc.edu", "Coworker")

    }

    /**
     * This method populates a student in the database.  It gets called when
     * the app launches.
     * @param name contact name
     * @param email contact email
     * @param contact_group contact group
     */
    fun addContact(name: String, email: String, contact_group: String) {
        dbHandler?.addContact(name, email, contact_group)
    }


    }
