package com.learning.adminmotawkel.Domain.models.items

data class ItemModel (
    val id : Int ,
    val idSection : Int ,
    val idTradeMarks : Int ,
    val name : String,
    val img : String,
    val price : Double,
    val priceOffer  :Double,
    val hasOfferForYou :Boolean,
    val bestSeller : Boolean,
    val isCategory:Boolean,
    val categoryName  : String ,
    val categoryQuantity : Int ,
    val categoryPrice :Double ,
    val isSubCategory:Boolean,
    val subCategoryName  : String ,
    val subCategoryQuantity : Int ,
    val subCategoryPrice :Double ,
)