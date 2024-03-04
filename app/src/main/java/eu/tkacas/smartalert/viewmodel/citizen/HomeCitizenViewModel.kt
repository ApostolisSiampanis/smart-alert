package eu.tkacas.smartalert.viewmodel.citizen

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.tkacas.smartalert.app.SharedPrefManager
import eu.tkacas.smartalert.database.cloud.FirebaseUtils
import eu.tkacas.smartalert.database.local.DatabaseHelper
import eu.tkacas.smartalert.models.ListOfHistoryMessages
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeCitizenViewModel( context: Context )  : ViewModel() {

    private val databaseHelper = DatabaseHelper(context)
    private val firebase = FirebaseUtils()

    val sharedPrefManager = SharedPrefManager(context)



    private val _data = MutableStateFlow<ListOfHistoryMessages?>(null)
    val data: StateFlow<ListOfHistoryMessages?> = _data

    private val _errorM = MutableStateFlow<String?>(null)
    val errorM: StateFlow<String?> = _errorM

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing

    val firstNameVal = MutableStateFlow(sharedPrefManager.getFirstName())

    init {
        fetchData()
        fetchFirstName()
    }

    fun fetchData() {
        viewModelScope.launch {
            _isRefreshing.value = true
            databaseHelper.getMessages { success, result, err ->
                if (success) {
                    _data.value = result
                } else {
                    _errorM.value = err
                }
                _isRefreshing.value = false
            }
        }
    }

    private fun fetchFirstName() {
        firebase.getFirstName { success, firstName, error ->
            if (success) {
                sharedPrefManager.setFirstName(firstName ?: "")
            } else {
                println("Error occurred: $error")
            }
        }
    }
}