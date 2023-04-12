package com.chuanglan.sdk.face.demo.net

import android.util.Log
import com.chuanglan.sdk.face.demo.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import com.chuanglan.sdk.face.demo.activity.MainActivity
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.ResponseBody
import retrofit2.Call
import java.util.HashMap

/**
 * @Author:wanggang
 * @Description: 主要功能：
 * @CreateDate: 2022/11/4 11:07
 */
class RequestParams {
    //日志显示级别
    private val okHttpClient: OkHttpClient
        //新建log拦截器
        //定制OkHttp
        //OkHttp进行添加拦截器loggingInterceptor
        private get() {
            //日志显示级别
            val level = HttpLoggingInterceptor.Level.BODY
            //新建log拦截器
            val loggingInterceptor = HttpLoggingInterceptor { message: String ->
                Log.e(
                    MainActivity.TAG,
                    "OkHttp====Message:$message"
                )
            }
            loggingInterceptor.level = level
            //定制OkHttp
            val httpClientBuilder = OkHttpClient.Builder()
            //OkHttp进行添加拦截器loggingInterceptor
            httpClientBuilder.addInterceptor(loggingInterceptor)
            return httpClientBuilder.build()
        }//使用自己创建的OkHttp

    //创建网络请求接口实例
    //创建Retrofit对象
    val service: VerifyService
        get() {
            //创建Retrofit对象
            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create()) //使用自己创建的OkHttp
                .client(okHttpClient)
                .build()

            //创建网络请求接口实例
            return retrofit.create(VerifyService::class.java)
        }

    fun queryFaceVerify(cerityId: String): Call<ResponseBody?>? {
        val params: MutableMap<String, Any> = HashMap(1)
        params["certifyId"] = cerityId
        return service.faceVerify(params)
    }
}