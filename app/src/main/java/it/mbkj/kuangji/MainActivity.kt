package it.mbkj.kuangji


import android.util.SparseArray
import android.view.View
import androidx.core.view.get
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import com.sskj.common.base.App
import com.sskj.common.rxbus2.RxBus
import com.sskj.common.rxbus2.Subscribe
import com.sskj.common.rxbus2.ThreadMode
import com.sskj.common.util.ToastUtil
import it.mbkj.homepage.fragment.HomePageFragment
import it.mbkj.kuangji.presenter.MainActivityPresenter
import it.mbkj.lib.base.AppManager
import it.mbkj.lib.base.ArouterAddress
import it.mbkj.lib.base.BaseActivity
import it.mbkj.lib.bean.TabItem
import it.mbkj.lib.utils.ImageUtil
import it.mbkj.personcenter.fragment.PersonCenterFragment
import it.mbkj.tradecenter.fragment.TradCenterFragment
import it.mbkj.tradecenter.fragment.XingQiuFragment
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList


@Route(path = ArouterAddress.MAINACTIVITY)
class MainActivity : BaseActivity<MainActivityPresenter>() {
    @JvmField
    @Autowired
    var sessionId:String?=null
    private var fragments: ArrayList<Fragment>? = null
    var tabMap = SparseArray<Fragment>()
    private var curPos = 0
    override fun initView() {
        ARouter.getInstance().inject(this)
        RxBus.getDefault().register(this)
        ImageUtil.setOriginImage(R.mipmap.homepage_d,gif)
        val tabItems = ArrayList<CustomTabEntity>()
        tabItems.add(
            TabItem(
                "首页",
                R.mipmap.lib_icon_menu_first_unselect,
                R.mipmap.lib_icon_menu_first_select
            )
        )
        tabItems.add(
            TabItem(
                "机甲大厅",
                R.mipmap.lib_icon_menu_second_unselect,
                R.mipmap.lib_icon_menu_second_select
            )
        )
        tabItems.add(
            TabItem(
                "贸易中心",
                R.mipmap.lib_icon_menu_third_unselect,
                R.mipmap.lib_icon_menu_third_select
            )
        )
        tabItems.add(
            TabItem(
                "个人中心",
                R.mipmap.lib_icon_menu_four_unselect,
                R.mipmap.lib_icon_menu_four_select
            )
        )
        fragments = ArrayList()
        fragments?.add(
            (ARouter.getInstance().build(ArouterAddress.HOMEPAGEFRAGMENT).withString("sessionId",sessionId).withInt("flag",1).navigation() as HomePageFragment)
        )

        fragments?.add(
            (ARouter.getInstance().build(ArouterAddress.XINGQIUFRAGMENT).navigation() as XingQiuFragment)
        )
        fragments?.add(
            (ARouter.getInstance().build(ArouterAddress.TRADCENTERFRAGMENT).navigation() as TradCenterFragment)
        )
        fragments?.add(
            (ARouter.getInstance().build(ArouterAddress.PERSONCENTERFRAGMENT).navigation() as PersonCenterFragment)
        )
        supportFragmentManager
            .beginTransaction()
            .add(R.id.frameLayout, fragments!![0])
            .show(fragments!![0])
            .commitAllowingStateLoss()
        tabMap.put(0, fragments!![0])
        commonTabLayout.setTabData(tabItems)
        commonTabLayout.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabSelect(position: Int) {
                changeTab(position)
            }

            override fun onTabReselect(position: Int) {}
        })
    }
    @Subscribe(threadMode = ThreadMode.MAIN,code=1005)
    fun getCode(){
        changeTab(0)
        commonTabLayout.currentTab =0
        var fragment = supportFragmentManager.fragments[0] as HomePageFragment
        fragment.initData()
        }
    override val layoutId: Int
        get() = R.layout.activity_main
    override val presenter: MainActivityPresenter
        get() = MainActivityPresenter()
    private fun changeTab(position: Int) {
        if (tabMap[position] == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.frameLayout, fragments!![position])
                //.add()
                .hide(fragments!![curPos])
                .show(fragments!![position])
                .commitAllowingStateLoss()
            tabMap.put(position, fragments!![position])
        } else {
            supportFragmentManager
                .beginTransaction()
                .hide(fragments!![curPos])
                .show(fragments!![position])
                .commitAllowingStateLoss()
            if(position==0){
                var fragment:HomePageFragment =supportFragmentManager.fragments.get(0) as HomePageFragment
                fragment.initData()

            }
                if(position==1){
                    for(i in supportFragmentManager.fragments){
                        if(i is XingQiuFragment){
                            (i as XingQiuFragment).initData()
                        }
                    }
                    /*if(supportFragmentManager.fragments.get(2) is XingQiuFragment){
                        var fragment1:XingQiuFragment =supportFragmentManager.fragments.get(2) as XingQiuFragment
                        fragment1.initData()
                    }else if(supportFragmentManager.fragments.get(2) is TradCenterFragment){
                        var fragment2:TradCenterFragment =supportFragmentManager.fragments.get(2) as TradCenterFragment
                        fragment2.initData()
                    }else if(supportFragmentManager.fragments.get(2) is PersonCenterFragment){
                        var fragment3:PersonCenterFragment =supportFragmentManager.fragments.get(2) as PersonCenterFragment
                        fragment3.initData()
                    }*/

                }
                if(position==2){
                    for(i in supportFragmentManager.fragments){
                        if(i is TradCenterFragment){
                            (i as TradCenterFragment).initData()
                        }
                    }
                   /* if(supportFragmentManager.fragments.get(3) is XingQiuFragment){
                        var fragment1:XingQiuFragment =supportFragmentManager.fragments.get(3) as XingQiuFragment
                        fragment1.initData()
                    }else if(supportFragmentManager.fragments.get(3) is TradCenterFragment){
                        var fragment2:TradCenterFragment =supportFragmentManager.fragments.get(3) as TradCenterFragment
                        fragment2.initData()
                    }else if(supportFragmentManager.fragments.get(3) is PersonCenterFragment){
                        var fragment3:PersonCenterFragment =supportFragmentManager.fragments.get(3) as PersonCenterFragment
                        fragment3.initData()
                    }*/
                }
                if(position==3){
                    for(i in supportFragmentManager.fragments){
                        if(i is PersonCenterFragment){
                            (i as PersonCenterFragment).initData()
                        }
                    }
                  /*  if(supportFragmentManager.fragments.get(4) is XingQiuFragment){
                        var fragment1:XingQiuFragment =supportFragmentManager.fragments.get(4) as XingQiuFragment
                        fragment1.initData()
                    }else if(supportFragmentManager.fragments.get(4) is TradCenterFragment){
                        var fragment2:TradCenterFragment =supportFragmentManager.fragments.get(4) as TradCenterFragment
                        fragment2.initData()
                    }else if(supportFragmentManager.fragments.get(4) is PersonCenterFragment){
                        var fragment3:PersonCenterFragment =supportFragmentManager.fragments.get(4) as PersonCenterFragment
                        fragment3.initData()
                    }*/
                }

        }
        curPos = position
        if(position==0){
           gif.visibility = View.VISIBLE


        }else{
            gif.visibility = View.GONE
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
