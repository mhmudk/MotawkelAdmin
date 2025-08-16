package com.learning.adminmotawkel.Domain

import com.learning.adminmotawkel.Domain.models.TradeMarks.TradeMarksModel
import com.learning.adminmotawkel.Domain.models.items.ItemModel
import com.learning.adminmotawkel.Domain.models.sections.SectionModel

interface FirebaseOperations {

    suspend fun  addSection(sectionModel: SectionModel)
    suspend fun  getSections():  List<SectionModel>


    suspend fun  addTradeMarks(tradeMarksModel: TradeMarksModel)
    suspend fun  getTradeMarks():  List<TradeMarksModel>

    suspend fun  addItem(itemModel: ItemModel)
    suspend fun  getItems():  List<ItemModel>


}