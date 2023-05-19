package com.sentigo.bangkit.sentigoapp.ui.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.google.android.material.textfield.TextInputEditText
import com.sentigo.bangkit.sentigoapp.R

class PasswordEditText: TextInputEditText {

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
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Do nothing.
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.length<8) {
                    isError = true
                    error = R.string.password_6.toString()
                } else {
                    isError = false
                    error = null
                }
            }

            override fun afterTextChanged(s: Editable) {
                if (s.length<8) {
                    isError = true
                    setError(context.getString(R.string.password_6), null)
                } else {
                    isError = false
                    error = null
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