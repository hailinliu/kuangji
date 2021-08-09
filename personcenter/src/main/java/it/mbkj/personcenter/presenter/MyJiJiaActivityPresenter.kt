package it.mbkj.personcenter.presenter

import com.lzy.okgo.OkGo
import it.mbkj.lib.base.BaseBean
import it.mbkj.lib.base.BasePresenter
import it.mbkj.lib.base.ILoadBind
import it.mbkj.lib.http.CallBackOption
import it.mbkj.lib.http.HttpConfig
import it.mbkj.personcenter.activity.MyJiJiaActivity
import it.mbkj.personcenter.bean.JiJiaListBean


class MyJiJiaActivityPresenter:BasePresenter<MyJiJiaActivity>() {
    fun getJijJiaList(session_id:String){
        OkGo.post<BaseBean<JiJiaListBean>>(HttpConfig.BASE_URL+"/v1.machine/machine_order_list")
            .params("session_id",session_id)
            .execute(object : CallBackOption<BaseBean<JiJiaListBean>>() {}.loadBind(mView as MyJiJiaActivity).execute(
                object : ILoadBind<BaseBean<JiJiaListBean>> {
                    override fun excute(bean: BaseBean<JiJiaListBean>) {
                        /* if(bean.error_code==20000){
                             ARouter.getInstance().build(ArouterAddress.LOGINACTIVITY).navigation()
                         }else if(bean.error_code==1001){
                             ToastUtil.showShort(bean.msg)
                         }*/
                        if(bean.code==200){
                            (mView as MyJiJiaActivity).setUI(bean.data.on_machine_list.data)
                        }
                    }
                }
            ))
    }
}