package com.exae.memorialapp.widget

import android.content.Context
import android.os.CountDownTimer
import android.text.Editable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextWatcher
import android.text.style.AbsoluteSizeSpan
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import clickWithTrigger
import com.exae.memorialapp.R

class CodeInputView : RelativeLayout {

    private lateinit var edtCode : EditText
    private lateinit var imgCancel : ImageView
    private lateinit var  viewLine : View
    private lateinit var tvGetCode : TextView
    private lateinit var timer: CountDownTimer
    private var codeGetListener : CodeGetListener? = null

    constructor(mContext: Context) : this(mContext,null)

    constructor(mContext: Context, attrs: AttributeSet?) : this(mContext, attrs!!,0)

    constructor(mContext: Context, attrs: AttributeSet, defStyleAttr:Int) : super(mContext, attrs,defStyleAttr) {
        initView()
    }

    private fun initView(){
        val contentView = LayoutInflater.from(context).inflate(R.layout.layout_code_input_view, this)

        edtCode = contentView.findViewById(R.id.edt_code) as EditText
        imgCancel = contentView.findViewById(R.id.img_cancel) as ImageView
        viewLine = contentView.findViewById(R.id.view_line) as View
        tvGetCode = contentView.findViewById(R.id.tv_getCode) as TextView

        setHintText(context.getString(R.string.code_edt_tips))

        edtCode.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                imgCancelState()
            }
        })

        edtCode.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus){
                viewLine.alpha = 1f
                imgCancelState()
            }else{
                viewLine.alpha = 0.2f
                imgCancel.visibility = View.INVISIBLE
            }
        }

        imgCancel.setOnClickListener {
            edtCode.setText("")
        }

        tvGetCode.clickWithTrigger {
            codeGetListener?.getCodeRequest()
        }

        timer = TimeCount()

    }

    private fun imgCancelState(){
        if (edtCode.text?.length!! > 0){
            imgCancel.visibility = View.VISIBLE
        }else{
            imgCancel.visibility = View.INVISIBLE
        }
    }

    fun endTime() {
        timer.cancel()
        timer.onFinish()
    }

    fun starTime() {
        timer.start()
    }

    fun setCodeGetListener(codeGetListener : CodeGetListener){
        this.codeGetListener = codeGetListener
    }

    fun getEditText() : String{
        return edtCode.text.toString().trim()
    }

    inner class TimeCount : CountDownTimer(60*1000,1000){
        override fun onFinish() {
            tvGetCode.isClickable = true
            tvGetCode.text = "获取验证码"
        }

        override fun onTick(millisUntilFinished: Long) {
            tvGetCode.isClickable = false
            tvGetCode.text = "${millisUntilFinished/1000} s"
        }

    }

    interface CodeGetListener{
       fun getCodeRequest()
    }

    private fun setHintText(tips : String){
        if (tips.isEmpty())return
        val s =  SpannableString(tips)
        val textSize = AbsoluteSizeSpan(12,true)
        s.setSpan(textSize,0,s.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        edtCode.hint = s
    }

}