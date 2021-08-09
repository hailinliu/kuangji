package it.mbkj.login.activity

import android.text.Editable
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.EditText
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.sskj.common.base.App
import com.sskj.common.rxbus2.RxBus
import com.sskj.common.util.SPUtil
import com.sskj.common.util.ToastUtil
import it.mbkj.lib.base.AppManager
import it.mbkj.lib.base.ArouterAddress
import it.mbkj.lib.base.BaseActivity
import it.mbkj.lib.base.EditHintUtils
import it.mbkj.lib.http.SPConfig
import it.mbkj.login.R
import it.mbkj.login.bean.LoginBean
import it.mbkj.login.databinding.ActivityLoginBinding
import it.mbkj.login.presenter.LoginActivityPresenter
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*

@Route(path = ArouterAddress.LOGINACTIVITY)
class LoginActivity:BaseActivity<LoginActivityPresenter>() {
    var isSee:Boolean=true
    var binding:ActivityLoginBinding?=null
    override val layoutId: Int
        get() = R.layout.activity_login
    override val presenter: LoginActivityPresenter
        get() = LoginActivityPresenter()

    override fun initView() {
        //super.initView()
        ARouter.getInstance().inject(this)
        RxBus.getDefault().register(this)
        binding =  getViewDataBinding<ActivityLoginBinding>()
        if(!SPUtil.get(SPConfig.PHONE,"").isNullOrEmpty()){
          binding!!.etPhone.text = Editable.Factory.getInstance().newEditable(SPUtil.get(SPConfig.PHONE,""))
        }
        if(!SPUtil.get(SPConfig.PWD,"").isNullOrEmpty()){
            binding!!.etPwd.text = Editable.Factory.getInstance().newEditable(SPUtil.get(SPConfig.PWD,""))
        }
        binding!!.etPhone.setHint(EditHintUtils.setHintSizeAndContent("请输入手机号",12,true))
        binding!!.etPwd.setHint(EditHintUtils.setHintSizeAndContent("请输入密码",12,true))

    }

    override fun initData() {

        binding!!.tvBt.setOnClickListener {
            mPresenter!!.login(binding!!.etPhone.text.toString(),binding!!.etPwd.text.toString())
        }
        binding!!.tvRegister.setOnClickListener {
            ARouter.getInstance().build(ArouterAddress.REGISTERACTIVITY).navigation()
        }
        binding!!.tvForget.setOnClickListener {
            ARouter.getInstance().build(ArouterAddress.FORGETPWDACTIVITY).navigation()
        }
        binding!!.ivSee.setOnClickListener {
            if(isSee){
                binding!!.ivSee.setImageResource(R.mipmap.login_unsee)
                binding!!.etPwd.transformationMethod= PasswordTransformationMethod.getInstance()
                binding!!.etPwd.setSelection(binding!!.etPwd.text.length)
            }else{
                binding!!.ivSee.setImageResource(R.mipmap.login_see)
                binding!!.etPwd.transformationMethod= HideReturnsTransformationMethod.getInstance()
                binding!!.etPwd.setSelection(binding!!.etPwd.text.length)
            }
            isSee = !isSee
        }

    }
        fun setData(bean:LoginBean){
            if(bean.code==200){
                ToastUtil.showShort(bean.msg)
                SPUtil.put(SPConfig.SESSION_ID,bean.session_id)
                SPUtil.put(SPConfig.PWD,binding!!.etPwd.text.toString())
                SPUtil.put(SPConfig.PHONE,binding!!.etPhone.text.toString())
               // RxBus.getDefault().send(1005)
                ARouter.getInstance().build(ArouterAddress.MAINACTIVITY).withString("sessionId",bean.session_id).navigation()
            }else{
               // ARouter.getInstance().build(ArouterAddress.MAINACTIVITY).navigation()
                ToastUtil.showShort(bean.msg)
            }
        }
    private var oldTime: Long = 0
    override fun onBackPressed() {
        val nowTime = Date().time
        if (nowTime - oldTime <= 2000) {
            AppManager.getSingleton().AppExit(this)
        } else {
            oldTime = nowTime
            ToastUtil.showShort(App.INSTANCE.getString(R.string.libbaseActivity1))
        }
    }
}