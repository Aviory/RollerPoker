package com.saymewhy.forfriend.rollerpoker.firebase

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.saymewhy.forfriend.rollerpoker.entity.Table
import com.saymewhy.forfriend.rollerpoker.interfaces.DB
import com.saymewhy.forfriend.rollerpoker.interfaces.MainView
import java.util.*
import kotlin.collections.ArrayList


class CloudFire(val mainView: MainView):DB {

    private val db = FirebaseFirestore.getInstance()
    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()


    init {
        getTable()
    }


    override fun getTable() {

        val docRef = db.collection(TABLES).document(
            TEST_TABLE_NAME
        )
        docRef.get().addOnSuccessListener { documentSnapshot ->
            var table = documentSnapshot.toObject(Table::class.java)
            if (table != null)
                tableConnect(table)
            else
                createTable()
        }
            .addOnFailureListener { exception ->
                Log.d("CloudFire", "get failed with ", exception)
            }
    }


    override fun updateTimer(time:Long) {
        val data = hashMapOf("timeNextState" to time)

        db.collection(TABLES).document(TEST_TABLE_NAME)
            .set(data, SetOptions.merge())
            .addOnSuccessListener { documentReference ->
                Log.d(
                    "CloudFire",
                    "update time"
                )
            }
            .addOnFailureListener { e -> Log.w("connect Table", "error time update", e) }
    }


    override fun updateTableState(state: Int) {
        val data = hashMapOf("state" to state)
//todo something make with time, and set table to update
        db.collection(TABLES).document(TEST_TABLE_NAME)
            .set(data, SetOptions.merge())
            .addOnSuccessListener { documentReference ->
                Log.d(
                    "CloudFire",
                    "update state"
                )
            }
            .addOnFailureListener { e -> Log.w("connect Table", "error update state", e) }
    }


    override fun createTable() {
        val dateTable = Table(
            1,
            ArrayList(),
            Date().time,
            Table.TABLE_STATE_CREATED
        )
        playerConnect(dateTable)

        db.collection(TABLES).document(
            TEST_TABLE_NAME
        )
            .set(dateTable)
            .addOnSuccessListener { documentReference ->
                Log.d(
                    "CloudFire",
                    "create Table"
                )
            }
            .addOnFailureListener { e -> Log.w("connect Table", "Error adding document", e) }
    }
//    "DocumentSnapshot added with ID: for 'add'+ documentReference.id"


    override fun tableConnect(table: Table) {

        playerConnect(table)
        val data = hashMapOf("players" to table.players)
//todo something make with time, and set table to update
        db.collection(TABLES).document(TEST_TABLE_NAME)
            .set(data, SetOptions.merge())
            .addOnSuccessListener { documentReference ->
                Log.d(
                    "CloudFire",
                    "connect to Table"
                )
            }
            .addOnFailureListener { e -> Log.w("connect Table", "Error adding document", e) }
    }


    private fun playerConnect(table: Table) {
        if (!table.players.contains(mAuth.currentUser?.email.toString()))
            table.players.add(mAuth.currentUser?.email.toString())
        mainView.setTable(table)
    }

    override fun logaut(){
        mAuth.signOut()
    }

    companion object {
        const val TABLES: String = "tables"
        const val TEST_TABLE_NAME: String = "testTable"
    }
}