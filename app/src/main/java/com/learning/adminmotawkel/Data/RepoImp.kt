package com.learning.adminmotawkel.Data

import com.google.firebase.firestore.FirebaseFirestore
import com.learning.adminmotawkel.Core.Helpers.showLogs
import com.learning.adminmotawkel.Domain.FirebaseOperations
import com.learning.adminmotawkel.Domain.models.product.Category
import com.learning.adminmotawkel.Domain.models.product.ItemModel
import com.learning.adminmotawkel.Domain.models.product.TradeMarksModel
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RepoImp @Inject constructor(
    private val db: FirebaseFirestore
) : FirebaseOperations {

    private val product = db.collection("products")
    override suspend fun addProduct(prod: Category): Boolean {
        return try {
            val docRef = product.document()
            val sectionWithId = prod.copy(id = docRef.id)
            docRef.set(sectionWithId).await()
            true
        } catch (e: Exception) {
            showLogs(e.message.toString())
            false
        }
    }


    override suspend fun addTradeMarks(
        tradeMarksModel: TradeMarksModel,
        categoryId: String
    ): Boolean {
        return try {
            val tradeMarksCollection = db.collection("products")
                .document(categoryId)
                .collection("tradeMarks")

            val docRef = tradeMarksCollection.document()
            val tradeMarkWithId = tradeMarksModel.copy(id = docRef.id)

            docRef.set(tradeMarkWithId).await()
            true
        } catch (e: Exception) {
            showLogs("Error adding trade mark: ${e.message}")
            false
        }
    }


    override suspend fun addItems(
        itemModel: ItemModel,
        categoryId: String,
        tradeMarkId: String
    ): Boolean {
        return try {
            val itemsCollection = product
                .document(categoryId)
                .collection("tradeMarks")
                .document(tradeMarkId)
                .collection("items")

            val docRef = itemsCollection.document()
            val itemWithId = itemModel.copy(id = docRef.id)
            docRef.set(itemWithId).await()
            true
        } catch (e: Exception) {
            showLogs("Error adding item: ${e.message}")
            false
        }
    }

    override suspend fun getItems(categoryId: String, tradeMarkId: String): List<ItemModel> {
        val snapshot = product
            .document(categoryId)
            .collection("tradeMarks")
            .document(tradeMarkId)
            .collection("items")
            .get()
            .await()

        return snapshot.toObjects(ItemModel::class.java)
    }

    override suspend fun getSections(): List<Category> {
        val snapshot = product.get().await()
        return snapshot.toObjects(Category::class.java)

    }

    override suspend fun getTradeMarks(categoryId: String): List<TradeMarksModel> {
        val snapshot = product
            .document(categoryId)
            .collection("tradeMarks")
            .get()
            .await()

        return snapshot.toObjects(TradeMarksModel::class.java)
    }
}