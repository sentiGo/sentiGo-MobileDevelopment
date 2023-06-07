package com.sentigo.bangkit.sentigoapp.data.local.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sentigo.bangkit.sentigoapp.data.local.entity.FavoriteEntity

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveFavoriteDb(favorite: FavoriteEntity)

    @Query("DELETE FROM favorite_table WHERE id = :id")
    suspend fun deleteFavoriteDb(id: Int)

    @Query("DELETE FROM favorite_table")
    suspend fun deleteAllFav()

    @Query("SELECT * FROM favorite_table")
    fun getFavoriteDb(): LiveData<List<FavoriteEntity>>
}