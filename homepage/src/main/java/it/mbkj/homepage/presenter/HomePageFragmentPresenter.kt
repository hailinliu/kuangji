package it.mbkj.homepage.presenter

import com.alibaba.android.arouter.launcher.ARouter
import com.lzy.okgo.OkGo
import com.lzy.okgo.callback.StringCallback
import com.lzy.okgo.model.Response
import com.sskj.common.util.GSonUtil
import com.sskj.common.util.ToastUtil
import it.mbkj.homepage.bean.HomePageBean
import it.mbkj.homepage.bean.NoticeBean
import it.mbkj.homepage.fragment.HomePageFragment
import it.mbkj.lib.base.ArouterAddress
import it.mbkj.lib.base.BaseBean
import it.mbkj.lib.base.BasePresenter
import it.mbkj.lib.base.ILoadBind
import it.mbkj.lib.http.CallBackOption
import it.mbkj.lib.http.HttpConfig

class HomePageFragmentPresenter :BasePresenter<HomePageFragment>(){
    fun getHomePage(session_id:String){
        OkGo.post<BaseBean<HomePageBean>>(HttpConfig.BASE_URL+"/v1.index/index")
            .params("session_id",session_id)
            .execute(object : CallBackOption<BaseBean<HomePageBean>>() {}.loadBind(mView as HomePageFragment).execute(
                object :ILoadBind<BaseBean<HomePageBean>>{
                    override fun excute(bean: BaseBean<HomePageBean>) {
                        if(bean.error_code==20000){
                            ARouter.getInstance().build(ArouterAddress.LOGINACTIVITY).navigation()
                        }else if(bean.code==200){
                            (mView as HomePageFragment).setUI(bean.data)
                        }
                    }
                }
            ))
    }
    fun getNotice(session_id:String){
        OkGo.post<String>(HttpConfig.BASE_URL+"/v1.index/notice_list")
            .params("session_id",session_id)
            .execute(object:StringCallback(){
                override fun onSuccess(s: Response<String>?) {
                 var bean= GSonUtil.GsonToBean(s!!.body(),NoticeBean::class.java)
                    if(bean.code==200){
                        (mView as HomePageFragment).setother(bean)
                    }/*else{
                        ToastUtil.showShort(bean.msg)
                    }*/

                }

            })
    }
}