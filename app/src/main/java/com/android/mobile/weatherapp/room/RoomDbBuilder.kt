package com.android.mobile.weatherapp.room

import android.content.Context
import androidx.room.Room

object RoomDbBuilder {
    private var roomDatabase: RoomAppDatabase? = null

    fun init(context: Context): RoomAppDatabase? {
        roomDatabase = Room.databaseBuilder(
            context,
            RoomAppDatabase::class.java, "room-database"
        ).fallbackToDestructiveMigration().allowMainThreadQueries().build()

        return roomDatabase
    }
}