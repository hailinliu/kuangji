package it.mbkj.personcenter.activity

import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.sskj.common.util.SPUtil
import it.mbkj.lib.base.ArouterAddress
import it.mbkj.lib.base.BaseActivity
import it.mbkj.lib.http.HttpConfig
import it.mbkj.lib.http.SPConfig
import it.mbkj.lib.utils.ImageUtil
import it.mbkj.personcenter.R
import it.mbkj.personcenter.bean.DataQ
import it.mbkj.personcenter.bean.DataX1
import it.mbkj.personcenter.bean.Demo
import it.mbkj.personcenter.bean.FirstList
import it.mbkj.personcenter.databinding.ActivityShouyiRecordBinding
import it.mbkj.personcenter.presenter.ProfitRecordActivityPresenter
import net.idik.lib.slimadapter.SlimAdapter
import java.text.SimpleDateFormat

@Route(path = ArouterAddress.PROFITRECORDACTIVITY)
class ProfitRecordActivity:BaseActivity<ProfitRecordActivityPresenter>() {
    var adapter:SlimAdapter?=null
    var page:Int=1
    var binding:ActivityShouyiRecordBinding?=null
    override val layoutId: Int
        get() = R.layout.activity_shouyi_record
    override val presenter: ProfitRecordActivityPresenter
        get() = ProfitRecordActivityPresenter()

    override fun initView() {
        setTitle("收益记录")
      binding=  getViewDataBinding<ActivityShouyiRecordBinding>()
        binding!!.rv.layoutManager = LinearLayoutManager(this)
        adapter = SlimAdapter.create().register<DataX1>(R.layout.activity_shouyi_record_item){
                data, injector ->
            injector.text(R.id.tv_zhitui,data.type_msg)
                .text(R.id.tv_hua_content,data.sum+if(data.money_str.equals("score")) "USDT" else if(data.money_str.equals("money"))"LBS" else "")
                .text(R.id.tv_qishi_time_content,SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(data.create_time*1000))
        }
            .attachTo(binding!!.rv)
    }

    override fun initData() {
        mPresenter!!.getProfitList(SPUtil.get(SPConfig.SESSION_ID,""),page)
        binding!!.smart.setOnRefreshListener {
            page = 1
            mPresenter!!.getProfitList(SPUtil.get(SPConfig.SESSION_ID,""),page)
            binding!!.smart.finishRefresh()
        }
        binding!!.smart.setOnLoadMoreListener {
            mPresenter!!.getProfitList(SPUtil.get(SPConfig.SESSION_ID,""),page++)
        }

    }
    var list1:MutableList<DataX1> = ArrayList()
    fun setUI(list: FirstList) {
        if(page==1){
            list1.clear()
            list1.addAll(list.data)
            adapter!!.updateData(list1)
        }else if(page<=list.last_page){
            list1.addAll(list.data)
            adapter!!.updateData(list1)
        }
        binding!!.smart.finishLoadMore()

    }
}