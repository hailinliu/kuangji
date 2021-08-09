package it.mbkj.tradecenter.presenter

import com.lzy.okgo.OkGo
import it.mbkj.lib.base.BaseBean
import it.mbkj.lib.base.BasePresenter
import it.mbkj.lib.base.ILoadBind
import it.mbkj.lib.http.CallBackOption
import it.mbkj.lib.http.HttpConfig
import it.mbkj.tradecenter.activity.SellListActivity
import it.mbkj.tradecenter.bean.SellListBean

class SellListActivityPresenter:BasePresenter<SellListActivity>(){
    fun getList(session_id:String,page:Int){
        OkGo.post<BaseBean<SellListBean>>(HttpConfig.BASE_URL+"/v1.deal/sell_list")
                .params("session_id",session_id)
                .params("page",page)
                .execute(object : CallBackOption<BaseBean<SellListBean>>() {}.loadBind(mView as SellListActivity).execute(
                        object : ILoadBind<BaseBean<SellListBean>> {
                            override fun excute(bean: BaseBean<SellListBean>) {
                                if(bean.code==200){
                                    bean.data.sell_list?.let {
                                        (mView as SellListActivity).setUI(bean.data.sell_list)
                                    }



                                }
                            }
                        }
                )
                )
    }
}