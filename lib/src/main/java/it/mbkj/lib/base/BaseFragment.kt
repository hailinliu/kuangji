package it.mbkj.lib.base

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import butterknife.ButterKnife
import butterknife.Unbinder
import com.bulong.rudeness.RudenessScreenHelper
import com.trello.rxlifecycle4.components.support.RxFragment

import it.mbkj.lib.R

@Suppress("UNCHECKED_CAST")
abstract class BaseFragment<P : IPresenter<*>?> : RxFragment(), IBaseViewLife {
    @JvmField
    protected var mPresenter: P? = null
    private var inflate: View? = null
    private var dataBinding:ViewDataBinding?=null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (inflate == null) {
            dataBinding = DataBindingUtil.inflate(inflater,layoutId, container,false)
            inflate = dataBinding!!.root
            mPresenter = presenter
            mPresenter!!.attachView(this, activity)
        }
        if (inflate!!.parent != null) {
            (inflate!!.parent as ViewGroup).removeView(inflate)
        }
        return inflate
    }

    override fun onDestroy() {
        super.onDestroy()

            mPresenter!!.detachView()
            mPresenter!!.cancelRequest()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
        initEvent()
    }

    protected open fun <T : ViewDataBinding?> getViewDataBinding(): T {
        return dataBinding as T
    }
    protected abstract val layoutId: Int
    protected abstract val presenter: P
    protected open fun initView() {}
    protected open fun initData() {}
    protected open fun initEvent() {}
    override fun showLoading() {
        if (activity != null && activity is BaseActivity<*>) {
            (activity as BaseActivity<*>?)!!.showLoading()
        }
    }

    override fun hideLoading() {
        if (activity != null && activity is BaseActivity<*>) {
            (activity as BaseActivity<*>?)!!.hideLoading()
        }
    }

    /**
     * 设置标题
     *
     * @param title
     */
    fun setTitle(title: String?) {
        if (TextUtils.isEmpty(title)) {
            return
        }
        val tvTitle = inflate!!.findViewById<TextView>(R.id.tvTitle)
        if (tvTitle != null) {
            tvTitle.text = title
        }
    }

    override fun getText(textView: TextView?): String {
        return textView!!.text.toString()
    }

    override fun color(id: Int): Int {
        return ContextCompat.getColor(requireContext(), id)
    }

    override fun drawable(id: Int): Drawable {
        return ContextCompat.getDrawable(requireContext(), id)!!
    }

    override fun setText(textView: TextView?, text: String?) {

            textView!!.text = text

        if (textView is EditText) {
            textView.setSelection(textView.text.length)
        }
    }
}