package com.exae.memorialapp.utils

import android.content.Context
import android.os.Environment
import com.exae.memorialapp.CusApplication
import java.io.File


object DataCleanManager {

    //删除内外缓存
    fun clearAllCache() {
        deleteDir(CusApplication.instance().cacheDir)
        cleanSharedPreference(CusApplication.instance())
        cleanWebViewFiles(CusApplication.instance())
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            deleteDir(CusApplication.instance().externalCacheDir)
        }
    }

    private fun deleteDir(dir: File?): Boolean? {
        if (dir != null && dir.exists() && dir.isDirectory) {
            val children: Array<String> = dir.list()
            for (i in children.indices) {
                val success = deleteDir(File(dir, children[i])) ?: return false
                if (!success) {
                    return false
                }
            }
        }
        return dir?.delete()
    }

    /**
     * 删除方法 这里只会删除某个文件夹下的所以文件及文件夹，如果传入的directory是个文件，将不做处理
     */
    private fun deleteFilesByDirectory(directory: File?) {
        if (directory != null && directory.exists() && directory.isDirectory) {
            for (item in directory.listFiles()) {
                if (item.isDirectory && item.listFiles() != null && item.listFiles().isNotEmpty()) {
                    // 非空文件夹，递归处理
                    deleteFilesByDirectory(item)
                } else {
                    item.delete()
                }
            }
        }
    }

    /**
     * 清除本应用所有数据库(/data/data/com.xxx.xxx/databases)
     */
    private fun cleanDatabases(context: Context) {
        deleteFilesByDirectory(
            File(
                ("/data/data/"
                        + context.packageName) + "/databases"
            )
        )
    }

    /**
     * 清除本应用SharedPreference(/data/data/com.xxx.xxx/shared_prefs)
     */
    private fun cleanSharedPreference(context: Context) {
        deleteFilesByDirectory(
            File(
                ("/data/data/"
                        + context.packageName) + "/shared_prefs"
            )
        )
    }

    private fun cleanWebViewFiles(context: Context) {
        deleteFilesByDirectory(
            File(
                ("/data/data/"
                        + context.packageName) + "/app_webview"
            )
        )
    }

    private fun cleanAllFiles(context: Context) {
        deleteFilesByDirectory(
            File(
                ("/data/data/"
                        + context.packageName)
            )
        )
    }

    /**
     * * 清除本应用内部缓存(/data/data/com.xxx.xxx/cache) * *
     */
    private fun cleanInternalCache(context: Context) {
        deleteFilesByDirectory(context.cacheDir)
    }

    private fun cleanFiles(context: Context) {
        deleteFilesByDirectory(context.filesDir)
    }
}