package it.mbkj.personcenter.presenter

import com.lzy.okgo.OkGo
import com.sskj.common.util.ToastUtil
import it.mbkj.lib.base.BaseBean
import it.mbkj.lib.base.BasePresenter
import it.mbkj.lib.base.ILoadBind
import it.mbkj.lib.http.CallBackOption
import it.mbkj.lib.http.HttpConfig
import it.mbkj.personcenter.activity.PledgeHallActivity
import it.mbkj.personcenter.bean.PledgeListBean


class PledgeHallActivityPresenter:BasePresenter<PledgeHallActivity>() {
    fun getPledgeList(session_id:String){
        OkGo.post<BaseBean<PledgeListBean>>(HttpConfig.BASE_URL+"/v1.pledge/pledge_list")
            .params("session_id",session_id)
            .execute(object : CallBackOption<BaseBean<PledgeListBean>>() {}.loadBind(mView as PledgeHallActivity).execute(
                object : ILoadBind<BaseBean<PledgeListBean>> {
                    override fun excute(bean: BaseBean<PledgeListBean>) {
                        /* if(bean.error_code==20000){
                             ARouter.getInstance().build(ArouterAddress.LOGINACTIVITY).navigation()
                         }else if(bean.error_code==1001){
                             ToastUtil.showShort(bean.msg)
                         }*/
                        if(bean.code==200){
                            (mView as PledgeHallActivity).setUI(bean.data.pledge_list)
                        }
                    }
                }
            ))
    }
    fun submit(session_id:String,id:Int,pay_pwd:String){
        OkGo.post<BaseBean<String>>(HttpConfig.BASE_URL+"/v1.pledge/buy_pledge")
            .params("session_id",session_id)
            .params("id",id)
            .params("pay_pwd",pay_pwd)
            .execute(object : CallBackOption<BaseBean<String>>() {}.loadBind(mView as PledgeHallActivity).execute(
                object : ILoadBind<BaseBean<String>> {
                    override fun excute(bean: BaseBean<String>) {
                        /* if(bean.error_code==20000){
                             ARouter.getInstance().build(ArouterAddress.LOGINACTIVITY).navigation()
                         }else if(bean.error_code==1001){
                             ToastUtil.showShort(bean.msg)
                         }*/
                        if(bean.code==200){
                            ToastUtil.showShort(bean.msg)
                           // (mView as PledgeHallActivity).setUI(bean.data.pledge_list)
                        }
                    }
                }
            ))
    }
}