package com.example.yourinterest.data.local

import com.example.yourinterest.data.model.user.UserModel
import com.example.yourinterest.db.InterestsDB
import com.example.yourinterest.util.DataOrException

class UsersDBLocalSource(private val database: InterestsDB) {


     fun getUser(): DataOrException<UserModel, Exception, Boolean> {
        return try {
            val result = database.usersDBQueryQueries.getUser().executeAsOne()
            val user = UserModel(
                id = result.id.toInt(),
                name = result.name ?: "",
                phone = result.phone,
                photoUrl = result.photoUrl
            )
            DataOrException(data = user, exception = null, isLoading = false)
        } catch(error: Exception) {
            print("error getUser: $error")
            DataOrException(data = null, exception = error, isLoading = false)
        }

     }


    fun insertUser(user: UserModel) {
        database.transaction {
            database.usersDBQueryQueries.insertUser(
                id = user.id.toLong(),
                name = user.name,
                phone = user.phone,
                photoUrl = user.photoUrl
            )
        }
    }

}