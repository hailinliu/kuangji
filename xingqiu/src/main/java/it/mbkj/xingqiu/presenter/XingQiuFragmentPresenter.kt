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
import it.mbkj.tradecenter.fragment.XingQiuFragment
import it.mbkj.xingqiu.bean.JiJiaBean


class XingQiuFragmentPresenter :BasePresenter<XingQiuFragment>(){
    fun getList(session_id:String){
        OkGo.post<JiJiaBean>(HttpConfig.BASE_URL+"/v1.machine/machine_list")
            .params("session_id",session_id)
            .execute(object : CallBackOption<JiJiaBean>() {}.loadBind(mView as XingQiuFragment).execute(
                object : ILoadBind<JiJiaBean> {
                    override fun excute(bean: JiJiaBean) {
                       /* if(bean.error_code==20000){
                            ARouter.getInstance().build(ArouterAddress.LOGINACTIVITY).navigation()
                        }else if(bean.error_code==1001){
                            ToastUtil.showShort(bean.msg)
                        }*/
                        if(bean.code==200){
                            (mView as XingQiuFragment).setUI(bean.data.machine_list.data)
                        }
                    }
                }
            ))
    }
    fun buyJiJia(session_id:String,id:Int,pay_pwd:String){
        OkGo.post<BaseBean<String>>(HttpConfig.BASE_URL+"/v1.machine/buy_machine")
            .params("session_id",session_id)
            .params("id",id)
            .params("pay_pwd",pay_pwd)
            .execute(object : CallBackOption<BaseBean<String>>() {}.loadBind(mView as XingQiuFragment).execute(
                object : ILoadBind<BaseBean<String>> {
                    override fun excute(bean: BaseBean<String>) {
                       /* if(bean.error_code==20000){
                            ARouter.getInstance().build(ArouterAddress.LOGINACTIVITY).navigation()
                        }else if(bean.error_code==1001){
                            ToastUtil.showShort(bean.msg)
                        }
                        else */if(bean.code==200){
                           ToastUtil.showShort(bean.msg)
                        }
                    }
                }
            ))
    }
}