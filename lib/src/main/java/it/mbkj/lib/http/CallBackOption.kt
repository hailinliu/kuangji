package it.mbkj.lib.http

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.launcher.ARouter
import com.google.gson.JsonSyntaxException
import com.lzy.okgo.callback.AbsCallback
import com.lzy.okgo.model.Response
import com.lzy.okgo.request.base.Request
import com.sskj.common.util.GSonUtil
import com.sskj.common.util.SPUtil
import com.sskj.common.util.ToastUtil
import it.mbkj.lib.base.*
import it.mbkj.lib.exception.JsonParseException
import org.json.JSONObject
import java.lang.reflect.ParameterizedType

abstract class CallBackOption<T> : AbsCallback<T?>() {
    internal enum class Strategy {
        LOAD_BIND,  //显示加载框和界面生命周期绑定
        UNLOAD_BIND,  //不显示加载框和界面生命周期绑定
        UNLOAD_UNBIND //不显示加载框不和界面生命周期绑定
    }

    private var iLoadBind: ILoadBind<T>? = null
    fun execute(t: ILoadBind<T>?): CallBackOption<T> {
        iLoadBind = t
        return this
    }

    private var iBaseViewLife: IBaseViewLife? = null
    private var lifecycleOwner: LifecycleOwner? = null
    private var strategy: Strategy? = null
    private var tMutableLiveData: MutableLiveData<T>? = null

    /**
     * 显示加载框和界面生命周期绑定
     *
     * @param iBaseViewLife
     * @return
     */
    fun loadBind(iBaseViewLife: IBaseViewLife?): CallBackOption<T> {
        this.iBaseViewLife = iBaseViewLife
        strategy = Strategy.LOAD_BIND
        return this
    }

    /**
     * 不显示加载框和界面生命周期绑定
     *
     * @return
     */
    fun unLoadBind(lifecycleOwner: LifecycleOwner?): CallBackOption<T> {
        this.lifecycleOwner = lifecycleOwner
        strategy = Strategy.UNLOAD_BIND
        return this
    }

    /**
     * 不显示加载框不和界面生命周期绑定
     *
     * @return
     */
    fun unLoadunBind(): CallBackOption<T> {
        strategy = Strategy.UNLOAD_UNBIND
        return this
    }

    override fun onStart(request: Request<T?, out Request<*, *>?>?) {
        super.onStart(request)
        if (strategy == Strategy.LOAD_BIND) {
            tMutableLiveData = MutableLiveData()
            tMutableLiveData!!.observe(iBaseViewLife!!, Observer { t: T? -> iLoadBind!!.excute(t!!) })
            iBaseViewLife!!.showLoading()
        } else if (strategy == Strategy.UNLOAD_BIND) {
            tMutableLiveData = MutableLiveData()
            tMutableLiveData!!.observe(lifecycleOwner!!, Observer { t: T? -> iLoadBind!!.excute(t!!) })
        }
    }

    override fun onSuccess(response: Response<T?>) {
        if (strategy == Strategy.UNLOAD_UNBIND) {
            iLoadBind!!.excute(response.body()!!)
        } else {
            tMutableLiveData!!.postValue(response.body())
        }
    }

    override fun onFinish() {
        super.onFinish()
        if (strategy == Strategy.LOAD_BIND) {
            iBaseViewLife!!.hideLoading()
        }
    }

    @Throws(Exception::class)
    override fun convertResponse(response: okhttp3.Response): T? {
        val body = response.body()
        val data: T
        if (body == null) return null
        var jsonBody = body.string()
        jsonBody = jsonBody.replace("\"data\":\"\"}", "\"data\":null}")
        if (!jsonBody.isEmpty()) {
            val jsonObject = JSONObject(jsonBody)
            val code = if (jsonObject.isNull("code")) 0 else jsonObject.getInt("code")
            val error_code =
                if (jsonObject.isNull("error_code")) 0 else jsonObject.getInt("error_code")
            val msg = if (jsonObject.isNull("msg")) "" else jsonObject.getString("msg")
            if (code != 200 && error_code != 20000) {
                ToastUtil.showShort(msg)
                // return null;
            } else if (error_code == 20000) {
                SPUtil.put(SPConfig.SESSION_ID,"")
                AppManager.getSingleton().finishAllActivity("MainActivity")
                ARouter.getInstance().build(ArouterAddress.LOGINACTIVITY).navigation()
                 // return null;
            }
                data = try {
                    val gson = GSonUtil.gson
                    val genericSuperclass = javaClass.genericSuperclass
                    val type = (genericSuperclass as ParameterizedType).actualTypeArguments[0]
                    gson.fromJson(jsonBody, type)
                } catch (e: JsonSyntaxException) {
                    throw JsonParseException()
                }
                return data

        }
        return null
    }

    override fun onError(response: Response<T?>) {
        super.onError(response)
        if (strategy == Strategy.LOAD_BIND) {
            iBaseViewLife!!.hideLoading()
        }
        ExceptionUtil.dealException(response.exception)
    }
}