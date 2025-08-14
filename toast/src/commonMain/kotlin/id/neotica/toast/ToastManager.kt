package id.neotica.toast

// source: https://proandroiddev.com/how-to-show-toasts-in-compose-multiplatform-android-ios-desktop-with-expect-actual-85c630d46d06

expect open class ToastManager() {
    fun showToast(message: String, toastDuration: ToastDuration ?= ToastDuration.SHORT)
}

enum class ToastDuration {
    SHORT,
    LONG
}