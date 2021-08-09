package it.mbkj.tradecenter.presenter

import com.lzy.okgo.OkGo
import com.sskj.common.util.ToastUtil
import it.mbkj.lib.base.BaseBean
import it.mbkj.lib.base.BasePresenter
import it.mbkj.lib.base.ILoadBind
import it.mbkj.lib.http.CallBackOption
import it.mbkj.lib.http.HttpConfig
import it.mbkj.tradecenter.activity.BuyBaoActivity
import it.mbkj.tradecenter.bean.GetOrderBean
import it.mbkj.tradecenter.bean.HomePageBean
import it.mbkj.tradecenter.bean.QiuGouListBean
import it.mbkj.tradecenter.fragment.TradCenterFragment

class BuyBaoActivityPresenter:BasePresenter<BuyBaoActivity>() {
    fun getBuy(session_id:String,sum:String,pay_pwd:String){
        OkGo.post<BaseBean<String>>(HttpConfig.BASE_URL+"/v1.deal/want_buy")
            .params("session_id",session_id)
            .params("sum",sum)
            .params("pay_pwd",pay_pwd)
            .execute(object : CallBackOption<BaseBean<String>>() {}.loadBind(mView as BuyBaoActivity).execute(
                object : ILoadBind<BaseBean<String>> {
                    override fun excute(bean: BaseBean<String>) {
                        if(bean.code==200){
                            ToastUtil.showShort(bean.msg)
                            (mView as BuyBaoActivity).setp()
                        }
                    }
                }
            ))
    }
    fun getList(session_id:String,page:Int){
        OkGo.post<BaseBean<GetOrderBean>>(HttpConfig.BASE_URL+"/v1.deal/self_want_buy_list")
            .params("session_id",session_id)
            .params("page",page)
            .execute(object : CallBackOption<BaseBean<GetOrderBean>>() {}.loadBind(mView as BuyBaoActivity).execute(
                object : ILoadBind<BaseBean<GetOrderBean>> {
                    override fun excute(bean: BaseBean<GetOrderBean>) {
                        if(bean.code==200){
                            (mView as BuyBaoActivity).setUI(bean.data.buy_list)
                        }
                    }
                }
            )
            )
    }
    fun cancelGuaDan(session_id:String,id:String){
        OkGo.post<BaseBean<String>>(HttpConfig.BASE_URL+"/v1.deal/down_deal_list")
            .params("session_id",session_id)
            .params("id",id)
            .execute(object : CallBackOption<BaseBean<String>>() {}.loadBind(mView as BuyBaoActivity).execute(
                object : ILoadBind<BaseBean<String>> {
                    override fun excute(bean: BaseBean<String>) {
                        if(bean.code==200){
                            (mView as BuyBaoActivity).setdata()
                            ToastUtil.showShort(bean.msg)
                        }
                    }
                }
            ))
    }
    fun getHomePage(session_id:String){
        OkGo.post<BaseBean<HomePageBean>>(HttpConfig.BASE_URL+"/v1.index/index")
            .params("session_id",session_id)
            .execute(object : CallBackOption<BaseBean<HomePageBean>>() {}.loadBind(mView as BuyBaoActivity).execute(
                object :ILoadBind<BaseBean<HomePageBean>>{
                    override fun excute(bean: BaseBean<HomePageBean>) {
                        /* if(bean.error_code==20000){
                             ARouter.getInstance().build(ArouterAddress.LOGINACTIVITY).navigation()
                         }else*/ if(bean.code==200){
                            (mView as BuyBaoActivity).setData(bean.data.user_msg)
                        }
                    }
                }
            ))
    }
}