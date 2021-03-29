fun Context.getDeviceIp(): String {
    val wm = this.getSystemService(Context.WIFI_SERVICE) as WifiManager
    @Suppress("DEPRECATION")
    return Formatter.formatIpAddress(wm.connectionInfo.ipAddress)
}


@SuppressLint("HardwareIds")
fun Context.getDeviceId(): String {
    var deviceId = Settings.Secure.getString(this.contentResolver, "android_id")
    return deviceId
}

private val outValue by lazy { TypedValue() }

fun Context.selectableItemBackgroundResId(): Int {
    theme.resolveAttribute(android.R.attr.selectableItemBackground, outValue, true)
    return outValue.resourceId
}

fun Context.isWriteExternalStoragePermissionGranted(): Boolean {
    return getNotGrantedPermissions(listOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)).isEmpty()
}

fun Context.getNotGrantedPermissions(neededPermissions: Collection<String>): List<String> {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
        return emptyList()
    }
    return neededPermissions.filter { this.checkSelfPermission(it) != PackageManager.PERMISSION_GRANTED }
}

fun Context.restartApp() {
    val packageManager = packageManager
    val intent = packageManager.getLaunchIntentForPackage(packageName)
    val componentName = intent!!.component
    val mainIntent = Intent.makeRestartActivityTask(componentName)
    this.startActivity(mainIntent)
    exitProcess(0)
}

fun Context.openAnotherApp(packageName: String) {
    val launchIntent = packageManager.getLaunchIntentForPackage(packageName.trim())
    if (launchIntent != null) {
        try {
            startActivity(launchIntent)
        } catch (e: Exception) {
            //pls add logging here
        }
    }
}

fun Context.getApplicationName(packageName: String = this.packageName): String {
    val pm = applicationContext.packageManager
    var applicationInfo: ApplicationInfo?
    try {
        applicationInfo = pm.getApplicationInfo(packageName, 0)
    } catch (e: PackageManager.NameNotFoundException) {
        applicationInfo = null
    }
    return (if (applicationInfo != null) pm.getApplicationLabel(applicationInfo) else "(unknown)") as String
}

fun Context.getAppVersion(packageName: String, withHash: Boolean = false): String? {
    return packageManager?.getPackageInfo(packageName, 0)?.versionName.let {
        if (withHash) {
            it
        } else {
            it?.split(".")?.dropLast(1)?.joinToString(".")
        }
    }
}

fun Context.getAppInfo(packageName: String = this.packageName, withHash: Boolean = true): String {
    return "${getApplicationName(packageName)} v${getAppVersion(packageName, withHash = withHash)}"
}
