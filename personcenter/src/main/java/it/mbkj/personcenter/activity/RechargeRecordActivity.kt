package it.mbkj.personcenter.activity

import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.sskj.common.util.SPUtil
import it.mbkj.lib.base.ArouterAddress
import it.mbkj.lib.base.BaseActivity
import it.mbkj.lib.http.SPConfig
import it.mbkj.personcenter.R
import it.mbkj.personcenter.bean.DataQ
import it.mbkj.personcenter.bean.Datad
import it.mbkj.personcenter.bean.NewList
import it.mbkj.personcenter.bean.TIBI
import it.mbkj.personcenter.databinding.ActivityCongRecordBinding
import it.mbkj.personcenter.databinding.ActivityMyCongBinding
import it.mbkj.personcenter.databinding.ActivityMyJijiaBinding
import it.mbkj.personcenter.presenter.MyJiJiaActivityPresenter
import it.mbkj.personcenter.presenter.RechargeActivityPresenter
import it.mbkj.personcenter.presenter.RechargeRecordActivityPresenter
import net.idik.lib.slimadapter.SlimAdapter
import java.text.SimpleDateFormat

@Route(path = ArouterAddress.RECHARGERECORDACTIVITY)
class RechargeRecordActivity:BaseActivity<RechargeRecordActivityPresenter>() {
    var binding:ActivityCongRecordBinding?=null
    var page:Int=1
    var slimAdapter: SlimAdapter?=null
    override val layoutId: Int
        get() = R.layout.activity_cong_record
    override val presenter: RechargeRecordActivityPresenter
        get() = RechargeRecordActivityPresenter()

    override fun initView() {
       binding=  getViewDataBinding<ActivityCongRecordBinding>()
        setTitle("充值记录")
        binding!!.rv.layoutManager = LinearLayoutManager(this)
        slimAdapter = SlimAdapter.create().register<Datad>(R.layout.activity_cong_record_item){
                data, injector ->
            injector.text(R.id.tv_cong_content,data.amount)
                .text(R.id.tv_fee_num_content,data.fee+" USDT")
                .text(R.id.tv_qishi_time_content, SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(data.create_time.toLong()*1000))

        }
            .attachTo(binding!!.rv)
       // super.initView()
    }

    override fun initData() {
       mPresenter!!.getList(SPUtil.get(SPConfig.SESSION_ID,""),page)
        binding!!.smart.setOnLoadMoreListener {
            mPresenter!!.getList(SPUtil.get(SPConfig.SESSION_ID,""),page++)
        }
    }
    var list1:MutableList<Datad> = ArrayList()
    fun setUI(data: NewList) {
        if(page==1){
            list1.clear()
            list1.addAll(data.data)
            slimAdapter!!.updateData(list1)
        }else if(page<=data.last_page){
            list1.addAll(data.data)
            slimAdapter!!.updateData(list1)
        }
        binding!!.smart.finishLoadMore()
       // slimAdapter!!.updateData(data.data)
    }
}