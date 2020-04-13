package com.saymewhy.forfriend.rollerpoker.interfaces

import com.saymewhy.forfriend.rollerpoker.entity.Table

interface DB {
    fun createTable()
    fun tableConnect(table: Table)
    fun getTable()
    fun updateTableState(state:Int)
    fun updateTimer(time: Long)
    fun logaut()
}