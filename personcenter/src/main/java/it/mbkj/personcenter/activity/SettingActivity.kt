package it.mbkj.personcenter.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.sskj.common.util.SPUtil
import it.mbkj.lib.base.AppManager
import it.mbkj.lib.base.ArouterAddress
import it.mbkj.lib.base.BaseActivity
import it.mbkj.lib.http.SPConfig
import it.mbkj.lib.utils.APKVersionCodeUtils
import it.mbkj.personcenter.R
import it.mbkj.personcenter.presenter.SettingActivityPresenter
import kotlinx.android.synthetic.main.activity_my_set.*

@Route(path = ArouterAddress.SETTINGACTIVITY)
class SettingActivity:BaseActivity<SettingActivityPresenter>() {
    override val layoutId: Int
        get() = R.layout.activity_my_set
    override val presenter: SettingActivityPresenter
        get() = SettingActivityPresenter()

    override fun initView() {
        setTitle("设置")

    }

    override fun initEvent() {
        tv_xiugaijiaoyi.setOnClickListener {
            ARouter.getInstance().build(ArouterAddress.UPDATEDEALPASSWORDACTIVITY).navigation()
        }
        tv_xiugaidenglu.setOnClickListener {
            ARouter.getInstance().build(ArouterAddress.UPDATELOGINACTIVITY).navigation()
        }
        tv_tuichu.setOnClickListener {
            SPUtil.put(SPConfig.SESSION_ID,"")
            AppManager.getSingleton().finishAllActivity("MainActivity")
            ARouter.getInstance().build(ArouterAddress.LOGINACTIVITY).navigation()
            //mPresenter!!.getCode(SPUtil.get(SPConfig.SESSION_ID,""))
        }
        tv_banben.setText(APKVersionCodeUtils.getVerName(this))
    }
}