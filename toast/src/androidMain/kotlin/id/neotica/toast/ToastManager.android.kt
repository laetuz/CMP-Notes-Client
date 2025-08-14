package id.neotica.toast

import android.app.Activity

actual open class ToastManager actual constructor() {
    actual fun showToast(
        message: String,
        toastDuration: ToastDuration?
    ) {
        activityProvider()?.runOnUiThread {
            android.widget.Toast.makeText(activityProvider(), message, android.widget.Toast.LENGTH_SHORT).show()
        }
    }
}

private var activityProvider: () -> Activity? = {
    null
}

fun setActivityProvider(provider: () -> Activity?) {
    activityProvider = provider
}