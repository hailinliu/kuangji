package it.mbkj.personcenter.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.sskj.common.util.SPUtil
import it.mbkj.lib.base.ArouterAddress
import it.mbkj.lib.base.BaseActivity
import it.mbkj.lib.base.EditHintUtils
import it.mbkj.lib.http.SPConfig
import it.mbkj.personcenter.R
import it.mbkj.personcenter.databinding.ActivityMyUpdateLoginPwdBinding
import it.mbkj.personcenter.databinding.ActivityMyUpdatePwdBinding
import it.mbkj.personcenter.presenter.UpdateLoginActivityPresenter


@Route(path = ArouterAddress.UPDATELOGINACTIVITY)
class UpdateLoginActivity:BaseActivity<UpdateLoginActivityPresenter>() {
    var binding:ActivityMyUpdateLoginPwdBinding?=null
    override val layoutId: Int
        get() = R.layout.activity_my_update_login_pwd
    override val presenter: UpdateLoginActivityPresenter
        get() = UpdateLoginActivityPresenter()

    override fun initView() {
       setTitle("修改登录密码")
         binding =getViewDataBinding<ActivityMyUpdateLoginPwdBinding>()
        binding!!.etNewPwd.setHint(EditHintUtils.setHintSizeAndContent("请输入旧的登录密码",12,true))
        binding!!.etPass.setHint(EditHintUtils.setHintSizeAndContent("请输入新的登录密码",12,true))
        binding!!.etXin.setHint(EditHintUtils.setHintSizeAndContent("请再次确认登录密码",12,true))
    }

    override fun initEvent() {
        binding!!.tvSure.setOnClickListener {
            mPresenter!!.updataLoginPwd(SPUtil.get(SPConfig.SESSION_ID,""),binding!!.etNewPwd.text.toString(),binding!!.etPass.text.toString(),binding!!.etXin.text.toString())
        }
    }

    fun setUI(data: String) {
        finish()

    }
}