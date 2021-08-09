package it.mbkj.tradecenter.fragment

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.sskj.common.util.SPUtil
import it.mbkj.lib.base.ArouterAddress
import it.mbkj.lib.base.BaseFragment
import it.mbkj.lib.base.EditHintUtils
import it.mbkj.lib.http.HttpConfig
import it.mbkj.lib.http.SPConfig
import it.mbkj.lib.utils.ImageUtil
import it.mbkj.tradecenter.presenter.XingQiuFragmentPresenter
import it.mbkj.xingqiu.R
import it.mbkj.xingqiu.bean.DataX
import it.mbkj.xingqiu.bean.MachineList
import it.mbkj.xingqiu.databinding.FragmentXingqiuBinding
import kotlinx.android.synthetic.main.lib_alert_dialog.view.*
import kotlinx.android.synthetic.main.xingqiu_head.view.*


import net.idik.lib.slimadapter.SlimAdapter
import pl.droidsonroids.gif.GifImageView


@Route(path = ArouterAddress.XINGQIUFRAGMENT)
class XingQiuFragment:BaseFragment<XingQiuFragmentPresenter>() {
    var adapter: SlimAdapter?=null
    var binding:FragmentXingqiuBinding?=null
    var dialog:AlertDialog?=null
    override val layoutId: Int
        get() = R.layout.fragment_xingqiu
    override val presenter: XingQiuFragmentPresenter
        get() = XingQiuFragmentPresenter()

    override fun isEmpty(textView: TextView?): Boolean {
        return false
    }

    override fun initView() {
        setTitle("机甲大厅")
        binding  = getViewDataBinding<FragmentXingqiuBinding>()
        binding!!.include.ivBack.visibility= View.INVISIBLE
        binding!!.rv.layoutManager = LinearLayoutManager(activity)
        adapter = SlimAdapter.create().register<DataX>(R.layout.fragment_xingqiu_item){
                data, injector ->
                injector.text(R.id.tv_name_content,data.name)
                    .text(R.id.tv_price_content,data.price+" USDT")
                    .text(R.id.tv_period_content,data.all_time.toString()+"天")
                    .text(R.id.tv_profit_content,data.income+" USDT")
                        .text(R.id.tv_jijiasuanli_content,data.machine_sum)
                        .clicked(R.id.tv_buy, View.OnClickListener {
                        var view:View = LayoutInflater.from(activity).inflate(R.layout.lib_alert_dialog, null)
                        view.et_pwd.hint = EditHintUtils.setHintSizeAndContent("请输入二级密码",12,true)

                        view.tv_sure.setOnClickListener(View.OnClickListener {
                            mPresenter!!.buyJiJia(SPUtil.get(SPConfig.SESSION_ID,""),data.id,view.et_pwd.text.toString())

                            dialog!!.dismiss()// ToastUtil.showShort("已经确认")
                        })
                        dialog = AlertDialog.Builder(requireActivity()).setView(view).create()
                        dialog!!.window!!.setGravity(Gravity.BOTTOM)
                        var prama = dialog!!.window!!.attributes
                        prama.y=200
                        dialog!!.window!!.attributes=prama
                        dialog!!.show()
                      //
                    })
               var image= injector.findViewById<GifImageView>(R.id.image)
            if(data.name.contains("一级")){
                image.setImageResource(R.mipmap.trad_jijia1)
            }else if(data.name.contains("二级")){
                image.setImageResource(R.mipmap.trad_jijia2)
            }else if(data.name.contains("三级")){
                image.setImageResource(R.mipmap.trad_jijia3)
            }else if(data.name.contains("四级")){
                image.setImageResource(R.mipmap.trad_jijia4)
            }
               // ImageUtil.setImage(HttpConfig.BASE_IMG_URL+data.img_url,image)
           /* injector.text(R.id.tv_title,data.title)
                .text(R.id.tv_time, SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date(data.create_time*1000)))
                .clicked(R.id.ll_item, View.OnClickListener {
                    ARouter.getInstance().build(ArouterAddress.GONGGAODETAILACTIVITY).withString("id",data.id.toString()).navigation()
                })*/

        }.attachTo(binding!!.rv)
    }

  public override fun initData() {
        mPresenter!!.getList(SPUtil.get(SPConfig.SESSION_ID,""))
    }

    override fun initEvent() {
        //super.initEvent()
        binding!!.smart.setOnRefreshListener {
            mPresenter!!.getList(SPUtil.get(SPConfig.SESSION_ID,""))
            it.finishRefresh(2000)
        }
    }
    fun setUI(data: List<DataX>) {
        adapter!!.updateData(data)
    }


}