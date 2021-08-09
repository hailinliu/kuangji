package it.mbkj.personcenter.activity

import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.sskj.common.util.SPUtil
import it.mbkj.lib.base.ArouterAddress
import it.mbkj.lib.base.BaseActivity
import it.mbkj.lib.http.SPConfig
import it.mbkj.personcenter.R
import it.mbkj.personcenter.bean.DataQ
import it.mbkj.personcenter.bean.TIBI
import it.mbkj.personcenter.bean.TiBiRecordBean
import it.mbkj.personcenter.databinding.ActivityTiRecordBinding
import it.mbkj.personcenter.databinding.ActivityTiRecordItemBinding
import it.mbkj.personcenter.presenter.WithDrawRecordActivityPresenter
import net.idik.lib.slimadapter.SlimAdapter
import java.text.SimpleDateFormat

@Route(path = ArouterAddress.WITHDRAWRECORDACTIVITY)
class WithDrawRecordActivity:BaseActivity<WithDrawRecordActivityPresenter>() {
  var binding: ActivityTiRecordBinding?=null
    var slimAdapter: SlimAdapter?=null
    var page:Int=1
    var list1:MutableList<TIBI> = ArrayList()
   /* fun setUI(list: List<TIBI>) {
        if(page==1){
            list1.clear()
            list1.addAll(list)
            slimAdapter!!.updateData(list1)
        }else if(page<=list.last_page){
            list1.addAll(list)
            slimAdapter!!.updateData(list1)
        }
        binding!!.smart.finishLoadMore()
    }*/

    override val layoutId: Int
        get() = R.layout.activity_ti_record
    override val presenter: WithDrawRecordActivityPresenter
        get() = WithDrawRecordActivityPresenter()

    override fun initView() {
        binding= getViewDataBinding<ActivityTiRecordBinding>()
        setTitle("提币记录")
        binding!!.rv.layoutManager = LinearLayoutManager(this)
        slimAdapter = SlimAdapter.create().register<TIBI>(R.layout.activity_ti_record_item){
                data, injector ->
            injector.text(R.id.tv_status,if(data.transfer_status==-1)"(失败)" else if(data.transfer_status==0)"(审核中)" else if(data.transfer_status==1)"(审核成功)" else "")
                .text(R.id.tv_cong_content,data.amount.toString()+data.coin_symbol)
                .text(R.id.tv_fee_num_content,data.fee.toString()+" USDT")
                .text(R.id.tv_qishi_time_content, SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(data.create_time.toLong()*1000))

        }
            .attachTo(binding!!.rv)
    }

    override fun initData() {
        mPresenter!!.getList(SPUtil.get(SPConfig.SESSION_ID,""),page)
    }
    override fun initEvent() {
        binding!!.smart.setOnLoadMoreListener {
            mPresenter!!.getList(SPUtil.get(SPConfig.SESSION_ID,""),page++)
        }
    }

    fun setUI(data: TiBiRecordBean) {
        if(page==1){
            list1.clear()
            list1.addAll(data.list)
            slimAdapter!!.updateData(list1)
        }else if(page<=data.last_page){
            list1.addAll(data.list)
            slimAdapter!!.updateData(list1)
        }
        binding!!.smart.finishLoadMore()
    }
}