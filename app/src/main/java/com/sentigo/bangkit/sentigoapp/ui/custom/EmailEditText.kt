package com.sentigo.bangkit.sentigoapp.ui.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import androidx.core.content.ContextCompat
import com.google.android.material.textfield.TextInputEditText
import com.sentigo.bangkit.sentigoapp.R

class EmailEditText : TextInputEditText {

    private var errorBorder: Drawable? = null
    private var defaultBorder: Drawable? = null
    private var isError: Boolean = false

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        errorBorder = ContextCompat.getDrawable(context, R.drawable.bg_errror)
        defaultBorder = ContextCompat.getDrawable(context, R.drawable.bg_default)

        addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (Patterns.EMAIL_ADDRESS.matcher(s).matches()) {
                    isError = false
                } else {
                    isError = true
                    setError(context.getString(R.string.ed_email_err), null)
                }
            }

            override fun afterTextChanged(s: Editable?) {
                if (Patterns.EMAIL_ADDRESS.matcher(s).matches()) {
                    isError = false
                } else {
                    isError = true
                    setError(context.getString(R.string.ed_email_err), null)
                }
            }

        })
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        background = if (isError) {
            errorBorder
        } else defaultBorder
    }
}