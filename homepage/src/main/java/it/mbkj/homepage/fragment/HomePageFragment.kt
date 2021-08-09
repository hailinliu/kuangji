package it.mbkj.homepage.fragment

import android.view.View
import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.sskj.common.rxbus2.RxBus
import com.sskj.common.rxbus2.Subscribe
import com.sskj.common.rxbus2.ThreadMode
import com.sskj.common.util.SPUtil
import com.sskj.common.util.ToastUtil
import it.mbkj.homepage.R
import it.mbkj.homepage.bean.HomePageBean
import it.mbkj.homepage.bean.NoticeBean
import it.mbkj.homepage.databinding.HomepageNewBinding
import it.mbkj.homepage.presenter.HomePageFragmentPresenter
import it.mbkj.lib.base.ArouterAddress
import it.mbkj.lib.base.BaseFragment
import it.mbkj.lib.http.HttpConfig
import it.mbkj.lib.http.SPConfig
import it.mbkj.lib.utils.ImageUtil
import it.mbkj.lib.utils.Utils
import kotlinx.android.synthetic.main.homepage_head.view.*
import kotlinx.android.synthetic.main.homepage_new.*
import java.util.logging.Handler

@Route(path = ArouterAddress.HOMEPAGEFRAGMENT)
class HomePageFragment :BaseFragment<HomePageFragmentPresenter>(){
    @JvmField
    @Autowired
    var sessionId:String?=null

    @JvmField
    @Autowired
    var flag:Int?=null
    var binding: HomepageNewBinding?=null
    override val layoutId: Int
        get() = R.layout.homepage_new
    override val presenter: HomePageFragmentPresenter
        get() = HomePageFragmentPresenter()

    override fun isEmpty(textView: TextView?): Boolean {
        return false
    }

  public override fun initData() {
        //super.initData()
        ARouter.getInstance().inject(this)
      if(sessionId.isNullOrEmpty()){
          mPresenter!!.getHomePage(SPUtil.get(SPConfig.SESSION_ID,""))
          mPresenter!!.getNotice(SPUtil.get(SPConfig.SESSION_ID,""))
      }else{
          mPresenter!!.getHomePage(sessionId!!)
          mPresenter!!.getNotice(sessionId!!)
      }

    }

    override fun initView() {
        RxBus.getDefault().register(this)
     binding =  getViewDataBinding<HomepageNewBinding>()
        setTitle("宝石星球")
      binding!!.include.ivBack.visibility = View.INVISIBLE
    }
   /* fun setUI(userMsg: UserMsg) {
        ImageUtil.setImage(HttpConfig.BASE_IMG_URL+userMsg.level_msg.img_str,binding!!.image)
        binding!!.userMsg = userMsg
       *//* binding!!.tvName.text = userMsg.level_msg.name
        binding!!.tvPhone.text = userMsg.phone
        binding!!.tvLbs.text = "LBS: "+userMsg.money
        binding!!.tvUsd.text = "USDT: "+userMsg.score*//*

    }*/

    override fun initEvent() {
       binding!!.tvNotice.setOnClickListener {
                if(Utils.isFastClick()){
                    ARouter.getInstance().build(ArouterAddress.GONGGAOACTIVITY).withSerializable("mybean",bean).navigation()
                }



       }
        binding!!.image.setOnClickListener {
            if(Utils.isFastClick()){
                ARouter.getInstance().build(ArouterAddress.ZICHANXINXIACTIVITY).navigation()
            }


        }
    }
    var bean:NoticeBean?=null
    fun setother(bean: NoticeBean?) {
        if(bean!!.data.notice_list.isNotEmpty()){
            this.bean = bean
          var st=  bean.data.notice_list[0].introduce
            tvNotice.setText(st)
            //binding!!.tvNotice.setText(st)
        }

    }
    @Subscribe(threadMode = ThreadMode.MAIN,code=1101)
    fun getRefresh(){
        if(sessionId.isNullOrEmpty()){
            mPresenter!!.getHomePage(SPUtil.get(SPConfig.SESSION_ID,""))
        }else{
            mPresenter!!.getHomePage(sessionId!!)
        }
    }
    fun setUI(data: HomePageBean) {
        ImageUtil.setCircleImage(HttpConfig.BASE_IMG_URL+data.user_msg.head_img,binding!!.image)
        binding!!.userMsg = data.user_msg
  /*      if(data.machine_list.get(0).can_in==0){
            view1.isClickable = false
            view1.isEnabled = false
        }else{
            view1.isClickable = true
            view1.isEnabled = true
        }
        if(data.machine_list.get(1).can_in==0){
            view2.isClickable = false
            view2.isEnabled = false
        }else{
            view2.isClickable = true
            view2.isEnabled = true
        }
        if(data.machine_list.get(2).can_in==0){
            view3.isClickable = false
            view3.isEnabled = false
        }else{
            view3.isClickable = true
            view3.isEnabled = true
        }
        if(data.machine_list.get(3).can_in==0){
            view4.isClickable = false
            view4.isEnabled = false
        }else{
            view4.isClickable = true
            view4.isEnabled = true
        }*/
        view1.setOnClickListener {
            if(Utils.isFastClick()){
                if(data.machine_list.get(0).can_in==0){
                    ToastUtil.showShort("请购买机甲")
                }else{
                    ARouter.getInstance().build(ArouterAddress.NEWACTIVITY).withString("can_get",data.machine_list.get(0).can_get.toString()).withString("name",data.machine_list.get(0).name).withString("id",data.machine_list.get(0).id).navigation()
                }
            }


            // ToastUtil.showShort("view1")
        }
        view2.setOnClickListener {
            if(Utils.isFastClick()){
                if(data.machine_list.get(1).can_in==0){
                    ToastUtil.showShort("请购买机甲")
                }else {
                    ARouter.getInstance().build(ArouterAddress.NEWACTIVITY)
                        .withString("can_get", data.machine_list.get(1).can_get.toString())
                        .withString("name", data.machine_list.get(1).name)
                        .withString("id", data.machine_list.get(1).id)
                        .navigation()            // ToastUtil.showShort("view2")
                }
            }
            }
        view3.setOnClickListener {
            if(Utils.isFastClick()){
                if(data.machine_list.get(2).can_in==0){
                    ToastUtil.showShort("请购买机甲")
                }else {
                    ARouter.getInstance().build(ArouterAddress.NEWACTIVITY).withString("can_get",data.machine_list.get(2).can_get.toString()).withString("name",data.machine_list.get(2).name).withString("id",data.machine_list.get(2).id).navigation()            //  ToastUtil.showShort("view3")
                }
            }
           }
        view4.setOnClickListener {
           if(Utils.isFastClick()){
               if(data.machine_list.get(3).can_in==0){
                   ToastUtil.showShort("请购买机甲")
               }else {
                   ARouter.getInstance().build(ArouterAddress.NEWACTIVITY).withString("can_get",data.machine_list.get(3).can_get.toString()).withString("name",data.machine_list.get(3).name).withString("id",data.machine_list.get(3).id).navigation()        }
           }
           }
          }
    }
