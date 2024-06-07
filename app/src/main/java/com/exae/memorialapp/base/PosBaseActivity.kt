package com.exae.memorialapp.base

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.NotificationManagerCompat
import androidx.viewbinding.ViewBinding
import com.exae.memorialapp.R
import com.exae.memorialapp.dialog.MMLoading

abstract class PosBaseActivity<T : ViewBinding>: AppCompatActivity() {

    private var topToolbar : Toolbar? = null
    private var imgHead: ImageView? = null
    private var toolbarTitle: TextView? = null
    private var tvCancel : TextView? = null
    private var tvRecord : TextView? = null

    var mLoadingDialog: MMLoading? = null

    private lateinit var _binding: T
    protected val binding get() = _binding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        StatusBarHelper.translucent(this)
//        StatusBarHelper.isDayNight(this)
        _binding = getViewBinding()
        setContentView(_binding.root)

        initToolBar()

        window.navigationBarColor = getColor(R.color.color_black)
    }

    protected abstract fun getViewBinding(): T

    private fun initToolBar() {
        topToolbar = _binding.root.findViewById(R.id.top_toolbar_p)
        imgHead = _binding.root.findViewById(R.id.img_head)
        toolbarTitle = _binding.root.findViewById(R.id.toolbar_title)
        tvCancel = _binding.root.findViewById(R.id.tv_cancel)
        tvRecord = _binding.root.findViewById(R.id.tv_record)

        topToolbar?.fitsSystemWindows = true
        //利用Toolbar代替ActionBar
        setSupportActionBar(topToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        topToolbar?.setNavigationOnClickListener {
            onBackPressed()
        }
        imgHead?.visibility = View.GONE
        imgHead?.setOnClickListener {
            rightImageClick()
        }
        tvCancel?.setOnClickListener {
            leftTvClick()
            onBackPressed()
        }
        tvRecord?.setOnClickListener {
            rightTvClick()
        }
        setSettingImage(false)
        setCancelState(false)
        setBackState(true) //back 箭头 默认显示
        setRightTv(false)
    }

    open fun setNavigationIcon(resourceId: Int){
        topToolbar?.setNavigationIcon(resourceId)
    }

    open fun setToolTitle(title: String?){
        title.let {
            toolbarTitle?.text = it
        }
    }

    open fun setBackState(isShow: Boolean){
        if (isShow){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }else{
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
        }

    }

    //取消 显示状态
    open fun setCancelState(isShow: Boolean){
        if (isShow){
            tvCancel?.visibility = View.VISIBLE
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
        }else{
            tvCancel?.visibility = View.GONE
        }
    }
    open fun setRightImg(resourceId: Int) {
        imgHead?.setImageResource(resourceId)
    }
    open fun setSettingImage(isShow: Boolean){
        if (isShow){
            imgHead?.visibility = View.VISIBLE
        }else{
            imgHead?.visibility = View.GONE
        }
    }

    open fun setRightTv(isShow: Boolean){
        if (isShow){
            tvRecord?.visibility = View.VISIBLE
        }else{
            tvRecord?.visibility = View.GONE
        }
    }

    open fun rightImageClick(){

    }

    open fun rightTvClick(){

    }

    open fun leftTvClick() {

    }
    fun showLoading(){
        if (mLoadingDialog == null){
            val loadBuilder = MMLoading.Builder(this)
                .setMessage("加载中...")
                .setCancelable(true)
                .setCancelOutside(true)
            mLoadingDialog = loadBuilder?.create()
        }
        if (!mLoadingDialog?.isShowing!!){
            mLoadingDialog?.show()
        }

    }

     fun dismissLoading() {
        if (mLoadingDialog != null && mLoadingDialog?.isShowing!!) mLoadingDialog?.dismiss()
    }

    private fun isNotificationEnabled() : Boolean{
        var isOpened = false
        isOpened = try {
            NotificationManagerCompat.from(baseContext).areNotificationsEnabled()
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
        return isOpened
    }

    override fun onBackPressed() {
        super.onBackPressed()

    }

}