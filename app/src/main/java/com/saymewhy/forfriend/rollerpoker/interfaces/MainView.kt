package com.saymewhy.forfriend.rollerpoker.interfaces

import com.saymewhy.forfriend.rollerpoker.entity.Table
import java.util.*

interface MainView {
    fun setTable(table: Table)
    fun setCard(list: LinkedList<String>)
}
