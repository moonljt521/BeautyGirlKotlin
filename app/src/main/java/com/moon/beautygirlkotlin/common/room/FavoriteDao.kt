package com.moon.beautygirlkotlin.common.room;

import androidx.room.*

@Dao
interface FavoriteDao {

    /*当数据库中已经有此项的时候，直接替换*/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavourite(bean: FavoriteBean): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(list: List<FavoriteBean>)

    @Query("SELECT * FROM favorite")
    suspend fun getAll(): List<FavoriteBean>?

    @Query("SELECT * FROM favorite WHERE url = :url")
    suspend fun getFavouriteByUrl(url: String): FavoriteBean?

    @Update
    suspend fun upDate(bean: FavoriteBean)

    @Query("DELETE FROM favorite WHERE id = :id")
    suspend fun delete(id: Int)

    @Query("SELECT * FROM favorite LIMIT 10 OFFSET :offset")
    fun queryByPage(offset : Int): List<FavoriteBean>?

    @Query("DELETE FROM favorite")
    suspend fun removeAll()

}