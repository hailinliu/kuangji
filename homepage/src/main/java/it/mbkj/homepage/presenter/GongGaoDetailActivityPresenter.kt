package it.mbkj.homepage.presenter

import com.alibaba.android.arouter.launcher.ARouter
import com.lzy.okgo.OkGo
import com.sskj.common.util.ToastUtil
import it.mbkj.homepage.activity.GongGaoDetailActivity
import it.mbkj.homepage.bean.HomePageBean
import it.mbkj.homepage.bean.NoticeDetailBean
import it.mbkj.homepage.fragment.HomePageFragment
import it.mbkj.lib.base.ArouterAddress
import it.mbkj.lib.base.BaseBean
import it.mbkj.lib.base.BasePresenter
import it.mbkj.lib.base.ILoadBind
import it.mbkj.lib.http.CallBackOption
import it.mbkj.lib.http.HttpConfig

class GongGaoDetailActivityPresenter:BasePresenter<GongGaoDetailActivity>() {
    fun getDetail(id:String,session_id:String){
        OkGo.post<BaseBean<NoticeDetailBean>>(HttpConfig.BASE_URL+"/v1.index/notice_msg")
            .params("id",id)
            .params("session_id",session_id)
            .execute(object : CallBackOption<BaseBean<NoticeDetailBean>>() {}.loadBind(mView as GongGaoDetailActivity).execute(
            object : ILoadBind<BaseBean<NoticeDetailBean>> {
                override fun excute(bean: BaseBean<NoticeDetailBean>) {
                    if(bean.error_code==20000){
                        ARouter.getInstance().build(ArouterAddress.LOGINACTIVITY).navigation()
                    }else if(bean.error_code==1001){
                       ToastUtil.showShort(bean.msg)
                    }
                   else if(bean.code==200){
                        (mView as GongGaoDetailActivity).setUI(bean.data.notice_msg)
                    }
                }
            }
        ))
    }
}