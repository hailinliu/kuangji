package it.mbkj.personcenter.activity

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.sskj.common.util.SPUtil
import it.mbkj.lib.base.ArouterAddress
import it.mbkj.lib.base.BaseActivity
import it.mbkj.lib.http.SPConfig
import it.mbkj.personcenter.R
import it.mbkj.personcenter.bean.ZiChanBean
import it.mbkj.personcenter.databinding.ActivityZichanBinding
import it.mbkj.personcenter.presenter.ZichanXinXiActivityPresenter
import kotlinx.android.synthetic.main.activity_zichan.*

@Route(path = ArouterAddress.ZICHANXINXIACTIVITY)
class ZichanXinXiActivity:BaseActivity<ZichanXinXiActivityPresenter>() {
    var binding:ActivityZichanBinding?=null
    override val layoutId: Int
        get() = R.layout.activity_zichan
    override val presenter: ZichanXinXiActivityPresenter
        get() = ZichanXinXiActivityPresenter()

    override fun initView() {
       setTitle("资产信息")
       binding=  getViewDataBinding<ActivityZichanBinding>()
    }
    override fun initEvent() {
        tv_cong.setOnClickListener(View.OnClickListener {
           ARouter.getInstance().build(ArouterAddress.RECHARGEACTIVITY).navigation()
        })
        tv_ti.setOnClickListener(View.OnClickListener {
            ARouter.getInstance().build(ArouterAddress.WITHDRAWACTIVITY).withString("ti",ti).withString("jiaoyi",jiaoyi).navigation()
        })
        tv_hua.setOnClickListener(View.OnClickListener {
            ARouter.getInstance().build(ArouterAddress.TRANFERACTIVITY).withString("hua",hua).withString("jiaoyi",jiaoyi).navigation()
        })
    }
    var jiaoyi:String?=null
    var ti:String?=null
    var hua:String?=null
    override fun initData() {
        mPresenter!!.getZiChan(SPUtil.get(SPConfig.SESSION_ID,""))
    }
    fun setUI(data: ZiChanBean) {
        binding!!.tvNum.text=data.data.money
        binding!!.tvCoin.text=data.data.score
        jiaoyi = data.data.score
        ti = data.withdraw_scale
        hua = data.transfer_scale
    }
}