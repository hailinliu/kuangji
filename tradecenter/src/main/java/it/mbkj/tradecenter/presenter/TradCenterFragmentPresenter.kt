package it.mbkj.tradecenter.presenter

import com.alibaba.android.arouter.launcher.ARouter
import com.lzy.okgo.OkGo
import com.sskj.common.util.ToastUtil
import it.mbkj.lib.base.ArouterAddress
import it.mbkj.lib.base.BaseBean
import it.mbkj.lib.base.BasePresenter
import it.mbkj.lib.base.ILoadBind
import it.mbkj.lib.http.CallBackOption
import it.mbkj.lib.http.HttpConfig
import it.mbkj.tradecenter.bean.HomePageBean
import it.mbkj.tradecenter.bean.KChartBean
import it.mbkj.tradecenter.bean.QiuGouListBean
import it.mbkj.tradecenter.fragment.TradCenterFragment

class TradCenterFragmentPresenter :BasePresenter<TradCenterFragment>(){
    fun getList(session_id:String,page:Int,phone:String){
    OkGo.post<BaseBean<QiuGouListBean>>(HttpConfig.BASE_URL+"/v1.deal/want_buy_list")
        .params("session_id",session_id)
        .params("page",page)
        .params("phone",phone)
        .execute(object : CallBackOption<BaseBean<QiuGouListBean>>() {}.loadBind(mView as TradCenterFragment).execute(
                object : ILoadBind<BaseBean<QiuGouListBean>> {
                    override fun excute(bean: BaseBean<QiuGouListBean>) {
                        if(bean.code==200){
                            (mView as TradCenterFragment).setUI(bean.data.buy_list)
                        }
                    }
                }
            )
        )
    }
    fun sell(session_id:String,id:Int,sum:String,pay_pwd:String){
        OkGo.post<BaseBean<String>>(HttpConfig.BASE_URL+"/v1.deal/get_buy_order")
            .params("session_id",session_id)
            .params("id",id)
            .params("sum",sum)
            .params("pay_pwd",pay_pwd)
            .execute(object : CallBackOption<BaseBean<String>>() {}.loadBind(mView as TradCenterFragment).execute(
                object : ILoadBind<BaseBean<String>> {
                    override fun excute(bean: BaseBean<String>) {
                        if(bean.code==200){
                           ToastUtil.showShort(bean.msg)
                            (mView as TradCenterFragment).setData()
                        }
                    }
                }
            )
            )
    }
    fun getK(session_id:String){
        OkGo.post<KChartBean>(HttpConfig.BASE_URL+"/v1.deal/index")
            .params("session_id",session_id)
            .execute(object : CallBackOption<KChartBean>() {}.loadBind(mView as TradCenterFragment).execute(
                object : ILoadBind<KChartBean> {
                    override fun excute(bean: KChartBean) {
                        if(bean.code==200){
                            (mView as TradCenterFragment).setK(bean)
                        }
                    }
                }
            )
            )
    }
    fun getHomePage(session_id:String){
        OkGo.post<BaseBean<HomePageBean>>(HttpConfig.BASE_URL+"/v1.index/index")
            .params("session_id",session_id)
            .execute(object : CallBackOption<BaseBean<HomePageBean>>() {}.loadBind(mView as TradCenterFragment).execute(
                object :ILoadBind<BaseBean<HomePageBean>>{
                    override fun excute(bean: BaseBean<HomePageBean>) {
                        if(bean.error_code==20000){
                            ARouter.getInstance().build(ArouterAddress.LOGINACTIVITY).navigation()
                        }else if(bean.code==200){
                            (mView as TradCenterFragment).setData1(bean.data)
                        }
                    }
                }
            ))
    }
}