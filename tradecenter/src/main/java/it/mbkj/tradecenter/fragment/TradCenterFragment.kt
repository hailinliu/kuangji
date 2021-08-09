package it.mbkj.tradecenter.fragment

import android.graphics.Color
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.*
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.sskj.common.util.SPUtil
import it.mbkj.lib.base.ArouterAddress
import it.mbkj.lib.base.BaseFragment
import it.mbkj.lib.base.EditHintUtils
import it.mbkj.lib.base.LogUtils
import it.mbkj.lib.http.SPConfig
import it.mbkj.lib.utils.ImageUtil
import it.mbkj.lib.utils.Utils
import it.mbkj.tradecenter.R
import it.mbkj.tradecenter.bean.*
import it.mbkj.tradecenter.databinding.FragmentTradcenterBinding
import it.mbkj.tradecenter.presenter.TradCenterFragmentPresenter
import kotlinx.android.synthetic.main.trad_alert_dialog.view.*
import net.idik.lib.slimadapter.SlimAdapter
import pl.droidsonroids.gif.GifImageView


@Route(path = ArouterAddress.TRADCENTERFRAGMENT)
class TradCenterFragment:BaseFragment<TradCenterFragmentPresenter>() {
    var slimAdapter: SlimAdapter?=null
    var binding:FragmentTradcenterBinding?=null
    var dialog:AlertDialog?=null
    var page:Int=1
    var flag:Int=0
        //  var lineChart: LineChart?=null
    var xAxis: XAxis?=null               //X轴
    var leftYAxis: YAxis?=null              //左侧Y轴
    var rightYaxis:YAxis?=null              //右侧Y轴
    var legend: Legend?=null                //图例
    //var limitLine: LimitLine?=null           //限制线
    override val layoutId: Int
        get() = R.layout.fragment_tradcenter
    override val presenter: TradCenterFragmentPresenter
        get() = TradCenterFragmentPresenter()

    override fun isEmpty(textView: TextView?): Boolean {
        return false
    }
    override fun initView() {
        setTitle("贸易中心")
       binding=  getViewDataBinding<FragmentTradcenterBinding>()
        binding!!.include.ivBack.visibility= View.INVISIBLE
        binding!!.etShuru.setHint(EditHintUtils.setHintSizeAndContent("请输入四位尾号",12,true))
        binding!!.tvBuy.setOnClickListener {
            if(Utils.isFastClick()){
                ARouter.getInstance().build(ArouterAddress.BUYBAOACTIVITY).navigation()
            }

        }
        binding!!.tvBuyList.setOnClickListener {
            if(Utils.isFastClick()){
                ARouter.getInstance().build(ArouterAddress.BUYLISTACTIVITY).navigation()
            }

        }
        binding!!.tvSellList.setOnClickListener {
            if(Utils.isFastClick()){
                ARouter.getInstance().build(ArouterAddress.SELLLISTACTIVITY).navigation()
            }

        }
        binding!!.rv.layoutManager = LinearLayoutManager(activity)
        binding!!.imageSolo.setOnClickListener {
            page = 1
            mPresenter!!.getList(SPUtil.get(SPConfig.SESSION_ID,""),page,binding!!.etShuru.text.toString())
        }
        slimAdapter = SlimAdapter.create().register<DataS>(R.layout.qiugou_item){
                data, injector ->
            injector.text(R.id.tv_pone,data.phone.substring(0,3)+"****"+data.phone.substring(7,data.phone.length))
                .text(R.id.tv_price,"单价:"+data.price+" USDT")
                .text(R.id.tv_zong,"总量:"+data.sum)
                .text(R.id.tv_sheng,"剩余数量:"+data.left_sum)
                .clicked(R.id.tv_sell, View.OnClickListener {
                    var view:View = LayoutInflater.from(activity).inflate(R.layout.trad_alert_dialog, null)
                    view.tv_cancel.setOnClickListener(View.OnClickListener {
                        dialog!!.dismiss()
                    })
                    view.et_num.hint = EditHintUtils.setHintSizeAndContent("卖出数量",12,true)
                    view.et_pwd.hint = EditHintUtils.setHintSizeAndContent("请输入二级密码",12,true)
                    view.tv_keyong.text = "可用币LBS:"+keyong
                    view.tv_current.text = "当前价格:"+data.price+" USDT"
                    view.tv_shengyu.text = "剩余数量:"+data.left_sum
                    view.tv_sure.setOnClickListener(View.OnClickListener {
                    mPresenter!!.sell(SPUtil.get(SPConfig.SESSION_ID,""),data.id,view.et_num.text.toString(),view.et_pwd.text.toString())

                        dialog!!.dismiss()// ToastUtil.showShort("已经确认")
                    })
                     dialog = AlertDialog.Builder(requireActivity()).setView(view).create()
                    dialog!!.window!!.setGravity(Gravity.BOTTOM)
                    var prama = dialog!!.window!!.attributes
                    prama.y=200
                    dialog!!.window!!.attributes=prama
                    dialog!!.show()
                })
            var image = injector.findViewById<ImageView>(R.id.image)
                ImageUtil.setCircleImage(R.mipmap.trad_logo,image)
               // image.setImageResource(R.mipmap.trad_jijia1)
         /*   if(data.name.contains("一级")){
                image.setImageResource(R.mipmap.trad_jijia1)
            }else if(data.name.contains("二级")){
                image.setImageResource(R.mipmap.trad_jijia2)
            }else if(data.name.contains("三级")){
                image.setImageResource(R.mipmap.trad_jijia3)
            }else if(data.name.contains("四级")){
                image.setImageResource(R.mipmap.trad_jijia4)
            }*/
        }
            .attachTo(binding!!.rv)


    }

   public override fun initData() {
        mPresenter!!.getList(SPUtil.get(SPConfig.SESSION_ID,""),page,"")
        mPresenter!!.getK(SPUtil.get(SPConfig.SESSION_ID,""))
        mPresenter!!.getHomePage(SPUtil.get(SPConfig.SESSION_ID,""))
    }
    override fun initEvent() {
        binding!!.smart.setOnRefreshListener {
            page = 1
            mPresenter!!.getList(SPUtil.get(SPConfig.SESSION_ID,""),page,"")
            binding!!.smart.finishRefresh()
        }
        binding!!.smart.setOnLoadMoreListener {
            mPresenter!!.getList(SPUtil.get(SPConfig.SESSION_ID,""),page++,"")
        }
    }
    var list:MutableList<DataS> = ArrayList()
    fun setUI(buyList: BuyList) {
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
   var newlist = ArrayList<IncomeBean>()

    fun setK(bean: KChartBean) {
        newlist.clear()
        initChart(binding!!.linechart)
      for((index,e) in bean.data.k_line_time.withIndex()){
          var incomeBean = IncomeBean(bean.data.k_line_time.get(index),bean.data.k_line_value.get(index))
            newlist.add(incomeBean)
      }
        showLinechart(newlist,"今日价格:"+bean.price, Color.CYAN)
    }
    /**
     * 初始化图表
     */
    fun initChart(lineChart:LineChart) {
        /***图表设置***/
        //是否展示网格线
        lineChart.setDrawGridBackground(false);
        //是否显示边界
        lineChart.setDrawBorders(true);
        //是否可以拖动
        lineChart.setDragEnabled(false);
        //是否有触摸事件
        lineChart.setTouchEnabled(true);
        //设置XY轴动画效果
        lineChart.animateY(2500);
        lineChart.animateX(1500);

        /***XY轴的设置***/
        xAxis = lineChart.getXAxis();
        leftYAxis = lineChart.getAxisLeft();
        rightYaxis = lineChart.getAxisRight();
        //X轴设置显示位置在底部
        xAxis!!.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis!!.setAxisMinimum(0f);
        xAxis!!.setGranularity(1f);
        //保证Y轴从0开始，不然会上移一点
        leftYAxis!!.setAxisMinimum(0f);
        rightYaxis!!.setAxisMinimum(0f);

        /***折线图例 标签 设置***/
        legend = lineChart.getLegend();
        //设置显示类型，LINE CIRCLE SQUARE EMPTY 等等 多种方式，查看LegendForm 即可
        legend!!.setForm(Legend.LegendForm.LINE);
        legend!!.setTextSize(12f);
        //显示位置 左下方
        legend!!.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP)
        legend!!.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT)
        legend!!.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend!!.textColor = Color.WHITE
        //是否绘制在图表里面
        legend!!.setDrawInside(false);
    }
    /**
     * 曲线初始化设置 一个LineDataSet 代表一条曲线
     *
     * @param lineDataSet 线条
     * @param color       线条颜色
     * @param mode
     */
    fun initLineDataSet(lineDataSet: LineDataSet, color:Int , mode:LineDataSet.Mode ) {
        lineDataSet.setColor(color);
        lineDataSet.setCircleColor(color);
        lineDataSet.setLineWidth(1f);
        lineDataSet.valueTextColor = Color.WHITE
        lineDataSet.setCircleRadius(3f);
        //设置曲线值的圆点是实心还是空心
        lineDataSet.setDrawCircleHole(false);
        lineDataSet.setValueTextSize(10f);
        //设置折线图填充
        lineDataSet.setDrawFilled(true);
        lineDataSet.setFormLineWidth(1f);
        lineDataSet.setFormSize(15f);
        if (mode == null) {
            //设置曲线展示为圆滑曲线（如果不设置则默认折线）
            lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        } else {
            lineDataSet.setMode(mode);
        }
    }
    /**
     * 展示曲线
     *
     * @param dataList 数据集合
     * @param name     曲线名称
     * @param color    曲线颜色
     */
fun showLinechart(datalist:List<IncomeBean>,name:String,color:Int){
        var entries = ArrayList<Entry>()
        for((index,e) in datalist.withIndex()){
        var data= datalist.get(index)
                var entry=  Entry(index.toFloat(),data.value.toFloat())
                entries.add(entry)


        }
      var set =  LineDataSet(entries,name)
        initLineDataSet(set,color,LineDataSet.Mode.LINEAR)
      var data =  LineData(set)
       binding!!.linechart.data = data
        binding!!.linechart.setDrawBorders(false)
        binding!!.linechart.extraBottomOffset = 18f
        var description = Description()
//        description.setText("需要展示的内容");
        description.setEnabled(false)
        binding!!.linechart.setDescription(description)
        xAxis!!.setDrawGridLines(false)
        xAxis!!.labelRotationAngle = 60.0f
        xAxis!!.textColor = Color.WHITE
        xAxis!!.setValueFormatter(object : IAxisValueFormatter {
            override fun getFormattedValue(value: Float, axis: AxisBase?): String {
                return datalist.get(value.toInt()).tradeDate
            }

        }
         )
        rightYaxis!!.setDrawGridLines(false)
        leftYAxis!!.setDrawGridLines(true)
        leftYAxis!!.enableGridDashedLine(10f, 10f, 0f)
        leftYAxis!!.textColor = Color.WHITE
        rightYaxis!!.setEnabled(false)
    }

    fun setData() {
        page=1
        mPresenter!!.getList(SPUtil.get(SPConfig.SESSION_ID,""),page,"")
    }
var keyong =""
    fun setData1(data: HomePageBean) {
        keyong = data.user_msg.money
    }


}


