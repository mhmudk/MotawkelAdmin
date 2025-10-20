package com.learning.adminmotawkel.Domain.models.product
import java.io.Serializable
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    val id :String?="",
    var sectionImage:String?="",
    var sectionName:String?="",
): Parcelable



@Parcelize
data class TradeMarksModel(
    var id : String  ?="",
    var tradeMarkImage:String?="",
): Parcelable


@Parcelize
data class ItemModel(
    var id : String ?="",
    var itemImage:String?="",
    var itemName:String?="",
    var singlePrice: String?="",
    var allPrice: String?="",
    var hasOffer:Boolean=false,
    var isBestSeller:Boolean=false,
    var offerForYou:Boolean=false,
    var singlePriceName:String ?="",
    var allPriceName:String ?=""
): Parcelable
