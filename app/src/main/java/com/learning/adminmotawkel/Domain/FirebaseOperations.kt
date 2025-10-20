package com.learning.adminmotawkel.Domain

import com.learning.adminmotawkel.Domain.models.product.Category
import com.learning.adminmotawkel.Domain.models.product.ItemModel
import com.learning.adminmotawkel.Domain.models.product.TradeMarksModel
import com.learning.adminmotawkel.Domain.models.sections.SectionModel

interface FirebaseOperations {

    suspend fun addProduct(product: Category): Boolean
    suspend fun getSections(): List<Category>


    suspend fun addTradeMarks(tradeMarksModel: TradeMarksModel, categoryId: String): Boolean
    suspend fun getTradeMarks(categoryId: String): List<TradeMarksModel>

    suspend fun getItems(categoryId: String, tradeMarkId: String): List<ItemModel>

    suspend fun addItems(itemModel: ItemModel, categoryId: String, tradeMarkId: String): Boolean
}