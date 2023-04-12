package com.chuanglan.sdk.face.demo.utils

import android.content.Context
import com.chuanglan.sdk.face.demo.utils.AppStringUtils
import java.lang.Exception

/**
 * @Author:wanggang
 * @Description: 主要功能：字符串处理工具
 * @CreateDate: 2020/12/26 15:31
 */
object AppStringUtils {
    /**
     * 判断字符串是否为空
     *
     * @param str 需要检测的字符串
     * @return true：空；false：不为空；
     */
    fun isEmpty(str: String?): Boolean {
        return str == null || "" == str || str.trim { it <= ' ' }.isEmpty() || "null" == str
    }

    fun isNotEmpty(str: String?): Boolean {
        return !isEmpty(str)
    }
}