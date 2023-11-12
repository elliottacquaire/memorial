package com.exae.memorialapp

import android.app.Application
import com.alibaba.android.arouter.BuildConfig
import com.alibaba.android.arouter.launcher.ARouter
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp //1
class CusApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        Logger.addLogAdapter(AndroidLogAdapter())
        initRouter()
//        startKoin{
//            androidLogger()
//            androidContext(this@CusApplication)
//            modules(appModule)
//        }
    }

    private fun initRouter() {
        if (BuildConfig.DEBUG) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog() // 打印日志
            ARouter.openDebug() // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this)
    }

    fun instance(): CusApplication {
        return CusApplication.instance
    }

    companion object {
        private lateinit var instance: CusApplication
        fun instance() = instance
    }
}