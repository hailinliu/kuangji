package it.mbkj.lib.base

import android.app.Activity
import it.mbkj.lib.http.HttpService

open class BasePresenter<VIEW : IBaseView> : IPresenter<VIEW>() {
    @JvmField
    var httpService: HttpService? = null
    override fun attachView(view: Any?, context: Activity?) {
        super.attachView(view, context)
        httpService = HttpService()
    }
}