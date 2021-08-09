package it.mbkj.personcenter.activity

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.sskj.common.rxbus2.RxBus
import com.sskj.common.util.SPUtil
import com.sskj.common.util.ToastUtil
import it.mbkj.lib.base.ArouterAddress
import it.mbkj.lib.base.BaseActivity
import it.mbkj.lib.http.SPConfig
import it.mbkj.lib.utils.TimeFormatUtil
import it.mbkj.personcenter.R
import it.mbkj.personcenter.bean.OrderMsg
import it.mbkj.personcenter.databinding.ActivityOrderDetailItemBinding
import it.mbkj.personcenter.presenter.OrderDetailActivityPresenter
import kotlinx.android.synthetic.main.alert_dialog.view.*
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.*


@Route(path = ArouterAddress.ORDERDETAILACTIVITY)
class OrderDetailActivity:BaseActivity<OrderDetailActivityPresenter>() {
    @JvmField
    @Autowired
    var id:String?=null
    var binding:ActivityOrderDetailItemBinding?=null
    var view:View? = null
    var dialog:AlertDialog?=null
    override val layoutId: Int
        get() = R.layout.activity_order_detail_item
    override val presenter: OrderDetailActivityPresenter
        get() = OrderDetailActivityPresenter()

    override fun initView() {
        binding = getViewDataBinding<ActivityOrderDetailItemBinding>()
        ARouter.getInstance().inject(this)
        RxBus.getDefault().register(this)
        setTitle("订单详情")
    }

    override fun initData() {
        //super.initData()

        mPresenter!!.getOrderDetail(SPUtil.get(SPConfig.SESSION_ID,""),id!!)
    }

    override fun initEvent() {
       binding!!.tvJiesuo.setOnClickListener(View.OnClickListener {

           view = LayoutInflater.from(this).inflate(R.layout.alert_dialog, null)
           view!!.tv_cancel.setOnClickListener(View.OnClickListener {
               dialog!!.dismiss()
           })
           // ToastUtil.showShort("已经解锁")
           view!!.tv_sure.setOnClickListener(View.OnClickListener {
               dialog!!.dismiss()
                mPresenter!!.cancel(SPUtil.get(SPConfig.SESSION_ID,""),id!!)
              // ToastUtil.showShort("已经确认")
           })
           dialog = AlertDialog.Builder(this).setView(view).create()
            dialog!!.window.setBackgroundDrawable(getDrawable(R.mipmap.person_xiaoqing))
           dialog!!.window.setGravity(Gravity.BOTTOM)
           var prama = dialog!!.window.attributes
           prama.y=200
           dialog!!.window.attributes=prama
           dialog!!.show()
       })

    }

    fun setUI(orderMsg: OrderMsg) {
        binding!!.tvTitle.text=orderMsg.pledge_name
        binding!!.tvNumTit.text = if(orderMsg.status==1)"质押中" else if(orderMsg.status==2)"已到期" else "已解锁"
        binding!!.tvNumTit.background = if(orderMsg.status==1) getDrawable(R.mipmap.person_zhiya_bging) else if(orderMsg.status==2)  getDrawable(R.mipmap.person_zhiya_bg_daoqi) else getDrawable(R.mipmap.person_zhiya_bged)
        binding!!.tvYushouyilu.text = BigDecimal(orderMsg.income).divide(BigDecimal(orderMsg.price),BigDecimal.ROUND_DOWN,2).stripTrailingZeros().toPlainString()
        binding!!.tvNumContent.text=orderMsg.price+"LBS"
        binding!!.tvQishiTimeContent.text = SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format((orderMsg.create_time.toLong())*1000)
        binding!!.tvDaoqiTimeContent.text = SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(orderMsg.end_time.toLong()*1000)
        binding!!.tvQixianContent1.text = orderMsg.all_time+"天"
        binding!!.tvYichiyouContent.text = ((Calendar.getInstance().time.time/1000-orderMsg.create_time.toLong())/3600/24).toString()+"天"
       if(((Calendar.getInstance().time.time/1000-orderMsg.create_time.toLong())/3600/24)>=orderMsg.all_time.toDouble()){
           binding!!.tvLingqu.isClickable = true
           binding!!.tvLingqu.isEnabled = true
           binding!!.tvLingqu.background = getDrawable(R.mipmap.lib_sure)
       }else{
           binding!!.tvLingqu.isClickable = false
           binding!!.tvLingqu.isEnabled = false
           binding!!.tvLingqu.background = getDrawable(R.mipmap.person_lingqu)
       }
        binding!!.tvLingqu.setOnClickListener {
            mPresenter!!.lingqu(SPUtil.get(SPConfig.SESSION_ID,""),orderMsg.id.toString())
        }
    }

    fun setData() {
        RxBus.getDefault().send(1111)
        finish()

    }

    fun setpp() {
        binding!!.tvLingqu.isClickable = false
        binding!!.tvLingqu.isEnabled = false
        binding!!.tvLingqu.background = getDrawable(R.mipmap.person_lingqu)
        mPresenter!!.getOrderDetail(SPUtil.get(SPConfig.SESSION_ID,""),id!!)
        RxBus.getDefault().send(1111)
    }
}