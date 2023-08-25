package com.chuanglan.sdk.face.demo.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.chuanglan.sdk.face.api.FaceVerification
import com.chuanglan.sdk.face.demo.R
import com.chuanglan.sdk.face.demo.net.RequestParams
import com.chuanglan.sdk.face.demo.utils.AppStringUtils
import com.chuanglan.sdk.face.entity.VerifyResponse
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * @Author:wanggang
 * @CreateDate: 2020/4/27 16:29
 */
class MainActivity : AppCompatActivity() {
    private var mConfigBtn: Button? = null
    private var mPreVerifyBtn: Button? = null
    private var mStartDetectBtn: Button? = null
    private var mStartVerifyBtn: Button? = null
    private var mCertifyId: String? = null
    var mLogInfo: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_activity_face_verify_demo)
        initView()
        setListener()
    }

    private fun initView() {
        mLogInfo = findViewById(R.id.log_info)
        mConfigBtn = findViewById(R.id.btn_config)
        mPreVerifyBtn = findViewById(R.id.btn_certifyid)
        mStartDetectBtn = findViewById(R.id.btn_start_detected)
        mStartVerifyBtn = findViewById(R.id.btn_start_verify)
    }

    private fun setListener() {
        mConfigBtn?.setOnClickListener {
            startActivity(
                Intent(this, SettingActivity::class.java)
            )
        }
        mPreVerifyBtn?.setOnClickListener {
            mLogInfo!!.clearComposingText()
            //FaceVerification.preFaceVerify()
            Toast.makeText(this, "预加载完成", Toast.LENGTH_LONG).show()
        }
        mStartDetectBtn?.setOnClickListener {
            mLogInfo?.clearComposingText()
            FaceVerification.faceVerify { response: VerifyResponse ->
                try {
                    mLogInfo?.setText(response.toString())
                    mCertifyId = response.certifyId
                    Toast.makeText(this, response.msg, Toast.LENGTH_LONG).show()
                    Log.e(TAG, "faceVerify==$response")
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        mStartVerifyBtn?.setOnClickListener {
            mLogInfo?.clearComposingText()
            if (AppStringUtils.isNotEmpty(mCertifyId)) {
                val call: Call<ResponseBody?>? = RequestParams().queryFaceVerify(mCertifyId!!)
                call?.enqueue(object : Callback<ResponseBody?> {
                    override fun onResponse(
                        call: Call<ResponseBody?>,
                        response: Response<ResponseBody?>
                    ) {
                        try {
                            if (response.body() != null) {
                                val responseData = String(response.body()!!.bytes())
                                mLogInfo?.setText(responseData)
                                if (AppStringUtils.isNotEmpty(responseData)) {
                                    val jsonData = JSONObject(responseData)
                                    val code: String = jsonData.optString("code")
                                    if ("200000" == code) {
                                        val data: JSONObject? = jsonData.optJSONObject("data")
                                        if (null != data) {
                                            val passed: String = data.optString("passed")
                                            if ("T" == passed) {
                                                Toast.makeText(
                                                    this@MainActivity,
                                                    "校验通过",
                                                    Toast.LENGTH_LONG
                                                ).show()
                                            } else {
                                                Toast.makeText(
                                                    this@MainActivity,
                                                    "校验失败",
                                                    Toast.LENGTH_LONG
                                                ).show()
                                            }
                                        } else {
                                            Toast.makeText(
                                                this@MainActivity,
                                                "data is null",
                                                Toast.LENGTH_LONG
                                            ).show()
                                        }
                                    } else {
                                        val message: String = jsonData.optString("message")
                                        Toast.makeText(
                                            this@MainActivity,
                                            message,
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                }
                                Log.e(TAG, responseData)
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                            Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                        Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_LONG).show()
                    }
                })
            } else {
                Toast.makeText(this@MainActivity, "CertifyId为空，请先获取CertifyId", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    companion object {
        const val TAG = "WG-->"
    }
}