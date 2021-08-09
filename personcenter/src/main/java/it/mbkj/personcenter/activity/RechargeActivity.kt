package it.mbkj.personcenter.activity

import android.content.ClipData
import android.content.ClipboardManager
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.sskj.common.util.SPUtil
import com.sskj.common.util.ToastUtil
import it.mbkj.lib.base.ArouterAddress
import it.mbkj.lib.base.BaseActivity
import it.mbkj.lib.http.HttpConfig
import it.mbkj.lib.http.SPConfig
import it.mbkj.lib.utils.ImageUtil
import it.mbkj.personcenter.R
import it.mbkj.personcenter.bean.RechargeBean
import it.mbkj.personcenter.databinding.ActivityMyCongBinding
import it.mbkj.personcenter.presenter.RechargeActivityPresenter

@Route(path = ArouterAddress.RECHARGEACTIVITY)
class RechargeActivity:BaseActivity<RechargeActivityPresenter>() {
    var binding:ActivityMyCongBinding?=null

    //剪切板管理工具类
     var mClipboardManager: ClipboardManager? = null

    //剪切板Data对象
     var mClipData: ClipData? = null
    override val layoutId: Int
        get() = R.layout.activity_my_cong
    override val presenter: RechargeActivityPresenter
        get() = RechargeActivityPresenter()

    override fun initView() {
      binding=  getViewDataBinding<ActivityMyCongBinding>()
        mClipboardManager = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        setTitle("充值")
        binding!!.include.rightTv.text="充值记录"
       // super.initView()
    }

    override fun initData() {
       // super.initData()
        mPresenter!!.getAddress(SPUtil.get(SPConfig.SESSION_ID,""))
    }
    override fun initEvent() {
       binding!!.include.rightTv.setOnClickListener(View.OnClickListener { ARouter.getInstance().build(ArouterAddress.RECHARGERECORDACTIVITY).navigation()})
    }

    fun setUI(data: RechargeBean) {
        ImageUtil.setImage(HttpConfig.BASE_IMG_URL + data.wallet_img, binding!!.image)
        binding!!.tvAddress.text = data.address
        binding!!.tvFuzhi.setOnClickListener {

            //复制
            //创建一个新的文本clip对象
            mClipData = ClipData.newPlainText("Simple test", data.address)
            //把clip对象放在剪贴板中
            mClipboardManager!!.primaryClip = mClipData
            ToastUtil.showShort("复制成功")
        }

    }

}