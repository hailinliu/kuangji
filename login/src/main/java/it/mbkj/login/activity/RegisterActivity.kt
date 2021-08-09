package it.mbkj.login.activity

import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import com.alibaba.android.arouter.facade.annotation.Route
import com.sskj.common.util.DisposUtil
import com.sskj.common.util.RxSchedulersHelper
import com.sskj.common.util.ToastUtil
import io.reactivex.Flowable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function
import it.mbkj.lib.base.ArouterAddress
import it.mbkj.lib.base.BaseActivity
import it.mbkj.lib.base.EditHintUtils
import it.mbkj.login.R
import it.mbkj.login.bean.LoginBean
import it.mbkj.login.databinding.ActivityRegisterBinding
import it.mbkj.login.presenter.RegisterActivityPresenter
import java.util.concurrent.TimeUnit

@Route(path =ArouterAddress.REGISTERACTIVITY)
class RegisterActivity:BaseActivity<RegisterActivityPresenter>() {
    var binding:ActivityRegisterBinding?=null
    private var timerDispo: Disposable? = null
    var isSee1:Boolean=true
    var isSee2:Boolean=true
    override val layoutId: Int
        get() = R.layout.activity_register
    override val presenter: RegisterActivityPresenter
        get() = RegisterActivityPresenter()

    override fun initView() {
       binding= getViewDataBinding<ActivityRegisterBinding>()
        DisposUtil.close(timerDispo)
        binding!!.etPhone.setHint(EditHintUtils.setHintSizeAndContent("请输入手机号",12,true))
        binding!!.etPwd.setHint(EditHintUtils.setHintSizeAndContent("请输入密码",12,true))
        binding!!.etPwdAgain.setHint(EditHintUtils.setHintSizeAndContent("请再次输入密码",12,true))
        binding!!.etYanzheng.setHint(EditHintUtils.setHintSizeAndContent("请输入验证码",12,true))
        binding!!.etYaoqing.setHint(EditHintUtils.setHintSizeAndContent("请输入邀请码",12,true))
    }

    override fun initData() {

    }

    override fun initEvent() {
       binding!!.tvTime.setOnClickListener {
           mPresenter!!.getCode(binding!!.etPhone.text!!.toString())
       }
        binding!!.ivSee1.setOnClickListener {
            if(isSee1){
                binding!!.ivSee1.setImageResource(R.mipmap.login_unsee)
                binding!!.etPwd.transformationMethod= PasswordTransformationMethod.getInstance()
                binding!!.etPwd.setSelection(binding!!.etPwd.text.length)
            }else{
                binding!!.ivSee1.setImageResource(R.mipmap.login_see)
                binding!!.etPwd.transformationMethod= HideReturnsTransformationMethod.getInstance()
                binding!!.etPwd.setSelection(binding!!.etPwd.text.length)
            }
            isSee1 = !isSee1
        }
        binding!!.ivSee2.setOnClickListener {
            if(isSee2){
                binding!!.ivSee2.setImageResource(R.mipmap.login_unsee)
                binding!!.etPwd.transformationMethod= PasswordTransformationMethod.getInstance()
                binding!!.etPwd.setSelection(binding!!.etPwd.text.length)
            }else{
                binding!!.ivSee2.setImageResource(R.mipmap.login_see)
                binding!!.etPwd.transformationMethod= HideReturnsTransformationMethod.getInstance()
                binding!!.etPwd.setSelection(binding!!.etPwd.text.length)
            }
            isSee2 = !isSee2
        }
    }
    fun setData(bean: LoginBean) {
        if(bean.code==200){
            timerDispo = Flowable.intervalRange(1, 60, 0, 1, TimeUnit.SECONDS)
                .map(Function<Long,Long> {i: Long -> 60 - i})
                .compose(RxSchedulersHelper.transformer())
                .subscribe { i: Long ->
                    binding!!.tvTime.text="重新获取("+i+")"
                    binding!!.tvTime.isClickable=false
                    binding!!.tvTime.isEnabled=false
                    if (i == 0L) {
                        binding!!.tvTime.isClickable=true
                        binding!!.tvTime.isEnabled=true
                    }
                }
        }
        ToastUtil.showShort(bean.msg)
    }
}