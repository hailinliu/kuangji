package it.mbkj.personcenter.presenter

import com.lzy.okgo.OkGo
import com.sskj.common.util.ToastUtil
import it.mbkj.lib.base.BaseBean
import it.mbkj.lib.base.BasePresenter
import it.mbkj.lib.base.ILoadBind
import it.mbkj.lib.http.CallBackOption
import it.mbkj.lib.http.HttpConfig
import it.mbkj.personcenter.activity.MyOrderActivity
import it.mbkj.personcenter.bean.OrderItemBean


class MyOrderActivityPresenter:BasePresenter<MyOrderActivity>() {
    fun getOrderList(session_id:String){
        OkGo.post<BaseBean<OrderItemBean>>(HttpConfig.BASE_URL+"/v1.pledge/pledge_order_list")
            .params("session_id",session_id)
            .execute(object : CallBackOption<BaseBean<OrderItemBean>>() {}.loadBind(mView as MyOrderActivity).execute(
                object : ILoadBind<BaseBean<OrderItemBean>> {
                    override fun excute(bean: BaseBean<OrderItemBean>) {
                        /* if(bean.error_code==20000){
                             ARouter.getInstance().build(ArouterAddress.LOGINACTIVITY).navigation()
                         }else if(bean.error_code==1001){
                             ToastUtil.showShort(bean.msg)
                         }*/
                        if(bean.code==200){
                            (mView as MyOrderActivity).setUI(bean.data.pledge_order_list)
                        }
                    }
                }
            ))
    }

}
