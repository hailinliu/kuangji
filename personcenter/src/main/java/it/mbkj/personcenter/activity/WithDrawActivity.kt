package it.mbkj.personcenter.activity

import android.text.Editable
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.jakewharton.rxbinding2.widget.RxTextView
import com.sskj.common.util.SPUtil
import it.mbkj.lib.base.ArouterAddress
import it.mbkj.lib.base.BaseActivity
import it.mbkj.lib.base.EditHintUtils
import it.mbkj.lib.http.SPConfig
import it.mbkj.personcenter.R
import it.mbkj.personcenter.databinding.ActivityMyTiBinding
import it.mbkj.personcenter.fragment.WithDrawActivityPresenter
import kotlinx.android.synthetic.main.personcenter_head.*
import java.math.BigDecimal

@Route(path = ArouterAddress.WITHDRAWACTIVITY)
class WithDrawActivity:BaseActivity<WithDrawActivityPresenter>() {
    @JvmField
    @Autowired
    var ti:String?=null
    @JvmField
    @Autowired
    var jiaoyi:String?=null
    var binding:ActivityMyTiBinding?=null
    override val layoutId: Int
        get() = R.layout.activity_my_ti
    override val presenter: WithDrawActivityPresenter
        get() = WithDrawActivityPresenter()

    override fun initView() {
        setTitle("提币")
        ARouter.getInstance().inject(this)
        binding = getViewDataBinding<ActivityMyTiBinding>()
        binding!!.include.rightTv.text = "提币记录"
        binding!!.tvTiqu.text = "可提取USDT:" + jiaoyi + " USDT"
        binding!!.etAddress.hint = EditHintUtils.setHintSizeAndContent("请输入您的提币地址",12,true)
        binding!!.etNum.hint = EditHintUtils.setHintSizeAndContent("请输入您提币的数量",12,true)
        binding!!.etPass.hint = EditHintUtils.setHintSizeAndContent("请输入您的提币密码",12,true)
        binding!!.tvShouxu.text = "手续费:"+"2"+" USDT"
        RxTextView.textChanges(binding!!.etNum).subscribe {
            var text = it.toString()
            if(!text.isNullOrEmpty()){
            /*   var d= BigDecimal(text).multiply(BigDecimal(ti!!)).divide(
                    BigDecimal(100),2,BigDecimal.ROUND_DOWN).stripTrailingZeros()*/
                binding!!.tvDaozhang.text = "到账数量:"+BigDecimal(text).subtract(BigDecimal("2")).stripTrailingZeros().toPlainString()+"USDT"
            }else{
                binding!!.tvDaozhang.text = "到账数量:0 USDT"
            }


        }
    }
    override fun initEvent() {
        binding!!.include.rightTv.setOnClickListener {
           ARouter.getInstance().build(ArouterAddress.WITHDRAWRECORDACTIVITY).navigation()
       }
        binding!!.tvQuan.setOnClickListener {
            binding!!.etNum.text = Editable.Factory.getInstance().newEditable(jiaoyi!!.toDouble().toInt().toString())
        }
        binding!!.tvSure.setOnClickListener {
           mPresenter!!.submit(SPUtil.get(SPConfig.SESSION_ID,""),binding!!.etAddress.text.toString(),binding!!.etNum.text.toString(),binding!!.etPass.text.toString())
        }
    }
}