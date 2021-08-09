package it.mbkj.lib

import android.app.Activity
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.sskj.common.util.ImgUtil
import com.sskj.common.util.RxSchedulersHelper
import com.sskj.common.util.SPUtil
import io.reactivex.Flowable
import io.reactivex.disposables.Disposable
import it.mbkj.lib.base.ArouterAddress
import it.mbkj.lib.http.SPConfig
import it.mbkj.lib.utils.ImageUtil
import kotlinx.android.synthetic.main.activity_lib.*
import java.util.concurrent.TimeUnit

@Route(path = ArouterAddress.LibAcitivity)
class LibAcitivity: Activity() {
    private var disposable: Disposable? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        NavigationBarStatusBar(this, true)
        setContentView(R.layout.activity_lib)
        ImageUtil.setOriginImage(R.mipmap.lib_qidong,image)
      disposable =
            Flowable.timer(3000, TimeUnit.MILLISECONDS).compose(RxSchedulersHelper.transformer())
                .subscribe { aLong: Long? ->
                    var token:String  = SPUtil.get(SPConfig.SESSION_ID, "")
                    if(TextUtils.isEmpty(token)){
                        ARouter.getInstance().build(ArouterAddress.LOGINACTIVITY).navigation()
                    }else{
                        ARouter.getInstance().build(ArouterAddress.MAINACTIVITY).withString("sessionId", token).navigation()
                    }
                    finish()
                }

    }

    /**
     * 导航栏，状态栏隐藏
     * @param activity
     */
    fun NavigationBarStatusBar(activity: Activity, hasFocus: Boolean) {
        if (hasFocus && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val decorView = activity.window.decorView
            decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        }
    }

}