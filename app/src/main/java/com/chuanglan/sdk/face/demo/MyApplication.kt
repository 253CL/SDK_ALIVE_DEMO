package com.chuanglan.sdk.face.demo

import android.app.Application
import com.chuanglan.sdk.face.api.FaceVerification
import com.tencent.bugly.crashreport.CrashReport

/**
 * @Author:wanggang
 * @Description: 主要功能：启动App时初始化资源
 * @CreateDate: 2022/10/27 10:19
 */
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        //bugly日志手机
        CrashReport.initCrashReport(applicationContext, "8a383583f6", true)
        //开启活体SDK日志开关
        FaceVerification.setPrintConsoleEnable(true)
        FaceVerification.logInfo(true)
        //初始化活体SDK
        FaceVerification.initWithAppId(this, BuildConfig.APP_ID)
    }
}