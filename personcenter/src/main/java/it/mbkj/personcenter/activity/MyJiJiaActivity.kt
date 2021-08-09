package it.mbkj.personcenter.activity

import android.view.View
import android.widget.SeekBar
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.sskj.common.util.SPUtil
import it.mbkj.lib.base.ArouterAddress
import it.mbkj.lib.base.BaseActivity
import it.mbkj.lib.http.SPConfig
import it.mbkj.lib.utils.TimeFormatUtil
import it.mbkj.personcenter.R
import it.mbkj.personcenter.bean.DataT
import it.mbkj.personcenter.databinding.ActivityMyJijiaBinding
import it.mbkj.personcenter.presenter.MyJiJiaActivityPresenter
import net.idik.lib.slimadapter.SlimAdapter
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.*

@Route(path = ArouterAddress.MYJIJIAACTIVITY)
class MyJiJiaActivity:BaseActivity<MyJiJiaActivityPresenter>() {
    var binding:ActivityMyJijiaBinding?=null
    var adapter: SlimAdapter?=null
    override val layoutId: Int
        get() = R.layout.activity_my_jijia
    override val presenter: MyJiJiaActivityPresenter
        get() = MyJiJiaActivityPresenter()

    override fun initView() {
       binding=  getViewDataBinding<ActivityMyJijiaBinding>()
        setTitle("我的机甲")
       binding!!.rv.layoutManager=LinearLayoutManager(this)
        adapter = SlimAdapter.create().register<DataT>(R.layout.activity_jijia_item){
                data, injector ->
            var start  = data.begin_time.toLong()*1000
            var end = data.end_time.toLong()*1000
            injector.text(R.id.tv_jijia_name_content,data.name)
                .text(R.id.tv_jijia_price_content,data.price+" USDT")
                .text(R.id.tv_jijia_period_content,data.all_time.toString()+"天")
                .text(R.id.tv_jijia_profit_content,data.income)
               // .text(R.id.tv_jijia_suanli_content,data.machine_sum)
                .text(R.id.tv_jijia_open_time_content, SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(start))
                .text(R.id.tv_jijia_already_time_content,data.get_time.toString()+"天")
            if(data.status==2){
              injector.text(R.id.tv_jijia_jiesu,"机甲结束时间:")
                  .visibility(R.id.tv_jijia_jiesu,View.VISIBLE)
                  .visibility(R.id.tv_jijia_jiesu_content,View.VISIBLE)
                  .text(R.id.tv_jijia_yichiyou_content,((data.last_time.toLong()-data.create_time.toLong())/3600/24).toString()+"天")
                  .text(R.id.tv_jijia_jiesu_content,SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(data.last_time.toLong()*1000))
                  .image(R.id.image,R.mipmap.person_shixiao)
            }else if(data.status==1){
                injector.visibility(R.id.tv_jijia_jiesu,View.GONE)
                        .visibility(R.id.tv_jijia_jiesu_content,View.GONE)
                    .text(R.id.tv_jijia_yichiyou_content,((Calendar.getInstance().time.time/1000-data.create_time.toLong())/3600/24).toString()+"天")
            }
        }
            .attachTo(binding!!.rv)
    }

    override fun initData() {
       mPresenter!!.getJijJiaList(SPUtil.get(SPConfig.SESSION_ID,""))
    }
    fun setUI(data: List<DataT>) {
        adapter!!.updateData(data)
    }
}