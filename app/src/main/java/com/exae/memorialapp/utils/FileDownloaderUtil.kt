package com.exae.memorialapp.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import androidx.core.content.FileProvider
import com.alibaba.android.arouter.launcher.ARouter
import java.io.File
import java.lang.ref.WeakReference
import java.util.*

object FileDownloaderUtil {
    private var mContext: WeakReference<Context>? = null
    private var singleTaskId = 0
    private var pathStr: String? = null
    private var stateListener: DownLoadStateListener? = null

    private fun deleteFile() {
        val dir = File(
            mContext?.get()?.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString()
        )
        deleteDirFile(dir)
        //删除单个任务的database记录
//        val deleteData = FileDownloader.getImpl().clear(singleTaskId, mSaveFolder)
//        val targetFile = File(mSinglePath)
//        var delate = false
//        if (targetFile.exists()) {
//            delate = targetFile.delete()
//        }

//        File(FileDownloadUtils.getTempPath(mSinglePath)).delete()
    }

    private fun deleteDirFile(dir: File?) {
        if (dir == null || !dir.exists() || !dir.isDirectory) return
        for (file in dir.listFiles()) {
            if (file.isFile) {
                file.delete()
            } else if (file.isDirectory) {
                deleteDirFile(file)
            }
        }
    }

    private fun openFile() {
        val intent = Intent(Intent.ACTION_VIEW)
        // 由于没有在Activity环境下启动Activity,设置下面的标签
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        //Android 7.0以上要使用FileProvider
        val file = File(pathStr)
        val type = getMIMEType(file)
        if (type == "application/pdf") {
            ARouter.getInstance().build("/pos/pdf/preview")
                .withString("pathFile", pathStr)
                .navigation()
        } else {
            mContext?.get()?.let {
                val apkUri: Uri =
                    FileProvider.getUriForFile(it, "cn.porsche.dealer.pos.fileProvider", file)
                //添加这一句表示对目标应用临时授权该Uri所代表的文件
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                intent.setDataAndType(apkUri, type)
                try {
                    it.startActivity(intent)
                } catch (e: Exception) {
                    Toast.makeText(mContext?.get(), "没有找到合适的软件打开", Toast.LENGTH_LONG).show()
                }
            }
        }

    }

    //依据文件名得到MIME类型
    private fun getMIMEType(file: File): String {
        val type = "*/*"
        val fName = file.name
        //获取后缀名前的分隔符"."在fName中的位置。
        val dotIndex = fName.lastIndexOf(".")
        if (dotIndex < 0) {
            return type
        }
        val end = fName.substring(dotIndex, fName.length).toLowerCase(Locale.getDefault())
        if (end === "") return type
        for (item in mimeMap.keys) {
            if (item == end) {
                return mimeMap[item] ?: type
            }
        }
        return type
    }

    //MIME类型
    private val mimeMap = mapOf(
        ".apk" to "application/vnd.android.package-archive",
        ".doc" to "application/msword",
        ".docx" to "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
        ".pdf" to "application/pdf",
        ".zip" to "application/x-zip-compressed",
        ".txt" to "text/plain"
    )

    interface DownLoadStateListener {
        fun stateRunning(progress: Int)
        fun stateSuccess()
        fun stateFailed()
    }

}