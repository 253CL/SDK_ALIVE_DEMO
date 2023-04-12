package com.chuanglan.sdk.face.demo.net

import retrofit2.http.POST
import retrofit2.http.FormUrlEncoded
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.FieldMap

/**
 * @Author:wanggang
 * @Description: 主要功能：
 * @CreateDate: 2022/11/4 11:00
 */
interface VerifyService {
    /**
     * 查询检测结果接口
     *
     * @param params
     * @return
     */
    @POST("/sdk/liveSDK/livingDetection/test")
    @FormUrlEncoded
    fun faceVerify(@FieldMap params: MutableMap<String, Any>): Call<ResponseBody?>?
}