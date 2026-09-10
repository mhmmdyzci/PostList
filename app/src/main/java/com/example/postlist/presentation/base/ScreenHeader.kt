package com.example.postlist.presentation.base

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import androidx.appcompat.content.res.AppCompatResources
import com.example.postlist.R
import com.example.postlist.databinding.ViewScreenHeaderBinding

class ScreenHeader @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding = ViewScreenHeaderBinding.inflate(
        android.view.LayoutInflater.from(context),
        this,
        true
    )
    private var backClickListener: (() -> Unit)? = null

    init {
        context.withStyledAttributes(
            attrs,
            R.styleable.ScreenHeader,
            defStyleAttr,
            0
        ) {

            binding.toolbar.title = getText(R.styleable.ScreenHeader_headerTitle)
            getResourceId(R.styleable.ScreenHeader_headerTitleTextAppearance, 0)
                .takeIf { it != 0 }
                ?.let { binding.toolbar.setTitleTextAppearance(context, it) }
            setBackButtonVisible(
                getBoolean(R.styleable.ScreenHeader_showBackButton, false)
            )
        }

        binding.toolbar.setNavigationOnClickListener { backClickListener?.invoke() }
    }
    fun setOnBackClickListener(listener: (() -> Unit)?) {
        backClickListener = listener
    }

    fun setBackButtonVisible(visible: Boolean) {
        binding.toolbar.navigationIcon = if (visible) {
            AppCompatResources.getDrawable(context, R.drawable.ic_arrow_back)?.mutate()?.apply {
                setTint(ContextCompat.getColor(context, R.color.white))
            }
        } else {
            null
        }
        binding.toolbar.navigationContentDescription = if (visible) {
            context.getString(R.string.navigate_back)
        } else {
            null
        }
    }
}
