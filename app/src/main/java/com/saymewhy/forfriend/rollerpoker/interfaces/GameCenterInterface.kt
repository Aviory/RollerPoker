package com.saymewhy.forfriend.rollerpoker.interfaces

import java.util.*
import kotlin.collections.HashMap

interface GameCenterInterface {
    fun setTricksCard(tricks: LinkedList<HashMap<Int, String>>)
}