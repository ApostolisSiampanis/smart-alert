package eu.tkacas.smartalert.ui.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import eu.tkacas.smartalert.R

@Composable
fun AppBarBackView(
    title: String,
    navController: NavController? = null,
    enableBackButton: Boolean = true,
    enableSettingsButton: Boolean = true
) {

    TopAppBar(
        title = {
            Text(
                text = title,
                color = colorResource(id = R.color.colorWhite),
                modifier = Modifier
                    .padding(start = 4.dp)
                    .heightIn(max = 24.dp)
            )
        },
        elevation = 3.dp,
        backgroundColor = colorResource(id = R.color.prussian_blue),
        navigationIcon =
        if (enableBackButton) {
            {
                IconButton(onClick = { navController?.navigateUp() }) {
                    Image(
                        painterResource(id = R.drawable.arrow_back),
                        contentDescription = null
                    )
                }
            }
        } else {
            null
        },
        actions = {
            if (enableSettingsButton) {
                IconButton(
                    onClick = {
                        navController?.navigate("settings")
                    }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.settings),
                        contentDescription = null
                    )
                }
            }
        }

    )
}