package it.mbkj.personcenter.activity

import android.content.ClipData
import android.content.ClipboardManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.sskj.common.util.SPUtil
import com.sskj.common.util.ToastUtil
import it.mbkj.lib.base.ArouterAddress
import it.mbkj.lib.base.BaseActivity
import it.mbkj.lib.http.HttpConfig
import it.mbkj.lib.http.SPConfig
import it.mbkj.lib.utils.ImageUtil
import it.mbkj.personcenter.R
import it.mbkj.personcenter.bean.YaoQingBean
import it.mbkj.personcenter.databinding.ActivityInviteBinding
import it.mbkj.personcenter.presenter.InviteFriendsActivityPresenter
@Route(path = ArouterAddress.INVITEFRIENDSACTIVITY)
class InviteFriendsActivity: BaseActivity<InviteFriendsActivityPresenter>() {
    var binding:ActivityInviteBinding?=null
    //剪切板管理工具类
    var mClipboardManager: ClipboardManager? = null

    //剪切板Data对象
    var mClipData: ClipData? = null
    override val layoutId: Int
        get() = R.layout.activity_invite
    override val presenter: InviteFriendsActivityPresenter
        get() = InviteFriendsActivityPresenter()

    override fun initView() {
       binding = getViewDataBinding<ActivityInviteBinding>()
        mClipboardManager = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        binding!!.leftImg.setOnClickListener {
            finish()
        }
    }

    override fun initData() {
        mPresenter!!.getInvite(SPUtil.get(SPConfig.SESSION_ID,""))
    }
    fun getData(data: YaoQingBean) {
        binding!!.tvYaoqing.text = data.agent_code.toString()
        ImageUtil.setCircleImageReady(data.img_url) {
            binding!!.llYaoqing.background = it
        }
       // binding!!.llYaoqing.background =

        binding!!.image.setOnClickListener {
            //复制
            //创建一个新的文本clip对象
            mClipData = ClipData.newPlainText("Simple test", data.agent_code.toString())
            //把clip对象放在剪贴板中
            mClipboardManager!!.primaryClip = mClipData
            ToastUtil.showShort("复制成功")
        }
    }


}