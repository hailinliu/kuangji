package it.mbkj.personcenter.activity

import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import com.sskj.common.util.SPUtil
import it.mbkj.lib.base.ArouterAddress
import it.mbkj.lib.base.BaseActivity
import it.mbkj.lib.http.HttpConfig
import it.mbkj.lib.http.SPConfig
import it.mbkj.lib.utils.ImageUtil
import it.mbkj.personcenter.R
import it.mbkj.personcenter.bean.Demo
import it.mbkj.personcenter.bean.MyTeamBean
import it.mbkj.personcenter.bean.TabItem
import it.mbkj.personcenter.databinding.ActivityMyTeamBinding
import it.mbkj.personcenter.presenter.MyTeamActivityPresenter
import net.idik.lib.slimadapter.SlimAdapter
import java.util.*
import kotlin.collections.ArrayList

@Route(path = ArouterAddress.MYTEAMACTIVITY)
class MyTeamActivity:BaseActivity<MyTeamActivityPresenter>() {
    var binding:ActivityMyTeamBinding?=null
    var adapter: SlimAdapter?=null
    override val layoutId: Int
        get() = R.layout.activity_my_team
    override val presenter: MyTeamActivityPresenter
        get() = MyTeamActivityPresenter()

    override fun initView() {
       binding=  getViewDataBinding<ActivityMyTeamBinding>()
        setTitle("我的团队")
        val tabItems = ArrayList<CustomTabEntity>()
        tabItems.add(TabItem("直推"))
        tabItems.add(TabItem("间推"))
        binding!!.commonTabLayout.setTabData(tabItems)
        binding!!.rv.layoutManager = LinearLayoutManager(this)
        adapter = SlimAdapter.create().register<Demo>(R.layout.activity_my_team_item){ data, injector ->
            injector.text(R.id.tv_name, data.phone.substring(0,3)+"****"+data.phone.substring(7,data.phone.length) + "/" + data.level)
                .text(R.id.tv_num, data.team_machine_sum.toString())
            val image = injector.findViewById<ImageView>(R.id.image1)
            ImageUtil.setImage(HttpConfig.BASE_IMG_URL + data.level_img, image)
        }
            .attachTo(binding!!.rv)

    }

    override fun initData() {
        mPresenter!!.getTeam(SPUtil.get(SPConfig.SESSION_ID, ""))
       // binding!!.tvZhitui.text =
    }

    override fun initEvent() {
       // super.initEvent()
        binding!!.commonTabLayout.setOnTabSelectListener(object :OnTabSelectListener{
            override fun onTabSelect(position: Int) {
                if(position==0){
                    adapter!!.updateData(list1)
                }else if(position==1){
                    adapter!!.updateData(list2)
                }
            }

            override fun onTabReselect(position: Int) {
            }
        })
    }
   var list1:List<Demo> = ArrayList()
    var list2:List<Demo> = ArrayList()
    fun setUI(data: MyTeamBean) {
       list1= data.list1
       list2= data.list2
        binding!!.tvZhitui.text=data.team_sum.toString()
        binding!!.tvSuanli.text = data.team_machine_sum
        adapter!!.updateData(list1)
    }
}