package dm.sample.mova.ui.viewmodel.profile.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat

fun isWriteExternalStoragePermissionGranted(context: Context): Boolean {
    return ActivityCompat.checkSelfPermission(context,
        Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
}

fun isReadExternalStoragePermissionGranted(context: Context): Boolean {
    return ActivityCompat.checkSelfPermission(context,
        Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
}