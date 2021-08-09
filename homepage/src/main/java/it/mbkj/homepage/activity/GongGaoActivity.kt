package it.mbkj.homepage.activity

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.zzhoujay.richtext.RichText
import it.mbkj.homepage.R
import it.mbkj.homepage.bean.NoticeBean
import it.mbkj.homepage.databinding.HomepageGonggaoBinding
import it.mbkj.homepage.presenter.GongGaoActivityPresenter
import it.mbkj.lib.base.ArouterAddress
import it.mbkj.lib.base.BaseActivity
import net.idik.lib.slimadapter.SlimAdapter
import java.text.SimpleDateFormat
import java.util.*


@Route(path = ArouterAddress.GONGGAOACTIVITY)
class GongGaoActivity:BaseActivity<GongGaoActivityPresenter>() {
    @JvmField
    @Autowired
    var mybean: NoticeBean?=null
    var adapter:SlimAdapter?=null
    var binding:HomepageGonggaoBinding?=null
    override val layoutId: Int
        get() = R.layout.homepage_gonggao
    override val presenter: GongGaoActivityPresenter
        get() = GongGaoActivityPresenter()

    override fun initView() {
       // super.initView()
        ARouter.getInstance().inject(this)
        binding= getViewDataBinding<HomepageGonggaoBinding>()
        binding!!.rv.layoutManager=LinearLayoutManager(this)
        setTitle("公告")
        adapter = SlimAdapter.create().register<NoticeBean.Data.Notice>(R.layout.homepage_gonggao_item){
                data, injector ->
                injector.text(R.id.tv_title,data.title)
                    .text(R.id.tv_time, SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date(data.create_time*1000)))
                    .clicked(R.id.ll_item, View.OnClickListener {
                        ARouter.getInstance().build(ArouterAddress.GONGGAODETAILACTIVITY).withString("id",data.id.toString()).navigation()
                    })
                RichText.from(data.introduce).into(injector.findViewById(R.id.tv_content))
        }
            .attachTo(binding!!.rv).updateData(mybean!!.data.notice_list)
    }

    override fun initEvent() {
        //super.initEvent()
    }

    override fun initData() {
    }
}