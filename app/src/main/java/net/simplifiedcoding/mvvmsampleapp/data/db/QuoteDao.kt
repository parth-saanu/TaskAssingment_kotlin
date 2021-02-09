package net.simplifiedcoding.mvvmsampleapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import net.simplifiedcoding.mvvmsampleapp.data.db.entities.Quote

@Dao
interface QuoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertQuote(quote: Quote)

    @Delete
    fun deleteQuote(quotes: Quote)

    @Query("SELECT * FROM Quote where quoteId=:id")
    fun getQuote(id: Int): Quote?

}