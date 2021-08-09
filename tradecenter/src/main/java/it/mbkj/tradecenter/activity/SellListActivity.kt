package it.mbkj.tradecenter.activity

import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.sskj.common.util.SPUtil
import it.mbkj.lib.base.ArouterAddress
import it.mbkj.lib.base.BaseActivity
import it.mbkj.lib.http.SPConfig
import it.mbkj.tradecenter.R
import it.mbkj.tradecenter.bean.DataT
import it.mbkj.tradecenter.bean.SellList
import it.mbkj.tradecenter.databinding.ActivityTradeSelllistBinding
import it.mbkj.tradecenter.presenter.SellListActivityPresenter
import net.idik.lib.slimadapter.SlimAdapter
import java.math.BigDecimal
import java.text.SimpleDateFormat


@Route(path = ArouterAddress.SELLLISTACTIVITY)
class SellListActivity: BaseActivity<SellListActivityPresenter>() {
    var page:Int=1
    var slimAdapter: SlimAdapter?=null
    var binding: ActivityTradeSelllistBinding?=null
    override val layoutId: Int
        get() = R.layout.activity_trade_selllist
    override val presenter: SellListActivityPresenter
        get() = SellListActivityPresenter()

    override fun initView() {
        //super.initView()
        setTitle("卖出列表详情")
        binding= getViewDataBinding<ActivityTradeSelllistBinding>()
        binding!!.rv.layoutManager = LinearLayoutManager(this)
        slimAdapter = SlimAdapter.create().register<DataT>(R.layout.sell_list_item){
                data, injector ->
            injector.text(R.id.tv_order,"订单号:"+data.order_num)
                .text(R.id.tv_buy,"买入人:"+data.buy_phone.substring(0,3)+"****"+data.buy_phone.substring(7,data.buy_phone.length))
                .text(R.id.tv_sell,"卖出人:"+data.sell_phone.substring(0,3)+"****"+data.sell_phone.substring(7,data.sell_phone.length))
                .text(R.id.tv_jiaoyi_num,"交易数量:"+data.sum)
                .text(R.id.tv_jiaoyi_price, "交易价格:"+data.price)
                .text(R.id.tv_jiaoyi_zonge,"交易总额:"+ BigDecimal(data.sum).multiply(BigDecimal(data.price)).setScale(2,
                    BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString())
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
    var list:MutableList<DataT> = ArrayList()
    fun setUI(buyList: SellList) {
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