package com.tableflow.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ReservationDao {

    @Query("SELECT * FROM reservations ORDER BY date, time")
    fun observeAll(): Flow<List<ReservationEntity>>

    @Insert
    suspend fun insert(reservation: ReservationEntity)

    @Query("SELECT * FROM reservations WHERE date = :date AND time = :time")
    suspend fun getForSlot(date: String, time: String): List<ReservationEntity>
}
