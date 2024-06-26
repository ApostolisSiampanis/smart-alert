package eu.tkacas.smartalert.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import eu.tkacas.smartalert.R
import eu.tkacas.smartalert.ui.theme.PrussianBlue

@Composable
fun PermissionDialog(
    permissionTextProvider: PermissionTextProvider,
    isPermanentlyDeclined: Boolean,
    onDismiss: () -> Unit,
    onOkClick: () -> Unit,
    onGoToAppSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        buttons = {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Divider()
                Text(
                    text = if (isPermanentlyDeclined) {
                        "Grant permission"
                    } else {
                        "OK"
                    },
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            if (isPermanentlyDeclined) {
                                onGoToAppSettingsClick()
                            } else {
                                onOkClick()
                            }
                        }
                        .padding(16.dp)
                )
            }
        },
        title = {
            Text(text = "Permission required")
        },
        text = {
            Text(
                text = permissionTextProvider.getDescription(
                    isPermanentlyDeclined = isPermanentlyDeclined
                )
            )
        },
        modifier = modifier
    )
}

interface PermissionTextProvider {
    fun getDescription(isPermanentlyDeclined: Boolean): String
}

class NotificationPermissionTextProvider : PermissionTextProvider {
    override fun getDescription(isPermanentlyDeclined: Boolean): String {
        return if (isPermanentlyDeclined) {
            "Notification permission is required to send you alerts. Please go to app settings and enable the notification permission."
        } else {
            "Notification permission is required to send you alerts. Please enable the notification permission."
        }
    }
}

class LocationPermissionTextProvider : PermissionTextProvider {
    override fun getDescription(isPermanentlyDeclined: Boolean): String {
        return if (isPermanentlyDeclined) {
            "Location permission is required to get your current location. Please go to app settings and enable the location permission."
        } else {
            "Location permission is required to get your current location. Please enable the location permission."
        }
    }
}

class CameraPermissionTextProvider : PermissionTextProvider {
    override fun getDescription(isPermanentlyDeclined: Boolean): String {
        return if (isPermanentlyDeclined) {
            "Camera permission is required to take a picture. Please go to app settings and enable the camera permission."
        } else {
            "Camera permission is required to take a picture. Please enable the camera permission."
        }
    }
}

@Composable
fun AlertWithImageDialog(
    showDialog: Boolean,
    message: String?,
    imageURL: String?,
    onDismiss: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                Text(
                    text = stringResource(id = R.string.Citizen_message),
                    color = PrussianBlue
                )
            },
            text = {
                Column(
                    modifier = Modifier
                        .size(400.dp)
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = message ?: "")
                    if (!imageURL.isNullOrEmpty()) {
                        Image(
                            painter = rememberAsyncImagePainter(model = imageURL),
                            contentDescription = "Image",
                            modifier = Modifier
                                .wrapContentSize()
                                .clip(RoundedCornerShape(15.dp))
                        )
                    } else {
                        Text(
                            text = stringResource(id = R.string.No_image_available),
                            color = Color.Red,
                        )
                    }
                }
            },
            confirmButton = {
                GeneralButtonComponent(
                    value = stringResource(id = R.string.close),
                    onButtonClicked = { onDismiss() }
                )
            },
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
        )
    }
}

@Composable
fun NotificationsHistoryDialog(
    showDialog: Boolean,
    weatherPhenomenonText: String = "Earthquake",
    locationText: String = "Kifissia, Athens",
    dateTimeText: String = "2024-02-20 10:00",
    messageText: String = "This is a test message",
    onDismiss: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(text = weatherPhenomenonText, color = PrussianBlue) },
            text = {
                Box(
                    modifier = Modifier
                        .height(400.dp)
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .padding(16.dp)
                    ) {
                        item {
                            Text(text = locationText)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = dateTimeText)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = messageText)
                        }
                    }
                }
            },
            confirmButton = {
                GeneralButtonComponent(
                    value = stringResource(id = R.string.close),
                    onButtonClicked = { onDismiss() }
                )
            },
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
        )
    }
}

@Composable
fun ConfirmDeleteDialog(
    showDialog: Boolean,
    title: String,
    message: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            title = { Text(title) },
            text = { Text(message) },
            confirmButton = {
                TextButton(
                    onClick = { onConfirm() }
                ) {
                    Text(text = stringResource(id = R.string.confirm), color = PrussianBlue)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { onDismiss() }
                ) {
                    Text(text = stringResource(id = R.string.cancel), color = Color.Red)
                }
            }
        )
    }
}