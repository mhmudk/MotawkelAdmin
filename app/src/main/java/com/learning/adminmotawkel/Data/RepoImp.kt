package com.learning.adminmotawkel.Data

import com.google.firebase.firestore.FirebaseFirestore
import com.learning.adminmotawkel.Domain.FirebaseOperations
import com.learning.adminmotawkel.Domain.models.TradeMarks.TradeMarksModel
import com.learning.adminmotawkel.Domain.models.items.ItemModel
import com.learning.adminmotawkel.Domain.models.sections.SectionModel
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RepoImp   @Inject constructor(
    private val db: FirebaseFirestore
) : FirebaseOperations {

    private val sectionCollection = db.collection("sections")
    override suspend fun addSection(sectionModel: SectionModel) {
        sectionCollection.add(sectionModel).await()
    }

    override suspend fun getSections(): List<SectionModel> {
        val snapshot = sectionCollection.get().await()
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