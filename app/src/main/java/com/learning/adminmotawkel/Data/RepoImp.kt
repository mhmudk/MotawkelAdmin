package com.learning.adminmotawkel.Data

import com.google.firebase.firestore.FirebaseFirestore
import com.learning.adminmotawkel.Core.Helpers.showLogs
import com.learning.adminmotawkel.Domain.FirebaseOperations
import com.learning.adminmotawkel.Domain.models.TradeMarks.TradeMarksModel
import com.learning.adminmotawkel.Domain.models.items.ItemModel
import com.learning.adminmotawkel.Domain.models.product.Product
import com.learning.adminmotawkel.Domain.models.sections.SectionModel
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RepoImp   @Inject constructor(
    private val db: FirebaseFirestore
) : FirebaseOperations {

    private val product = db.collection("products")
    override suspend fun addProduct(prod: Product): Boolean {
      return   try {
            val docRef = product.document()
            val sectionWithId = prod.copy(id = docRef.id)
            docRef.set(sectionWithId).await()
            true
        } catch (e: Exception) {
          showLogs(e.message.toString())
            false
        }
    }

    override suspend fun getSections(): List<SectionModel> {
        val snapshot = product.get().await()
        return snapshot.toObjects(SectionModel::class.java)
    }

    override suspend fun addTradeMarks(tradeMarksModel: TradeMarksModel) {
        TODO("Not yet implemented")
    }

    override suspend fun getTradeMarks(): List<TradeMarksModel> {
        TODO("Not yet implemented")
    }

    override suspend fun addItem(itemModel: ItemModel) {
        TODO("Not yet implemented")
    }

    override suspend fun getItems(): List<ItemModel >{
        TODO("Not yet implemented")
    }

}