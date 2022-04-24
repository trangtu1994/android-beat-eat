package com.example.beatoreat.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.beatoreat.database.entities.UserDto

@Dao
interface UserDao {

    @Query("SELECT * FROM userdto")
    public fun getAll() : List<UserDto>

    @Query("SELECT * FROM UserDto WHERE id like :userId")
    public fun getById(userId: Int) : UserDto?

    @Query("SELECT * FROM UserDto WHERE name like :userName")
    public fun getByName(userName: String) : UserDto?

    @Insert
    public fun insert(userDto: UserDto)

    @Insert
    public fun insertAll(users: List<UserDto>)

    @Delete
    public fun remove(userDto: UserDto)
}