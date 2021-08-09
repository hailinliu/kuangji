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
import it.mbkj.personcenter.databinding.ActivityMyTransferBinding
import it.mbkj.personcenter.presenter.TranferActivityPresenter
import kotlinx.android.synthetic.main.personcenter_head.*
import java.math.BigDecimal

@Route(path = ArouterAddress.TRANFERACTIVITY)
class TranferActivity:BaseActivity<TranferActivityPresenter>() {
    @JvmField
    @Autowired
    var hua:String?=null
    @JvmField
    @Autowired
    var jiaoyi:String?=null
    var binding:ActivityMyTransferBinding?=null
    override val layoutId: Int
        get() = R.layout.activity_my_transfer
    override val presenter: TranferActivityPresenter
        get() = TranferActivityPresenter()

    override fun initView() {
        ARouter.getInstance().inject(this)
        setTitle("划转")
        right_tv.text = "划转记录"
        binding= getViewDataBinding<ActivityMyTransferBinding>()
        binding!!.include.rightTv.setOnClickListener {
            ARouter.getInstance().build(ArouterAddress.TRANSFERRECORDACTIVITY).navigation()
        }
        binding!!.etAddress.setHint(EditHintUtils.setHintSizeAndContent("请输入接收用户的手机号",12,true))
        binding!!.etNum.setHint(EditHintUtils.setHintSizeAndContent("请输入您划转的数量",12,true))
        binding!!.etPass.setHint(EditHintUtils.setHintSizeAndContent("请输入您的划转密码",12,true))
        binding!!.tvQuan.setOnClickListener {

        }
        binding!!.tvSure.setOnClickListener {
         mPresenter!!.transfer(SPUtil.get(SPConfig.SESSION_ID,""),binding!!.etAddress.text.toString(),binding!!.etNum.text.toString(),binding!!.etPass.text.toString())
        }
        RxTextView.textChanges(binding!!.etNum).subscribe {
            var text = it.toString()
            if(!text.isNullOrEmpty()){
               // var d= BigDecimal(text).multiply(BigDecimal(hua!!)).divide(
                  //  BigDecimal(100),2, BigDecimal.ROUND_DOWN).stripTrailingZeros()
              //  binding!!.tvShouxu.text = "手续费:"+d.toPlainString()+"USDT"
                binding!!.tvDaozhang.text = "到账数量:"+ BigDecimal(text).stripTrailingZeros().toPlainString()+"USDT"
            }else{
               // binding!!.tvShouxu.text = "手续费:0 USDT"
                binding!!.tvDaozhang.text = "到账数量:0 USDT"
            }


        }
        binding!!.tvTiqu.text = "可划转USDT:"+jiaoyi+" USDT"
    }

    override fun initEvent() {
        //super.initEvent()
        binding!!.tvQuan.setOnClickListener {
            binding!!.etNum.text = Editable.Factory.getInstance().newEditable(jiaoyi!!.toDouble().toInt().toString())
        }
    }
}