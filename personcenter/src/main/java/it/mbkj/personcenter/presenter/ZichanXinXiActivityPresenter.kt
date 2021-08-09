package it.mbkj.personcenter.presenter

import com.alibaba.android.arouter.launcher.ARouter
import com.lzy.okgo.OkGo
import com.lzy.okgo.callback.StringCallback
import com.lzy.okgo.model.Response
import com.sskj.common.util.GSonUtil
import com.sskj.common.util.ToastUtil
import it.mbkj.lib.base.ArouterAddress
import it.mbkj.lib.base.BaseBean
import it.mbkj.lib.base.BasePresenter
import it.mbkj.lib.base.ILoadBind
import it.mbkj.lib.http.CallBackOption
import it.mbkj.lib.http.HttpConfig
import it.mbkj.personcenter.activity.ZichanXinXiActivity
import it.mbkj.personcenter.bean.ZiChanBean


class ZichanXinXiActivityPresenter:BasePresenter<ZichanXinXiActivity>() {
    fun getZiChan(session_id:String){
        /*OkGo.post<BaseBean<ZiChanBean>>(HttpConfig.BASE_URL+"/v1.my/my_money")
            .params("session_id",session_id)
            .execute(object : CallBackOption<BaseBean<ZiChanBean>>() {}.loadBind(mView as ZichanXinXiActivity).execute(
                object : ILoadBind<BaseBean<ZiChanBean>> {
                    override fun excute(bean: BaseBean<ZiChanBean>) {
                        *//* if(bean.error_code==20000){
                             ARouter.getInstance().build(ArouterAddress.LOGINACTIVITY).navigation()
                         }else if(bean.error_code==1001){
                             ToastUtil.showShort(bean.msg)
                         }*//*
                        if(bean.code==200){
                            (mView as ZichanXinXiActivity).setUI(bean.data)
                        }
                    }
                }
            ))*/
        OkGo.post<String>(HttpConfig.BASE_URL+"/v1.my/my_money").params("session_id",session_id)
            .execute(object :StringCallback(){
                override fun onSuccess(response: Response<String>?) {
                    var bean=  GSonUtil.GsonToBean(response!!.body(),ZiChanBean::class.java)
                    if(bean.code==200){
                        (mView as ZichanXinXiActivity).setUI(bean)
                    }else if(bean.error_code==20000){
                        ARouter.getInstance().build(ArouterAddress.LOGINACTIVITY).navigation()
                    }else{
                        ToastUtil.showShort(bean.msg)
                    }
                }
            })
    }
}