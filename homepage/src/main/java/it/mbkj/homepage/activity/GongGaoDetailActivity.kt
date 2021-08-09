package it.mbkj.homepage.activity

import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.sskj.common.util.SPUtil
import com.zzhoujay.richtext.RichText
import it.mbkj.homepage.R
import it.mbkj.homepage.bean.NoticeMsg
import it.mbkj.homepage.databinding.HomepageGonggaoDetailBinding
import it.mbkj.homepage.presenter.GongGaoDetailActivityPresenter
import it.mbkj.lib.base.ArouterAddress
import it.mbkj.lib.base.BaseActivity
import it.mbkj.lib.http.SPConfig
import it.mbkj.lib.utils.TimeFormatUtil
import java.sql.Date
import java.text.SimpleDateFormat


@Route(path = ArouterAddress.GONGGAODETAILACTIVITY)
class GongGaoDetailActivity:BaseActivity<GongGaoDetailActivityPresenter>() {
    @JvmField
    @Autowired
    var id:String?=null
    var binding:HomepageGonggaoDetailBinding?=null

    override val layoutId: Int
        get() = R.layout.homepage_gonggao_detail
    override val presenter: GongGaoDetailActivityPresenter
        get() = GongGaoDetailActivityPresenter()

    override fun initView() {
        //super.initView()
         binding=getViewDataBinding<HomepageGonggaoDetailBinding>()
        setTitle("公告详情")
    }

    override fun initData() {
        ARouter.getInstance().inject(this)
        mPresenter!!.getDetail(id!!,SPUtil.get(SPConfig.SESSION_ID,""))
    }
    fun setUI(noticeMsg: NoticeMsg) {
       var time= SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date(noticeMsg.create_time*1000))
        binding!!.tvTime.text = time
        RichText.from(noticeMsg.content).into(binding!!.tvContent)
    }

}
