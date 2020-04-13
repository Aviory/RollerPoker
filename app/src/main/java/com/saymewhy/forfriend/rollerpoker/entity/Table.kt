package com.saymewhy.forfriend.rollerpoker.entity

import android.media.AsyncPlayer
import com.google.firebase.auth.FirebaseUser
import com.google.type.TimeOfDay

data class Table (
    val id: Int = 0,
    val players: ArrayList<String> = ArrayList(),
    var timeNextState: Long = 0,
    var state:Int = TABLE_STATE_CREATED
){
    companion object {
        const val TABLE_STATE_CREATED: Int = 0
        const val TABLE_STATE_WAIT_FOR_PLAYERS: Int = 1
        const val TABLE_STATE_WAIT_FOR_PLAYERS_ONE_HOUR: Int =2
        const val TABLE_STATE_IN_GAME: Int = 3
    }
}
