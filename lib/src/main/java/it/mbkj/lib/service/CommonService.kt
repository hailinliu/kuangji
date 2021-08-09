package it.mbkj.lib.service

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.facade.template.IProvider
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.lzy.okgo.OkGo
import com.lzy.okgo.cache.CacheEntity
import com.lzy.okgo.cache.CacheMode
import com.lzy.okgo.cookie.CookieJarImpl
import com.lzy.okgo.cookie.store.SPCookieStore
import com.lzy.okgo.https.HttpsUtils
import com.sskj.common.log.HttpLogger
import it.mbkj.lib.base.App
import it.mbkj.lib.base.HttpsUtil
import it.mbkj.lib.base.LogUtil
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

import java.util.concurrent.TimeUnit

@Route(path = "/lib/commonService")
class CommonService:IProvider {
    override fun init(context: Context?) {

    }
    fun initOkGoHttp(context: Context, isDebug: Boolean){
        LogUtil.init(isDebug)
        val cookieJar = CookieJarImpl(SPCookieStore(context))
        val builder = OkHttpClient.Builder()
        try {
            val sslParams1 = HttpsUtils.getSslSocketFactory()
            builder.sslSocketFactory(sslParams1.sSLSocketFactory, sslParams1.trustManager)
//            setSSL(builder);
        } catch (e: Exception) {
            e.printStackTrace()
        }
        builder.addNetworkInterceptor(StethoInterceptor())
            .cookieJar(cookieJar)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .hostnameVerifier(HttpsUtil.Companion.UnSafeHostnameVerifier()) //                .addInterceptor(new CookieInterceptor(cookieJar))


        val client = builder.build()
        OkGo.getInstance().init(App.INSTANCE) //必须调用初始化
            .setEncrypt(false)
           // .encryptStratage(EncodeUtils::encodeAES2B)
            .setOkHttpClient(client) //必须设置OkHttpClient
            .setCacheMode(CacheMode.NO_CACHE) //全局统一缓存模式，默认不使用缓存，可以不传
            .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE) //全局统一缓存时间，默认永不过期，可以不传
            .setRetryCount(0)
    }

}