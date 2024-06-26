package eu.tkacas.smartalert.viewmodel.navigation

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import eu.tkacas.smartalert.app.SharedPrefManager
import eu.tkacas.smartalert.database.cloud.CloudFunctionsUtils
import eu.tkacas.smartalert.database.cloud.FirebaseUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NavigationViewModel(context: Context) : ViewModel() {

    val firebase = FirebaseUtils()
    private var _cloudFunctionsUtils: CloudFunctionsUtils = CloudFunctionsUtils()
    private var sharedPrefManager: SharedPrefManager = SharedPrefManager(context)

    fun findStartDestination(): String = if (firebase.userExists()) {
        "home"
    } else {
        "welcome"
    }

    fun setUserIdentity() {
        CoroutineScope(Dispatchers.IO).launch {
            val isEmployee = _cloudFunctionsUtils.userIsEmployee()
            sharedPrefManager.setIsEmployee(isEmployee)
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @Composable
    fun permissionsAreGranted(): Boolean {
        val locationPermission = ContextCompat.checkSelfPermission(
            LocalContext.current,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val notificationPermission = ContextCompat.checkSelfPermission(
            LocalContext.current,
            Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED

        return locationPermission && notificationPermission
    }

}