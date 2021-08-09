package it.mbkj.personcenter.presenter

import com.lzy.okgo.OkGo
import it.mbkj.lib.base.BaseBean
import it.mbkj.lib.base.BasePresenter
import it.mbkj.lib.base.HttpsUtil
import it.mbkj.lib.base.ILoadBind
import it.mbkj.lib.http.CallBackOption
import it.mbkj.lib.http.HttpConfig
import it.mbkj.personcenter.bean.HomePageBean
import it.mbkj.personcenter.bean.PledgeListBean
import it.mbkj.personcenter.fragment.PersonCenterFragment

class PersonCenterFragmentPresenter: BasePresenter<PersonCenterFragment>() {
    fun getHomePage(session_id:String){
        OkGo.post<BaseBean<HomePageBean>>(HttpConfig.BASE_URL+"/v1.index/index")
            .params("session_id",session_id)
            .execute(object : CallBackOption<BaseBean<HomePageBean>>() {}.loadBind(mView as PersonCenterFragment).execute(
                object :ILoadBind<BaseBean<HomePageBean>>{
                    override fun excute(bean: BaseBean<HomePageBean>) {
                        /* if(bean.error_code==20000){
                             ARouter.getInstance().build(ArouterAddress.LOGINACTIVITY).navigation()
                         }else*/ if(bean.code==200){
                            (mView as PersonCenterFragment).setUI(bean.data.user_msg)
                        }
                    }
                }
            ))
    }

}