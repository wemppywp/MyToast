package id.co.wemppywp.customtoast

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.Typeface
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import kotlinx.android.synthetic.main.full_color_toast.view.*
import kotlinx.android.synthetic.main.custom_toast.view.*

@Suppress("DEPRECATION")
class CustomToast {

    companion object{

        const val LONG_DURATION = 5000L
        const val SHORT_DURATION = 2000L
        const val TOAST_SUCCESS = "SUCCESS"
        const val TOAST_FAILED = "FAILED"
        const val TOAST_NO_INTERNET = "NO INTERNET CONNECTIONS"
        const val GRAVITY_TOP = 50
        const val GRAVITY_BOTTOM = 80
        const val GRAVITY_CENTER = 20

        private lateinit var layoutInflater: LayoutInflater

        private var successToastColor: Int = R.color.success_color
        private var failedToastColor: Int = R.color.failed_color
        private var noInternetToastColor: Int = R.color.no_internet_color

        private var successBackgroundToastColor: Int = R.color.success_bg_color
        private var failedBackgroundToastColor: Int = R.color.failed_bg_color
        private var noInternetBackgroundToastColor: Int = R.color.no_internet_bg_color

        fun resetToastColor(){
            successToastColor = R.color.success_color
            failedToastColor = R.color.failed_color
            noInternetToastColor =R.color.no_internet_color

            successBackgroundToastColor = R.color.success_bg_color
            failedBackgroundToastColor = R.color.failed_bg_color
            noInternetBackgroundToastColor = R.color.no_internet_bg_color
        }

        fun setSuccessColor(color: Int){
            successToastColor = color
        }
        fun setSuccessBackgroundColor(color: Int){
            successBackgroundToastColor = color
        }
        fun setFailedColor(color: Int){
            failedToastColor = color
        }
        fun setFailedBackgroundColor(color: Int){
            failedBackgroundToastColor = color
        }
        fun setNoInternetColor(color: Int){
            noInternetToastColor = color
        }
        fun setNoInternetBackgroundColor(color: Int){
            noInternetBackgroundToastColor = color
        }

        fun createToast(
            context: Activity,
            title: String? = null,
            message: String,
            style: String,
            position: Int,
            duration: Long,
            font: Typeface?
        ){
            layoutInflater = LayoutInflater.from(context)
            val layout = layoutInflater.inflate(
                R.layout.custom_toast, (context).findViewById(R.id.custom_toast_view)
            )
            when (style){
                TOAST_SUCCESS -> {
                    layout.custom_toast_image.setImageDrawable(
                        ContextCompat.getDrawable(
                            context,
                            R.drawable.ic_check_green
                        )
                    )
                    DrawableCompat.setTint(
                        DrawableCompat.wrap(layout.custom_toast_image.drawable),
                        ContextCompat.getColor(context, successToastColor)
                    )

                    // Pulse Animation for Icon
                    startPulseAnimation(context, layout)

                    // Background tint color for side view
                    layout.colorView.backgroundTintList =
                        ContextCompat.getColorStateList(context, successToastColor)

                    // round background color
                    setBackgroundAndFilter(
                        R.drawable.toast_round_background,
                        successBackgroundToastColor, layout, context
                    )

                    // Setting up the color for title & Message text
                    layout.custom_toast_text.setTextColor(
                        ContextCompat.getColor(
                            context,
                            successToastColor
                        )
                    )
                    layout.custom_toast_text.text =
                        if (title.isNullOrBlank()) TOAST_SUCCESS else title

                    setDescriptionDetails(font, Color.BLACK, message, layout.custom_toast_description)

                    // init toast
                    val toast = Toast(context.applicationContext)
                    startTimer(duration, toast)

                    // Setting Toast Gravity
                    setGravity(position, toast)

                    // Setting layout to toast
                    toast.view = layout
                    toast.show()
                }
            }
        }
        private fun startTimer(duration: Long, toast: Toast) {
            val timer = object : CountDownTimer(duration, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    // do nothing
                }

                override fun onFinish() {
                    toast.cancel()
                }
            }
            timer.start()
        }

        private fun startPulseAnimation(context: Activity, layout: View) {
            val pulseAnimation = AnimationUtils.loadAnimation(context, R.anim.pulse)
            layout.custom_toast_image.startAnimation(pulseAnimation)
        }

        private fun setDescriptionDetails(
            font: Typeface?,
            textColor: Int,
            message: String,
            layout: TextView
        ) {
            layout.setTextColor(textColor)
            layout.text = message
            font?.let {
                layout.typeface = font
            }
        }

        private fun setGravity(position: Int, toast: Toast) {
            if (position == GRAVITY_BOTTOM) {
                toast.setGravity(position, 0, 100)
            } else {
                toast.setGravity(position, 0, 0)
            }
        }

        private fun setBackgroundAndFilter(
            @DrawableRes background: Int,
            @ColorRes colorFilter: Int,
            layout: View,
            context: Context
        ) {
            val drawable = ContextCompat.getDrawable(context, background)
            drawable?.colorFilter = PorterDuffColorFilter(
                ContextCompat.getColor(context, colorFilter),
                PorterDuff.Mode.MULTIPLY
            )
            layout.background = drawable
        }
    }
}