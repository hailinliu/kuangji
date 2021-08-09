package it.mbkj.homepage.activity

import android.app.Activity
import android.os.Build
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.sskj.common.rxbus2.RxBus
import com.sskj.common.util.SPUtil
import it.mbkj.homepage.R
import it.mbkj.homepage.presenter.NewActivityPresenter
import it.mbkj.lib.base.ArouterAddress
import it.mbkj.lib.base.BaseActivity
import it.mbkj.lib.http.SPConfig
import it.mbkj.lib.utils.ImageUtil
import kotlinx.android.synthetic.main.homepage_new_activity.*

@Route(path = ArouterAddress.NEWACTIVITY)
class NewActivity:BaseActivity<NewActivityPresenter>() {
    @JvmField
    @Autowired
    var name:String?=null
    @JvmField
    @Autowired
    var id:String?=null
    @JvmField
    @Autowired
    var can_get:String?=null
    override val layoutId: Int
        get() = R.layout.homepage_new_activity
    override val presenter: NewActivityPresenter
        get() = NewActivityPresenter()

    override fun initView() {
        ARouter.getInstance().inject(this)
        RxBus.getDefault().register(this)
        //super.initView()
        ImageUtil.setOriginImage(R.mipmap.homepage_dong,gif)

        tv_title.text = name
        if(can_get!!.toInt()==0){
            tv_bt.text ="已领取"
            tv_bt.isEnabled =false
            tv_bt.isClickable = false
        }else if(can_get!!.toInt()==1){
            tv_bt.text ="领取收益"
            tv_bt.isEnabled =true
            tv_bt.isClickable = true
        }
    }

    override fun initEvent() {
        tv_bt.setOnClickListener {
            mPresenter!!.get(SPUtil.get(SPConfig.SESSION_ID,""),id!!)
            RxBus.getDefault().send(1101)
            tv_bt.text ="已领取"
        }
        image.setOnClickListener {
            finish()
        }
    }

}