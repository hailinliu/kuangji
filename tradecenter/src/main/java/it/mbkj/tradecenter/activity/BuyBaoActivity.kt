package it.mbkj.tradecenter.activity

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.sskj.common.util.SPUtil
import it.mbkj.lib.base.ArouterAddress
import it.mbkj.lib.base.BaseActivity
import it.mbkj.lib.base.EditHintUtils
import it.mbkj.lib.http.SPConfig
import it.mbkj.lib.utils.ImageUtil
import it.mbkj.tradecenter.R
import it.mbkj.tradecenter.bean.BuyList1
import it.mbkj.tradecenter.bean.DataX
import it.mbkj.tradecenter.bean.UserMsg
import it.mbkj.tradecenter.databinding.ActivityTradeCenterBinding
import it.mbkj.tradecenter.presenter.BuyBaoActivityPresenter
import net.idik.lib.slimadapter.SlimAdapter
import pl.droidsonroids.gif.GifImageView

@Route(path = ArouterAddress.BUYBAOACTIVITY)
class BuyBaoActivity:BaseActivity<BuyBaoActivityPresenter>() {
    var slimAdapter: SlimAdapter?=null
    var binding:ActivityTradeCenterBinding?=null
    var page:Int=1
    override val layoutId: Int
        get() = R.layout.activity_trade_center
    override val presenter: BuyBaoActivityPresenter
        get() = BuyBaoActivityPresenter()

    override fun initView() {
        setTitle("我要买入")
        binding = getViewDataBinding<ActivityTradeCenterBinding>()
        binding!!.etNum.setHint(EditHintUtils.setHintSizeAndContent("买入数量",12,true))
        binding!!.etPwd.setHint(EditHintUtils.setHintSizeAndContent("请输入二级密码",12,true))
        binding!!.tvSure.setOnClickListener {
            mPresenter!!.getBuy(SPUtil.get(SPConfig.SESSION_ID,""),binding!!.etNum.text.toString(),binding!!.etPwd.text.toString())
        }
        binding!!.rv.layoutManager = LinearLayoutManager(this)
        slimAdapter = SlimAdapter.create().register<DataX>(R.layout.qiugou_item){
                data, injector ->
            if(data.status==0){
                injector.visibility(R.id.tv_sell,View.VISIBLE)
            }else{
                injector.visibility(R.id.tv_sell,View.INVISIBLE)
            }
            injector.text(R.id.tv_pone,data.phone)
                .visibility(R.id.tv_pone,View.GONE)
                .text(R.id.tv_price,"单价:"+data.price+" USDT")
                .text(R.id.tv_zong,"总量:"+data.sum)
                .text(R.id.tv_sheng,"剩余数量:"+data.left_sum)
                .text(R.id.tv_sell,"撤销")
                .clicked(R.id.tv_sell, View.OnClickListener {
                    mPresenter!!.cancelGuaDan(SPUtil.get(SPConfig.SESSION_ID,""),data.id.toString())

                })
           var imageView= injector.findViewById<ImageView>(R.id.image)
            ImageUtil.setCircleImage(R.mipmap.trad_logo,imageView)
        }
            .attachTo(binding!!.rv)
    }

    override fun initData() {
        mPresenter!!.getList(SPUtil.get(SPConfig.SESSION_ID,""),page)
        mPresenter!!.getHomePage(SPUtil.get(SPConfig.SESSION_ID,""))
        binding!!.smart.setOnRefreshListener {
            page = 1
            mPresenter!!.getList(SPUtil.get(SPConfig.SESSION_ID,""),page)
            binding!!.smart.finishRefresh()
        }
        binding!!.smart.setOnLoadMoreListener {
            mPresenter!!.getList(SPUtil.get(SPConfig.SESSION_ID,""),page++)
        }
    }
    var list:MutableList<DataX> = ArrayList()
    fun setUI(buyList: BuyList1) {
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

    fun setData(userMsg: UserMsg) {
        binding!!.tvNum.text =userMsg.score+" USDT"
    }

    fun setp() {
        page=1
        mPresenter!!.getList(SPUtil.get(SPConfig.SESSION_ID,""),page)
    }

    fun setdata() {
        page=1
        mPresenter!!.getList(SPUtil.get(SPConfig.SESSION_ID,""),page)
    }
}