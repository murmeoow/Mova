package dm.sample.data.utils

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.database.Cursor
import android.net.Uri
import android.os.Environment

/**
 * @return Download Id
 */
fun DownloadManager.startDownload(
    downloadUrl: String,
    downloadTitle: String?,
    downloadDescription: String,
) : Long {
    val request = DownloadManager.Request(Uri.parse(downloadUrl))
    request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
    request.setTitle(downloadTitle ?: "Untitled")
    request.setDescription(downloadDescription)
    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
    request.setDestinationInExternalPublicDir(
        Environment.DIRECTORY_DOWNLOADS,
        "/Mova/${downloadTitle ?: "Untitled"}.mp4"
    )
    return enqueue(request)
}

/**
 * @return DownloadManager.STATUS
 */
@SuppressLint("Range")
fun DownloadManager.getDownloadStatus(downloadId: Long) : Int {
    val query = DownloadManager.Query()
    query.setFilterById(downloadId)
    val cursor = query(query)
    return if (cursor.moveToFirst()) {
        cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
    } else {
        DownloadManager.STATUS_FAILED
    }
}

fun DownloadManager.isDownloadingRunning(downloadId: Long) : Boolean {
    return when(getDownloadStatus(downloadId)) {
        DownloadManager.STATUS_PAUSED,
        DownloadManager.STATUS_PENDING,
        DownloadManager.STATUS_RUNNING -> {
            true
        }
        else -> {
            false
        }
    }
}

fun DownloadManager.getCursorById(downloadId: Long) : Cursor {
    val query = DownloadManager.Query()
    query.setFilterById(downloadId)
    return query(query)
}

fun DownloadManager.getCursorByStatus(status: Int) : Cursor {
    val query = DownloadManager.Query()
    query.setFilterByStatus(status)
    return query(query)
}

@SuppressLint("Range")
fun Cursor.getBytesDownloaded() : Float {
    return getFloat(getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR))
}

@SuppressLint("Range")
fun Cursor.getBytesTotal() : Float {
    return getFloat(getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES))
}

@SuppressLint("Range")
fun Cursor.getDownloadStatus() : Int {
    return getInt(getColumnIndex(DownloadManager.COLUMN_STATUS))
}