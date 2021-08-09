package it.mbkj.tradecenter.activity

import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.sskj.common.util.SPUtil
import it.mbkj.lib.base.ArouterAddress
import it.mbkj.lib.base.BaseActivity
import it.mbkj.lib.http.SPConfig
import it.mbkj.tradecenter.R
import it.mbkj.tradecenter.bean.BuyList2
import it.mbkj.tradecenter.bean.DataX1
import it.mbkj.tradecenter.databinding.ActivityTradeBuylistBinding
import it.mbkj.tradecenter.presenter.BuyListActivityPresenter
import net.idik.lib.slimadapter.SlimAdapter
import java.math.BigDecimal
import java.text.SimpleDateFormat

@Route(path = ArouterAddress.BUYLISTACTIVITY)
class BuyListActivity:BaseActivity<BuyListActivityPresenter>() {
    var page:Int=1
    var slimAdapter: SlimAdapter?=null
    var binding:ActivityTradeBuylistBinding?=null
    override val layoutId: Int
        get() = R.layout.activity_trade_buylist
    override val presenter: BuyListActivityPresenter
        get() = BuyListActivityPresenter()

    override fun initView() {
       // super.initView()
        setTitle("买入列表详情")
         binding=getViewDataBinding<ActivityTradeBuylistBinding>()
        binding!!.rv.layoutManager = LinearLayoutManager(this)
        slimAdapter = SlimAdapter.create().register<DataX1>(R.layout.sell_list_item){
                data, injector ->
            injector.text(R.id.tv_order,"订单号:"+data.order_num)
                .text(R.id.tv_buy,"买入人:"+data.buy_phone.substring(0,3)+"****"+data.buy_phone.substring(7,data.buy_phone.length))
                .text(R.id.tv_sell,"卖出人:"+data.sell_phone.substring(0,3)+"****"+data.sell_phone.substring(7,data.sell_phone.length))
                .text(R.id.tv_jiaoyi_num,"交易数量:"+data.sum)
                .text(R.id.tv_jiaoyi_price, "交易价格:"+data.price).text(R.id.tv_jiaoyi_zonge,"交易总额:"+BigDecimal(data.sum).multiply(BigDecimal(data.price)).setScale(2,BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString())
                .text(R.id.tv_jiaoyi_time,"交易时间:"+ SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(data.create_time.toLong()*1000))
        }
            .attachTo(binding!!.rv)
    }

    override fun initData() {
        // super.initData()
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
    var list:MutableList<DataX1> = ArrayList()
    fun setUI(buyList: BuyList2) {
        if(page==1){
            list.clear()
            list.addAll(buyList.data)
            slimAdapter!!.updateData(list)
        }else if(page<=buyList.last_page){
            list.addAll(buyList.data)
            slimAdapter!!.updateData(list)
        }
        binding!!.smart.finishLoadMore()
    }
}