package com.learning.adminmotawkel.Domain.models.TradeMarks

data class TradeMarksModel(
    val id : Int ,
    val idSection : Int ,
    val name : String,
    val img : String,
    val hasOffer :Boolean,
    val isTradeMarks :Boolean,
)
