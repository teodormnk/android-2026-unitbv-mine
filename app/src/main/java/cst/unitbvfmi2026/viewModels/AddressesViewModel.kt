package cst.unitbvfmi2026.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import cst.unitbvfmi2026.data.AppDataBase
import cst.unitbvfmi2026.data.entities.UserEntity
import cst.unitbvfmi2026.network.RetrofitClient
import cst.unitbvfmi2026.network.dtos.toEntity
import cst.unitbvfmi2026.data.entities.AddressEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AddressesViewModel(
    application: Application
) : AndroidViewModel(application) //ofera context la instantierea bazei de date
{
    private val addressDao = AppDataBase.getDatabase(application).addressDao()
    val addresses = addressDao.getAll().stateIn(
        scope = viewModelScope,//pt corutina in backgr
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),//porneste la collect
        initialValue = emptyList()
    )
    fun insert(city: String, country: String)
    {
        viewModelScope.launch{
            addressDao.insert(AddressEntity(city = city, country = country))
        }
    }
}