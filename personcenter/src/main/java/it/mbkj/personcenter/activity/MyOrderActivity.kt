package it.mbkj.personcenter.activity

import android.app.AlertDialog
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.SeekBar
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import com.sskj.common.rxbus2.RxBus
import com.sskj.common.rxbus2.Subscribe
import com.sskj.common.rxbus2.ThreadMode
import com.sskj.common.util.SPUtil
import com.sskj.common.util.ToastUtil
import it.mbkj.lib.base.ArouterAddress
import it.mbkj.lib.base.BaseActivity
import it.mbkj.lib.http.SPConfig
import it.mbkj.personcenter.R
import it.mbkj.personcenter.bean.DataX
import it.mbkj.personcenter.bean.Pledge
import it.mbkj.personcenter.bean.PledgeOrderList
import it.mbkj.personcenter.databinding.ActivityMyOrderBinding
import it.mbkj.personcenter.presenter.MyOrderActivityPresenter
import kotlinx.android.synthetic.main.alert_dialog.view.*
import net.idik.lib.slimadapter.SlimAdapter
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.*

@Route(path = ArouterAddress.MYORDERACTIVITY)
class MyOrderActivity:BaseActivity<MyOrderActivityPresenter>() {
    var view:View? = null
    var dialog: androidx.appcompat.app.AlertDialog?=null
    private var binding: ActivityMyOrderBinding?=null
    var adapter: SlimAdapter?=null
    override val layoutId: Int
        get() = R.layout.activity_my_order
    override val presenter: MyOrderActivityPresenter
        get() = MyOrderActivityPresenter()

    override fun initView() {
        setTitle("我的订单")
        ARouter.getInstance().inject(this)
        RxBus.getDefault().register(this)
     binding =  getViewDataBinding<ActivityMyOrderBinding>()
       binding!!.rv.layoutManager = LinearLayoutManager(this)
        adapter = SlimAdapter.create().register<DataX>(R.layout.activity_my_order_item){
                data, injector ->
            var d = data.create_time*1000;
            injector.text(R.id.tv_title,data.pledge_name)
                .text(R.id.tv_num_tit,if(data.status==1)"质押中" else if(data.status==2)"已到期" else "已解锁")
                .background(R.id.tv_num_tit,if(data.status==1) getDrawable(R.mipmap.person_zhiya_bging) else if(data.status==2) getDrawable(R.mipmap.person_zhiya_bg_daoqi) else getDrawable(R.mipmap.person_zhiya_bged))
                .text(R.id.tv_time_content,SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(d))
                .text(R.id.tv_num_content,data.price+" LBS")
                .text(R.id.tv_qixian_content,(if((Calendar.getInstance().time.time/1000-data.create_time)/3600/24>=data.all_time.toDouble()) data.all_time else ((Calendar.getInstance().time.time/1000-data.create_time)/3600/24)).toString()+"天/")
                .visibility(R.id.tv_qixian_content,if(data.status==3) View.GONE else View.VISIBLE)
                .visibility(R.id.tv_qixian,if(data.status==3) View.GONE else View.VISIBLE)
                .text(R.id.tv_qixian_content1,data.all_time+"天")
                .visibility(R.id.tv_qixian_content1,if(data.status==3) View.GONE else View.VISIBLE)
                .clicked(R.id.ll_item, View.OnClickListener {
                    view = LayoutInflater.from(this).inflate(R.layout.alert_dialog1, null)
                        if(data.status==3){
                          //  ToastUtil.showShort("您已解锁，本金退回!")
                            dialog = androidx.appcompat.app.AlertDialog.Builder(this).setView(view).create()
                            //dialog!!.window.setBackgroundDrawable(getDrawable(R.mipmap.person_xiaoqing))
                            dialog!!.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                            dialog!!.window.clearFlags( WindowManager.LayoutParams.FLAG_DIM_BEHIND)
                            dialog!!.window.setLayout(20,20)
                            dialog!!.window.setDimAmount(0f)
                            dialog!!.show()
                        }else{
                            if(data.status==1){
                                ARouter.getInstance().build(ArouterAddress.ORDERDETAILACTIVITY).withString("id",data.id.toString()).navigation()
                            }

                        }


                })
        }
            .attachTo(binding!!.rv)
    }
        @Subscribe(threadMode = ThreadMode.MAIN,code=1111)
        fun getData(){
            mPresenter!!.getOrderList(SPUtil.get(SPConfig.SESSION_ID,""))
        }
    override fun initData() {
        binding!!.smart.setOnRefreshListener(OnRefreshListener
        {
           // ToastUtil.showShort("我已刷新")
            mPresenter!!.getOrderList(SPUtil.get(SPConfig.SESSION_ID,""))
            binding!!.smart.finishRefresh()
        })
       /* binding!!.smart.setOnLoadMoreListener(OnLoadMoreListener
        { ToastUtil.showShort("我已经加载")
        })*/
        mPresenter!!.getOrderList(SPUtil.get(SPConfig.SESSION_ID,""))
    }

    fun setUI(pledgeOrderList: PledgeOrderList) {
       // binding!!.smart.finishLoadMore()
       adapter!!.updateData(pledgeOrderList.data)
    }
}