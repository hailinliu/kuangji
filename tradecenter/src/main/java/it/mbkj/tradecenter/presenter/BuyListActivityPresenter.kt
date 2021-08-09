package it.mbkj.tradecenter.presenter

import com.lzy.okgo.OkGo
import com.lzy.okgo.callback.StringCallback
import com.lzy.okgo.model.Response
import it.mbkj.lib.base.BaseBean
import it.mbkj.lib.base.BasePresenter
import it.mbkj.lib.base.ILoadBind
import it.mbkj.lib.http.CallBackOption
import it.mbkj.lib.http.HttpConfig
import it.mbkj.tradecenter.activity.BuyListActivity
import it.mbkj.tradecenter.bean.BuyListBean


class BuyListActivityPresenter:BasePresenter<BuyListActivity>() {
    fun getList(session_id:String,page:Int){
        OkGo.post<BaseBean<BuyListBean>>(HttpConfig.BASE_URL+"/v1.deal/buy_list")
            .params("session_id",session_id)
            .params("page",page)
            .execute(object : CallBackOption<BaseBean<BuyListBean>>() {}.loadBind(mView as BuyListActivity).execute(
                object : ILoadBind<BaseBean<BuyListBean>> {
                    override fun excute(bean: BaseBean<BuyListBean>) {
                        if(bean.code==200){
                            bean.data.buy_list?.let{
                                (mView as BuyListActivity).setUI(bean.data.buy_list)
                            }

                        }
                    }
                }
            )
            /*object :StringCallback(){
                override fun onSuccess(response: Response<String>?) {
                  response!!.body()
                }

            }*/
            )
    }
}