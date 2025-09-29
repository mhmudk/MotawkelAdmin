package com.learning.adminmotawkel.Domain

import com.learning.adminmotawkel.Domain.models.TradeMarks.TradeMarksModel
import com.learning.adminmotawkel.Domain.models.items.ItemModel
import com.learning.adminmotawkel.Domain.models.product.Product
import com.learning.adminmotawkel.Domain.models.sections.SectionModel

interface FirebaseOperations {

    suspend fun  addProduct(product: Product) :Boolean
    suspend fun  getSections():  List<SectionModel>


    suspend fun  addTradeMarks(tradeMarksModel: TradeMarksModel)
    suspend fun  getTradeMarks():  List<TradeMarksModel>

    suspend fun  addItem(itemModel: ItemModel)
    suspend fun  getItems():  List<ItemModel>


}