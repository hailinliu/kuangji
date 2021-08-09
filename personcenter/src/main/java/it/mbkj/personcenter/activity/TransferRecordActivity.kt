package it.mbkj.personcenter.activity

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.sskj.common.util.SPUtil
import it.mbkj.lib.base.ArouterAddress
import it.mbkj.lib.base.BaseActivity
import it.mbkj.lib.http.SPConfig
import it.mbkj.personcenter.R
import it.mbkj.personcenter.bean.Bean
import it.mbkj.personcenter.bean.DataQ
import it.mbkj.personcenter.databinding.ActivityTransferRecordBinding
import it.mbkj.personcenter.presenter.TransferRecordActivityPresenter
import net.idik.lib.slimadapter.SlimAdapter
import java.text.SimpleDateFormat

@Route(path = ArouterAddress.TRANSFERRECORDACTIVITY)
class TransferRecordActivity:BaseActivity<TransferRecordActivityPresenter>() {
    var slimAdapter: SlimAdapter?=null
    var page:Int=1
    var binding:ActivityTransferRecordBinding?=null
    var list1:MutableList<DataQ> = ArrayList()
    fun setUI(list: Bean) {
        if(page==1){
            list1.clear()
            list1.addAll(list.data)
            slimAdapter!!.updateData(list1)
        }else if(page<=list.last_page){
            list1.addAll(list.data)
            slimAdapter!!.updateData(list1)
        }
        binding!!.smart.finishLoadMore()
    }

    override val layoutId: Int
        get() = R.layout.activity_transfer_record
    override val presenter: TransferRecordActivityPresenter
        get() = TransferRecordActivityPresenter()

    override fun initView() {
       // super.initView()
        setTitle("划转记录")
       binding=  getViewDataBinding<ActivityTransferRecordBinding>()
        binding!!.rv.layoutManager = LinearLayoutManager(this)
        slimAdapter = SlimAdapter.create().register<DataQ>(R.layout.activity_tranfer_record_item){
                data, injector ->
            injector.text(R.id.tv_cong,data.type_msg)
                .text(R.id.tv_huaduixiang_content,data.sum+if(data.money_str == "money") "LBS" else "USDT")
                .text(R.id.tv_qishi_time_content,SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(data.create_time.toLong()*1000))

        }
            .attachTo(binding!!.rv)
    }

    override fun initData() {
        //super.initData()
        mPresenter!!.getList(SPUtil.get(SPConfig.SESSION_ID,""),page)
    }
    override fun initEvent() {
        binding!!.smart.setOnRefreshListener {
            page = 1
            mPresenter!!.getList(SPUtil.get(SPConfig.SESSION_ID,""),page)
            binding!!.smart.finishRefresh()
        }
        binding!!.smart.setOnLoadMoreListener {
            mPresenter!!.getList(SPUtil.get(SPConfig.SESSION_ID,""),page++)
        }
    }
}