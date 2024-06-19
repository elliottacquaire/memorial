package com.exae.memorialapp.widget

import android.content.Context
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
import com.exae.memorialapp.R

class CipherInputView : RelativeLayout {

    private lateinit var edtPhone : EditText
    private lateinit var imgCancel : ImageView
    private lateinit var  viewLine : View

    constructor(mContext: Context) : this(mContext,null)

    constructor(mContext: Context, attrs: AttributeSet?) : this(mContext, attrs!!,0)

    constructor(mContext: Context, attrs: AttributeSet,defStyleAttr:Int) : super(mContext, attrs,defStyleAttr) {
        initView()
    }

    private fun initView(){
        val contentView = LayoutInflater.from(context).inflate(R.layout.layout_cipher_input_view, this)

        edtPhone = contentView.findViewById(R.id.edt_phone) as EditText
        imgCancel = contentView.findViewById(R.id.img_cancel) as ImageView
        viewLine = contentView.findViewById(R.id.view_line) as View

        setHintText("请输入密码")
        edtPhone.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                imgCancelState()
            }

        })

        edtPhone.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus){
                viewLine.alpha = 1f
                imgCancelState()
            }else{
                viewLine.alpha = 0.2f
                imgCancel.visibility = View.INVISIBLE
            }
        }

        imgCancel.setOnClickListener {
            edtPhone.setText("")
        }

    }

    private fun imgCancelState(){
        if (edtPhone.text?.length!! > 0){
            imgCancel.visibility = View.VISIBLE
        }else{
            imgCancel.visibility = View.INVISIBLE
        }
    }

    fun getEditText() : String{
        return edtPhone.text.toString().trim()
    }

    fun setEditText(arg : String){
        if (arg.isNotEmpty()){
            edtPhone.setText(arg)
        }
    }
    private fun setHintText(tips : String){
        if (tips.isEmpty())return
        val s =  SpannableString(tips)
        val textSize = AbsoluteSizeSpan(12,true);
        s.setSpan(textSize,0,s.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        edtPhone.hint = s
    }

}