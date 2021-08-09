package it.mbkj.personcenter.activity


import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.app.AlertDialog

import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener

import com.sskj.common.util.SPUtil
import com.sskj.common.util.ToastUtil
import it.mbkj.lib.base.ArouterAddress
import it.mbkj.lib.base.BaseActivity
import it.mbkj.lib.base.EditHintUtils
import it.mbkj.lib.http.HttpConfig
import it.mbkj.lib.http.SPConfig
import it.mbkj.lib.utils.ImageUtil
import it.mbkj.personcenter.R
import it.mbkj.personcenter.bean.Pledge
import it.mbkj.personcenter.databinding.ActivityPledgeHallBinding
import it.mbkj.personcenter.presenter.PledgeHallActivityPresenter
import kotlinx.android.synthetic.main.lib_alert_dialog.view.*
import net.idik.lib.slimadapter.SlimAdapter
import java.math.BigDecimal


@Route(path = ArouterAddress.PLEDGEHALLACTIVITY)
class PledgeHallActivity:BaseActivity<PledgeHallActivityPresenter>(){
    var  binding: ActivityPledgeHallBinding?=null
    var adapter: SlimAdapter?=null
    var dialog:AlertDialog?=null
    override val layoutId: Int
        get() = R.layout.activity_pledge_hall
    override val presenter: PledgeHallActivityPresenter
        get() = PledgeHallActivityPresenter()

    override fun initView() {
        binding = getViewDataBinding<ActivityPledgeHallBinding>()
        binding!!.include.rightTv.text="我的订单"
        setTitle("质押大厅")
        binding!!.rv.layoutManager = LinearLayoutManager(this)
        adapter = SlimAdapter.create().register<Pledge>(R.layout.activity_pledge_hall_item){
                data, injector ->
                    injector.text(R.id.tv_title,data.pledge_name)
                        .text(R.id.tv_num,data.price+" LBS")
                        .text(R.id.tv_qixian,data.all_time+" 天")
                        .text(R.id.tv_baifen,BigDecimal(data.income).divide(BigDecimal(data.price),BigDecimal.ROUND_DOWN).setScale(4).multiply(BigDecimal(100)).stripTrailingZeros().toPlainString()+"%")
                        .text(R.id.tv_bizhi,(data.sum-data.have_out).toString()+"/"+data.sum.toString())
                        .clicked(R.id.tv_sure, View.OnClickListener {
                            var view:View = LayoutInflater.from(this).inflate(R.layout.lib_alert_dialog, null)
                            view.et_pwd.hint = EditHintUtils.setHintSizeAndContent("请输入二级密码",12,true)

                            view.tv_sure.setOnClickListener(View.OnClickListener {
                                mPresenter!!.submit(SPUtil.get(SPConfig.SESSION_ID,""),data.id,view.et_pwd.text.toString())

                                dialog!!.dismiss()// ToastUtil.showShort("已经确认")
                            })
                            dialog = AlertDialog.Builder(this).setView(view).create()
                            dialog!!.window!!.setGravity(Gravity.BOTTOM)
                            var prama = dialog!!.window!!.attributes
                            prama.y=200
                            dialog!!.window!!.attributes=prama
                            dialog!!.show()
                            //mPresenter!!.submit(SPUtil.get(SPConfig.SESSION_ID,""),data.id)
                        })
                      var seekbar= injector.findViewById<SeekBar>(R.id.seekbar)
                        seekbar.max = data.sum
                        seekbar.progress =data.sum-data.have_out
        }
            .attachTo(binding!!.rv)

    }

    override fun initData() {
       // super.initData()
        mPresenter!!.getPledgeList(SPUtil.get(SPConfig.SESSION_ID,""))

        binding!!.smart.setOnRefreshListener(OnRefreshListener {
            mPresenter!!.getPledgeList(SPUtil.get(SPConfig.SESSION_ID,""))
            binding!!.smart.finishRefresh()
        })
       // binding!!.smart.setOnLoadMoreListener(OnLoadMoreListener { ToastUtil.showShort("我已经加载更多") })
       // SlimAdapter.create()
    }

    override fun initEvent() {
       binding!!.include.rightTv.setOnClickListener(View.OnClickListener { ARouter.getInstance().build(ArouterAddress.MYORDERACTIVITY).navigation() })
    }

    fun setUI(pledgeList: List<Pledge>) {
        adapter!!.updateData(pledgeList)
    }

}