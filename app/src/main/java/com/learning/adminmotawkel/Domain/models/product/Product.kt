package com.learning.adminmotawkel.Domain.models.product

data class Product(
    val id :String,
    var sectionImage:String,
    var sectionName:String,
    var tradeMarkImage:String,
    var itemImage:String,
    var itemName:String,
    var singlePrice: String,
    var allPrice: String
    ,
    var hasOffer:Boolean,
    var isBestSeller:Boolean,
    var offerForYou:Boolean,
)
