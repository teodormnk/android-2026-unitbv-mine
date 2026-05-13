package cst.unitbvfmi2026.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import cst.unitbvfmi2026.data.AppDataBase
import cst.unitbvfmi2026.data.entities.UserEntity
import cst.unitbvfmi2026.network.RetrofitClient
import cst.unitbvfmi2026.network.dtos.toEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class UsersViewModel(
    application: Application
) : AndroidViewModel(application) //ofera context la instantierea bazei de date
{
    private val userDao = AppDataBase.getDatabase(application).userDao()
    val users = userDao.getAll().stateIn(
        scope = viewModelScope,//pt corutina in backgr
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),//porneste la collect
        initialValue = emptyList()
    )
    fun insert(email: String, name: String)
    {
        viewModelScope.launch{
            userDao.insert(UserEntity(name = name, email = email))
        }
    }

    fun loadUsers()
    {
        viewModelScope.launch{
            try{
                val results = RetrofitClient.usersApi.getUsers(1)
                val userList = results.data.map{ userDto -> //map = un fel de foreach, ii atribuie o modif fiecarui obj si returneaza lista cu obiectele modificate
                    userDto.toEntity(null)
                }
                userDao.insert(userList)
            }
            catch(e : Exception){
                Log.e("UsersViewModel", "Failed to load users: ${e.message}")
            }
        }
    }
}