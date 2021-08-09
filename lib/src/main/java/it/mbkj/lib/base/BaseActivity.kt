package it.mbkj.lib.base

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
/*import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding*/
import com.gyf.barlibrary.ImmersionBar
import com.sskj.common.base.App
import com.sskj.common.rxbus2.RxBus
import com.sskj.common.util.ClickUtil
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity

import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.DisposableSubscriber
import it.mbkj.lib.R
import it.mbkj.lib.utils.MyProgressDialogs
import java.util.concurrent.TimeUnit

abstract class BaseActivity<T : IPresenter<*>?> : RxAppCompatActivity(), IBaseViewLife {
    var orientation = true
    var mPresenter: T? = null
    var mActivity: BaseActivity<*>? = null

    private var mProgressDialogs: MyProgressDialogs? = null
    private var disposableSubscriber: DisposableSubscriber<Long>? = null
    private var mCompositeDisposable: CompositeDisposable? = null
     var mActivityIsActive = false
    private var mRequestCount = 0
    private var dataBinding: ViewDataBinding?=null
    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (orientation) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
        dealFirstSaveInstance(savedInstanceState)
        mProgressDialogs = MyProgressDialogs(this)
        checkDeviceHasNavigationBar(this)
        if (immersion) {
            ImmersionBar.with(this).navigationBarAlpha(0f).init()
            //  setContentView(R.layout.lib_activity);
        } else {
            ImmersionBar.with(this).navigationBarAlpha(0f).init()
            //   setContentView(R.layout.lib_immer_activity);
        }
       dataBinding = DataBindingUtil.setContentView(this, layoutId)
        /* ViewGroup viewRoot = findViewById(R.id.flImmerRoot);
        viewRoot.addView(getLayoutInflater().inflate(getLayoutId(), null, false));*/mActivity = this
        mPresenter = presenter
        mPresenter!!.attachView(this, this)
        //  ButterKnife.bind(this);
        initView()
        initData()
        initEvent()
        val back = findViewById<View>(R.id.ivBack)
        if (back != null) {
            ClickUtil.click(back) { onBackPressed() }
        }
    }
    protected open fun <T : ViewDataBinding?> getViewDataBinding(): T {
        return dataBinding as T
    }
    open fun dealFirstSaveInstance(savedInstanceState: Bundle?) {}
    open fun initEvent() {}
    open  fun initData() {}
    open fun initView() {}
    val immersion: Boolean
        get() = true

    /**
     * 初始化布局ID
     *
     * @return
     */
    protected abstract val layoutId: Int

    /**
     * 初始化当前View 的 presenter
     *
     * @return
     */
    abstract val presenter: T

    /**
     * 设置标题
     *
     * @param title
     */
    fun setTitle(title: String?) {
        if (TextUtils.isEmpty(title)) {
            return
        }
        val tvTitle = findViewById<TextView>(R.id.tvTitle)
        if (tvTitle != null) {
            tvTitle.text = title
        }
    }

    /**
     * 设置标题
     *
     * @param title
     */
    fun setRightTitle(title: String?, onClickListener: View.OnClickListener?) {
        if (TextUtils.isEmpty(title)) {
            return
        }
        val tvTitle = findViewById<TextView>(R.id.right_tv)
        if (tvTitle != null) {
            tvTitle.visibility = View.VISIBLE
            tvTitle.text = title
            tvTitle.setOnClickListener(onClickListener)
        }
    }

    override fun onDestroy() {
        mProgressDialogs!!.closeDialog()
        super.onDestroy()
        if (disposableSubscriber != null) {
            disposableSubscriber!!.dispose()
        }
        mPresenter!!.cancelRequest()
        mPresenter!!.detachView()
        RxBus.getDefault().unregister(this)
        ImmersionBar.with(this).destroy()
        clearDisposable()
    }

    /**
     * 取消所有订阅
     */
    fun clearDisposable() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable!!.clear()
        }
    }

    /**
     * 添加订阅
     */
    fun addDisposable(mDisposable: Disposable?) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = CompositeDisposable()
        }
        mCompositeDisposable!!.add(mDisposable!!)
    }

    override fun showLoading() {
        if (mActivityIsActive && !isFinishing) {
            if (mProgressDialogs != null && mRequestCount <= 0) {
                mRequestCount = 1
                mProgressDialogs!!.showDialog()
            } else {
                ++mRequestCount
            }
        }
    }

    override fun onResume() {
        super.onResume()
        mActivityIsActive = true
    }

    override fun onPause() {
        super.onPause()
        mActivityIsActive = true
    }
    override fun hideLoading() {
        if (!isFinishing) {
            if (mRequestCount <= 1) {
                mRequestCount = 0
                mProgressDialogs!!.closeDialog()
            } else {
                --mRequestCount
            }
        }
    }

    override fun isEmpty(textView: TextView?): Boolean {
        return textView == null || TextUtils.isEmpty(textView.text)
    }

    /**
     * 60s倒计时
     *
     * @param getCodeView
     */
    fun startTimeDown(getCodeView: TextView?) {
        getCodeView!!.isEnabled = false
        disposableSubscriber = object: DisposableSubscriber<Long>() {
            override fun onNext(aLong: Long) {
                val time = (60 - aLong).toInt()
                if (getCodeView != null) {
                    getCodeView.text =
                time.toString() + App.INSTANCE.getString(R.string.lib_baseActivity2)
                }
            }

            override fun onError(aLong: Throwable?) {
                println(aLong.toString())
            }

            override fun onComplete() {
                if (getCodeView != null) {
                    getCodeView.text = App.INSTANCE.getString(R.string.lib_baseActivity3)
                    getCodeView.isEnabled = true
                }
                if (!disposableSubscriber!!.isDisposed) {
                    disposableSubscriber!!.dispose()
                    disposableSubscriber = null
                }
            }

        }
                /*DisposableSubscriber<Long>() {
            override fun onNext(aLong: Long) {
                val time = (60 - aLong).toInt()
                if (getCodeView != null) {
                    getCodeView.text =
                        time.toString() + App.INSTANCE.getString(R.string.lib_baseActivity2)
                }
            }

            override fun onError(t: Throwable) {
                println(t.toString())
            }

            override fun onComplete() {
                if (getCodeView != null) {
                    getCodeView.text = App.INSTANCE.getString(R.string.lib_baseActivity3)
                    getCodeView.isEnabled = true
                }
                if (!disposableSubscriber!!.isDisposed) {
                    disposableSubscriber!!.dispose()
                    disposableSubscriber = null
                }
            }
        }*/
        Flowable.interval(0, 1, TimeUnit.SECONDS, Schedulers.newThread())
            .take(60)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(disposableSubscriber)
    }

    override fun color(id: Int): Int {
        return ContextCompat.getColor(this, id)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        // 如果你的app可以横竖屏切换，并且适配4.4或者emui3手机请务必在onConfigurationChanged方法里添加这句话
        ImmersionBar.with(this).init()
    }

    override fun drawable(id: Int): Drawable? {
        return ContextCompat.getDrawable(this, id)
    }

    override fun getText(textView: TextView?): String? {
        return textView!!.text.toString()
    }

    override fun setText(textView: TextView?, text: String?) {
        if (textView != null) {
            textView.text = text
        }
        if (textView is EditText) {
            textView.setSelection(textView.text.length)
        }
    }

    companion object {
        private const val TAG = "BaseActivity"
        fun checkDeviceHasNavigationBar(context: Context): Boolean {
            var hasNavigationBar = false
            val rs = context.resources
            val id = rs.getIdentifier("config_showNavigationBar", "bool", "android")
            if (id > 0) {
                hasNavigationBar = rs.getBoolean(id)
            }
            try {
                val systemPropertiesClass = Class.forName("android.os.SystemProperties")
                val m = systemPropertiesClass.getMethod("get", String::class.java)
                val navBarOverride = m.invoke(systemPropertiesClass, "qemu.hw.mainkeys") as String
                if ("1" == navBarOverride) {
                    hasNavigationBar = false
                } else if ("0" == navBarOverride) {
                    hasNavigationBar = true
                }
            } catch (e: Exception) {
            }
            return hasNavigationBar
        }
    }
}