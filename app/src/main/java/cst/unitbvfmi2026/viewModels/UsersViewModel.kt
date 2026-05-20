package cst.unitbvfmi2026.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import cst.unitbvfmi2026.data.AppDataBase
import cst.unitbvfmi2026.data.entities.UserEntity
import cst.unitbvfmi2026.network.RetrofitClient
import cst.unitbvfmi2026.network.dtos.toEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class UsersViewModel(
    application: Application,
    val addressId: Long
) : AndroidViewModel(application) //ofera context la instantierea bazei de date
{
//    private val userDao = AppDataBase.getDatabase(application).userDao()
//    val users = userDao.getAll().stateIn(
//        scope = viewModelScope,//pt corutina in backgr
//        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),//porneste la collect
//        initialValue = emptyList()
//    )
    //operatie de "get" folosind relatia:
    private val userDao = AppDataBase.getDatabase(application).userDao()
    private val addressDao = AppDataBase.getDatabase(application).addressDao()
    val users = addressDao.getAddressWithUsers(addressId).stateIn(
        scope = viewModelScope,//pt corutina in backgr
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),//porneste la collect
        initialValue = null
    )

    override fun onCleared() { //parte din ciclul de viata al ViewModel-ului
        super.onCleared()
    }
    fun insert(email: String, name: String) {
        viewModelScope.launch {
            userDao.insert(UserEntity(name = name, email = email, addressId = addressId))
        }
    }

    fun loadUsers() {
        viewModelScope.launch {
            try {
                val results = RetrofitClient.usersApi.getUsers(1)
                val userList =
                    results.data.map { userDto -> //map = un fel de foreach, ii atribuie o modif fiecarui obj si returneaza lista cu obiectele modificate
                        userDto.toEntity(addressId)
                    }
                userDao.insert(userList)
            } catch (e: Exception) {
                Log.e("UsersViewModel", "Failed to load users: ${e.message}")
            }
        }
    }

    companion object {
        fun factory(application: Application, addressId: Long): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory { // sintaxa "object:" echiv in Java pt mom in care creem o instanta anonima pentru o interfata/clasa
                @Suppress("UNCHECKED_CAST") // pentru eliminare warning
                override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                    return UsersViewModel(application, addressId) as T
                }
            }
        // ViewModelProvider = ne juta sa creem un ViewModel, dar cu proprietati in plus fata de default
    }//"pet" al clasei - analogie jocuri (Tamagochi), simuleaza "static" din Java; NU putem avea mai multe companion objects intr-o clasa!
}