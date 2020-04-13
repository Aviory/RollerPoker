package com.saymewhy.forfriend.rollerpoker.core

import android.content.Intent
import android.widget.Toast
import com.saymewhy.forfriend.rollerpoker.LoginActivity
import com.saymewhy.forfriend.rollerpoker.MainActivity
import com.saymewhy.forfriend.rollerpoker.entity.Table
import com.saymewhy.forfriend.rollerpoker.firebase.CloudFire
import com.saymewhy.forfriend.rollerpoker.interfaces.DB
import com.saymewhy.forfriend.rollerpoker.interfaces.GameCenterInterface
import java.util.*
import kotlin.collections.HashMap


class GameCenter(val view: MainActivity):GameCenterInterface {

    val timer: Timer = Timer()
    lateinit var table: Table
    val db: DB = CloudFire(view,this)


    fun start(table: Table) {
        this.table = table
        updateTable()
    }

    private fun updateTable() {
        when (table.state) {
            Table.TABLE_STATE_CREATED -> {
                db.updateTableState(Table.TABLE_STATE_WAIT_FOR_PLAYERS)
            }
            Table.TABLE_STATE_WAIT_FOR_PLAYERS -> {
                if (table.players.size > 2) {
                    db.updateTimer(Date().time + 3600000)
                    db.updateTableState(Table.TABLE_STATE_WAIT_FOR_PLAYERS_ONE_HOUR)
                }
                else
                    Toast.makeText(
                        view,
                        "до начала отсчета нужно : ${3 - table.players.size} роллермонстров",
                        Toast.LENGTH_SHORT
                    )

            }
            Table.TABLE_STATE_WAIT_FOR_PLAYERS_ONE_HOUR ->{
                if (table.timeNextState < Date().time || table.players.size > 4){
                    Toast.makeText(view, "игра началась, ребятки :)", Toast.LENGTH_SHORT).show()
                    db.updateTableState(Table.TABLE_STATE_IN_GAME)
                    startGame()
                }else
                    Toast.makeText(
                        view,
                        "до начала игры : ${5 - table.players.size} роллера или ${Date(table.timeNextState-Date().time).minutes} минут ",
                        Toast.LENGTH_SHORT
                    ).show()
            }
            Table.TABLE_STATE_IN_GAME -> {

            }
        }

    }


    fun startTimer() {
        timer.schedule(object : TimerTask() {
            override fun run() {
                // some actions

            }
        }, 1, 1000)
    }


    private fun startGame() {
        when (table.state) {
            Table.TABLE_STATE_IN_GAME->{
                db.getCard()
            }
        }

    }


    fun logaut() {
        db.logaut()
        val intent = Intent(view, LoginActivity::class.java)
        // start your next activity
        view.startActivity(intent)
    }


    override fun setTricksCard(tricks: LinkedList<HashMap<Int, String>>) {
        when (table.state) {
            Table.TABLE_STATE_IN_GAME->{
                val list = LinkedList<String>()
                list.add(tricks[0][0].toString())
                list.add(tricks[0][1].toString())
                list.add(tricks[0][2].toString())
                view.setCard(list)
            }
        }
    }


}