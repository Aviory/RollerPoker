package com.saymewhy.forfriend.rollerpoker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.saymewhy.forfriend.rollerpoker.core.GameCenter
import com.saymewhy.forfriend.rollerpoker.entity.Table
import com.saymewhy.forfriend.rollerpoker.interfaces.MainView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(),MainView {

    lateinit var gameCore :GameCenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        gameCore = GameCenter(this)

    }

    override fun setTable(table: Table) {
        gameCore.start(table)

        for (i in table.players.indices){
            when(i){
                0 -> et_player_1.setText(table.players[i])
                1 -> et_player_2.setText(table.players[i])
                2 -> et_player_3.setText(table.players[i])
                3 -> et_player_4.setText(table.players[i])
                4 -> {
                    et_player_5.setText(table.players[i])
                    startGame()
                }
            }


        }

    }

    override fun setCard(list: LinkedList<String>) {
        for (i in list.indices)
        when(i) {
            0 -> et_card_1.setText(list[i])
            1 -> et_card_2.setText(list[i])
            2 -> et_card_3.setText(list[i])
        }
    }

    private fun startGame() {
        TODO("Not yet implemented")
    }

    override fun onBackPressed() {
        gameCore.logaut()
    }
}
