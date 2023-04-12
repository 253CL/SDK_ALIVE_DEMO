package com.chuanglan.sdk.face.demo.activity

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.chuanglan.sdk.face.api.FaceVerification
import com.chuanglan.sdk.face.api.VerifyConfig
import com.chuanglan.sdk.face.demo.R
import com.chuanglan.sdk.face.demo.utils.AppStringUtils

/**
 * @Author:wanggang
 * @CreateDate: 2020/4/27 16:29
 */
class SettingActivity : AppCompatActivity() {
    var mScreenOrientation: ToggleButton? = null
    var mUsevedio: ToggleButton? = null
    var mUsePic: ToggleButton? = null
    var mSingleAction: ToggleButton? = null
    var mProgressColor: EditText? = null
    var mTimeout: EditText? = null
    var mConfigSave: TextView? = null
    var mConfigBack: RelativeLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_activity_setting_demo)
        initView()
        setListener()
    }

    private fun setListener() {
        mConfigSave?.setOnClickListener {
            FaceVerification.setVerifyConfig(
                VerifyConfig.Builder()
                    .setScreenOrientation(if (mScreenOrientation!!.isChecked()) VerifyConfig.SCREEN_ORIENTATION_LAND else VerifyConfig.SCREEN_ORIENTATION_PORT)
                    .setUseVideo(mUsevedio!!.isChecked())
                    .setUseBitmap(mUsePic!!.isChecked())
                    .setFaceProgressColor(
                        if (AppStringUtils.isNotEmpty(
                                mProgressColor?.text.toString()
                            )
                        ) mProgressColor?.text.toString() else "#303030"
                    )
                    .setActionModel(if (mSingleAction!!.isChecked()) VerifyConfig.ACTION_MODEL_LIVENESS else VerifyConfig.ACTION_MODEL_MULTI_ACTION)
                    .setVerifyTimeout(
                        if (AppStringUtils.isNotEmpty(
                                mTimeout?.text.toString()
                            )
                        ) mTimeout?.text.toString().toInt() else 6
                    )
                    .build()
            )
            Toast.makeText(this@SettingActivity, "配置成功", Toast.LENGTH_LONG).show()
            finish()
        }
        mConfigBack?.setOnClickListener { finish() }
    }

    private fun initView() {
        mScreenOrientation = findViewById(R.id.screenOrientation_et)
        mUsevedio = findViewById(R.id.use_vedio_et)
        mUsePic = findViewById(R.id.use_pic_et)
        mSingleAction = findViewById(R.id.single_action_et)
        mProgressColor = findViewById(R.id.progress_color)
        mTimeout = findViewById(R.id.time_out_et)
        mConfigSave = findViewById(R.id.config_save)
        mConfigBack = findViewById(R.id.config_back)
    }
}