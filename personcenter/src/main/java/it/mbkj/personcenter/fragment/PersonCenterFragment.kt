package it.mbkj.personcenter.fragment


import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.sskj.common.util.SPUtil

import it.mbkj.lib.base.ArouterAddress
import it.mbkj.lib.base.BaseFragment

import it.mbkj.lib.http.HttpConfig
import it.mbkj.lib.http.SPConfig
import it.mbkj.lib.utils.ImageUtil
import it.mbkj.lib.utils.Utils
import it.mbkj.personcenter.R
import it.mbkj.personcenter.bean.UserMsg
import it.mbkj.personcenter.databinding.FragmentPersoncenterBinding

import it.mbkj.personcenter.presenter.PersonCenterFragmentPresenter


@Route(path = ArouterAddress.PERSONCENTERFRAGMENT)
class PersonCenterFragment:BaseFragment<PersonCenterFragmentPresenter>(){
    var binding: FragmentPersoncenterBinding?=null
    override val layoutId: Int
        get() = R.layout.fragment_personcenter
    override val presenter: PersonCenterFragmentPresenter
        get() = PersonCenterFragmentPresenter()

    override fun isEmpty(textView: TextView?): Boolean {
        return false
    }

    override fun initView() {
        //super.initView()
        binding= getViewDataBinding<FragmentPersoncenterBinding>()
        binding!!.llItem1.setOnClickListener {
            if(Utils.isFastClick()){
                ARouter.getInstance().build(ArouterAddress.PLEDGEHALLACTIVITY).navigation()
            }

        }
        binding!!.llItem2.setOnClickListener{
            if(Utils.isFastClick()){
                ARouter.getInstance().build(ArouterAddress.MYJIJIAACTIVITY).navigation()
            }

        }
        binding!!.llItem3.setOnClickListener {
            if(Utils.isFastClick()){
                ARouter.getInstance().build(ArouterAddress.MYTEAMACTIVITY).navigation()
            }

        }
        binding!!.llItem4.setOnClickListener {
            if(Utils.isFastClick()){
                ARouter.getInstance().build(ArouterAddress.ZICHANXINXIACTIVITY).navigation()
            }

        }
        binding!!.llItem5.setOnClickListener {
            if(Utils.isFastClick()){
                ARouter.getInstance().build(ArouterAddress.PROFITRECORDACTIVITY).navigation()
            }

        }
        binding!!.llItem6.setOnClickListener {
            if(Utils.isFastClick()){
                ARouter.getInstance().build(ArouterAddress.SETTINGACTIVITY).navigation()
            }

        }
        binding!!.llItem7.setOnClickListener {
            if(Utils.isFastClick()){
                ARouter.getInstance().build(ArouterAddress.INVITEFRIENDSACTIVITY).navigation()
            }

        }
        binding!!.llItem8.setOnClickListener {
            if(Utils.isFastClick()){
                ARouter.getInstance().build(ArouterAddress.CONACTUSACTIVITY).navigation()
            }

        }
        binding!!.image.setOnClickListener {
            if(Utils.isFastClick()){
                ARouter.getInstance().build(ArouterAddress.ZICHANXINXIACTIVITY).navigation()
            }

        }
    }
   public override fun initData() {
        mPresenter!!.getHomePage(SPUtil.get(SPConfig.SESSION_ID,""))
    }

    fun setUI(userMsg: UserMsg) {
        binding!!.tvPhone.text = userMsg.phone
        binding!!.tvTeam.text =userMsg.level_msg.name
        ImageUtil.setImage(HttpConfig.BASE_IMG_URL+userMsg.level_msg.img_str,binding!!.image1)
        ImageUtil.setCircleImage(HttpConfig.BASE_IMG_URL+userMsg.head_img,binding!!.image)
        //tv_team.text =
    }


}