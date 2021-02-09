package net.simplifiedcoding.mvvmsampleapp.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Quote(
    @PrimaryKey(autoGenerate = false)
    val quoteId: Int
)