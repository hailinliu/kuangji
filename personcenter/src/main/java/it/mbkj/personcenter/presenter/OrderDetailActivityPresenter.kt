package it.mbkj.personcenter.presenter

import com.lzy.okgo.OkGo
import com.sskj.common.util.ToastUtil
import it.mbkj.lib.base.BaseBean
import it.mbkj.lib.base.BasePresenter
import it.mbkj.lib.base.ILoadBind
import it.mbkj.lib.http.CallBackOption
import it.mbkj.lib.http.HttpConfig
import it.mbkj.personcenter.activity.OrderDetailActivity
import it.mbkj.personcenter.bean.OrderDetailBean

class OrderDetailActivityPresenter:BasePresenter<OrderDetailActivity>() {
    fun getOrderDetail(session_id:String,id:String){
        OkGo.post<BaseBean<OrderDetailBean>>(HttpConfig.BASE_URL+"/v1.pledge/pledge_order_msg")
            .params("session_id",session_id)
            .params("id",id)
            .execute(object : CallBackOption<BaseBean<OrderDetailBean>>() {}.loadBind(mView as OrderDetailActivity).execute(
                object : ILoadBind<BaseBean<OrderDetailBean>> {
                    override fun excute(bean: BaseBean<OrderDetailBean>) {
                        /* if(bean.error_code==20000){
                             ARouter.getInstance().build(ArouterAddress.LOGINACTIVITY).navigation()
                         }else if(bean.error_code==1001){
                             ToastUtil.showShort(bean.msg)
                         }*/
                        if(bean.code==200){
                            (mView as OrderDetailActivity).setUI(bean.data.order_msg)
                        }
                    }
                }
            ))
        
    }
    fun cancel(session_id:String,id:String){
        OkGo.post<BaseBean<String>>(HttpConfig.BASE_URL+"/v1.pledge/out_pledge")
            .params("session_id",session_id)
            .params("id",id)
            .execute(object : CallBackOption<BaseBean<String>>() {}.loadBind(mView as OrderDetailActivity).execute(
                object : ILoadBind<BaseBean<String>> {
                    override fun excute(bean: BaseBean<String>) {
                        /* if(bean.error_code==20000){
                             ARouter.getInstance().build(ArouterAddress.LOGINACTIVITY).navigation()
                         }else if(bean.error_code==1001){
                             ToastUtil.showShort(bean.msg)
                         }*/
                        if(bean.code==200){
                            ToastUtil.showShort(bean.msg)
                            (mView as OrderDetailActivity).setData()
                        }
                    }
                }
            ))
    }
    fun lingqu(session_id:String,id:String){
        OkGo.post<BaseBean<String>>(HttpConfig.BASE_URL+"/v1.pledge/pledge_income")
            .params("session_id",session_id)
            .params("id",id)
            .execute(object : CallBackOption<BaseBean<String>>() {}.loadBind(mView as OrderDetailActivity).execute(
                object : ILoadBind<BaseBean<String>> {
                    override fun excute(bean: BaseBean<String>) {
                        /* if(bean.error_code==20000){
                             ARouter.getInstance().build(ArouterAddress.LOGINACTIVITY).navigation()
                         }else if(bean.error_code==1001){
                             ToastUtil.showShort(bean.msg)
                         }*/
                        if(bean.code==200){

                            ToastUtil.showShort(bean.msg)
                              (mView as OrderDetailActivity).setpp()
                        }
                    }
                }
            ))
    }
}