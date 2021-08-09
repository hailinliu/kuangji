package it.mbkj.personcenter.activity

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.SeekBar
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.sskj.common.util.SPUtil
import it.mbkj.lib.base.ArouterAddress
import it.mbkj.lib.base.BaseActivity
import it.mbkj.lib.base.EditHintUtils
import it.mbkj.lib.http.SPConfig
import it.mbkj.personcenter.R
import it.mbkj.personcenter.bean.Demo1
import it.mbkj.personcenter.bean.Pledge
import it.mbkj.personcenter.bean.QuesonBean
import it.mbkj.personcenter.databinding.ActivityContactUsBinding
import it.mbkj.personcenter.presenter.ConactUsActivityPresenter
import net.idik.lib.slimadapter.SlimAdapter
import java.math.BigDecimal
import java.text.SimpleDateFormat

@Route(path = ArouterAddress.CONACTUSACTIVITY)
class ConactUsActivity:BaseActivity<ConactUsActivityPresenter>() {
    var binding:ActivityContactUsBinding?=null
    var adapter: SlimAdapter?=null
    var page:Int =1
    override val layoutId: Int
        get() = R.layout.activity_contact_us
    override val presenter: ConactUsActivityPresenter
        get() = ConactUsActivityPresenter()

    override fun initView() {
       binding=  getViewDataBinding<ActivityContactUsBinding>()
        setTitle("联系我们")
        binding!!.etContent.setHint(EditHintUtils.setHintSizeAndContent("请输入您要反馈的问题",14,true))
        binding!!.rv.layoutManager = LinearLayoutManager(this)
        adapter = SlimAdapter.create().register<Demo1>(R.layout.activity_question_item){
                data, injector ->
            var flag = false
            injector.text(R.id.tv_time, SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(data.question_time.toLong()*1000))
                .text(R.id.tv_quesion,"问题:"+if(data.question.isNullOrEmpty()) "" else data.question)
                .text(R.id.tv_huifu,"回复:"+if(data.answer.isNullOrEmpty()) "" else data.answer)
                .clicked(R.id.ll_title, View.OnClickListener {
                    var tt=injector.findViewById<LinearLayout>(R.id.ll_tt)
                    var image=injector.findViewById<ImageView>(R.id.image)
                        if(flag){
                            tt.visibility = View.GONE
                            image.setImageResource(R.mipmap.person_you)
                        }else{
                            tt.visibility = View.VISIBLE
                            image.setImageResource(R.mipmap.person_xia)
                        }
                    flag = !flag
                })

        }
            .attachTo(binding!!.rv)
    }

    override fun initData() {
       mPresenter!!.getRecord(SPUtil.get(SPConfig.SESSION_ID,""),page)
    }
    override fun initEvent() {
        binding!!.tvSubmit.setOnClickListener {
            mPresenter!!.submit(SPUtil.get(SPConfig.SESSION_ID,""),binding!!.etContent.text.toString())
        }
        binding!!.smart.setOnRefreshListener {
            page = 1
            mPresenter!!.getRecord(SPUtil.get(SPConfig.SESSION_ID,""),page)
            binding!!.smart.finishRefresh()
        }
        binding!!.smart.setOnLoadMoreListener {
            mPresenter!!.getRecord(SPUtil.get(SPConfig.SESSION_ID,""),page++)
        }
    }

    fun setData() {
        page=1
        mPresenter!!.getRecord(SPUtil.get(SPConfig.SESSION_ID,""),page)
    }
    var list1 = ArrayList<Demo1>()
    fun setUI(data: QuesonBean) {
        if(page==1){
            list1.clear()
            list1.addAll(data.list)
            adapter!!.updateData(list1)
        }else if(page<=2){
            list1.addAll(data.list)
            adapter!!.updateData(list1)
        }
        binding!!.smart.finishLoadMore()
    }
}