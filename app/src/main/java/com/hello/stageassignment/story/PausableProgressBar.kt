package com.hello.stageassignment.story

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.ScaleAnimation
import android.view.animation.Transformation
import android.widget.FrameLayout
import androidx.annotation.AttrRes
import com.hello.stageassignment.R
import com.hello.stageassignment.databinding.PausableProgressBinding


internal class PausableProgressBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var binding: PausableProgressBinding =
        PausableProgressBinding.inflate(LayoutInflater.from(context), this, true)

    private var animation: PausableScaleAnimation? = null
    private var duration = DEFAULT_PROGRESS_DURATION.toLong()
    private var callback: Callback? = null

    internal interface Callback {
        fun onStartProgress()
        fun onFinishProgress()
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.pausable_progress, this)
        binding = PausableProgressBinding.bind(this)
    }

    fun setDuration(duration: Long) {
        this.duration = duration
    }

    fun setCallback(callback: Callback) {
        this.callback = callback
    }

    fun setMax() {
        finishProgress(true)
    }

    fun setMin() {
        finishProgress(false)
    }

    fun setMinWithoutCallback() {
        binding.maxProgress.setBackgroundResource(R.color.progress_secondary)
        binding.maxProgress.visibility = VISIBLE
        animation?.apply {
            setAnimationListener(null)
            cancel()
        }
    }

    fun setMaxWithoutCallback() {
        binding.maxProgress.setBackgroundResource(R.color.progress_max_active)
        binding.maxProgress.visibility = VISIBLE
        animation?.apply {
            setAnimationListener(null)
            cancel()
        }
    }

    private fun finishProgress(isMax: Boolean) {
        binding.maxProgress.apply {
            setBackgroundResource(if (isMax) R.color.progress_max_active else 0)
            visibility = if (isMax) VISIBLE else GONE
        }
        animation?.apply {
            setAnimationListener(null)
            cancel()
            callback?.onFinishProgress()
        }
    }

    fun startProgress() {
        binding.maxProgress.visibility = GONE
        animation = PausableScaleAnimation(
            0f, 1f, 1f, 1f,
            Animation.ABSOLUTE, 0f,
            Animation.RELATIVE_TO_SELF, 0f
        ).apply {
            duration = this@PausableProgressBar.duration
            interpolator = LinearInterpolator()
            setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {
                    binding.frontProgress.visibility = VISIBLE
                    callback?.onStartProgress()
                }

                override fun onAnimationRepeat(animation: Animation) {}

                override fun onAnimationEnd(animation: Animation) {
                    callback?.onFinishProgress()
                }
            })
            fillAfter = true
        }
        binding.frontProgress.startAnimation(animation)
    }

    fun pauseProgress() {
        animation?.pause()
    }

    fun resumeProgress() {
        animation?.resume()
    }

    fun clear() {
        animation?.apply {
            setAnimationListener(null)
            cancel()
            animation = null
        }
    }

    private inner class PausableScaleAnimation(
        fromX: Float, toX: Float, fromY: Float,
        toY: Float, pivotXType: Int, pivotXValue: Float, pivotYType: Int,
        pivotYValue: Float
    ) : ScaleAnimation(
        fromX, toX, fromY, toY, pivotXType, pivotXValue, pivotYType, pivotYValue
    ) {
        private var mElapsedAtPause: Long = 0
        private var mPaused = false

        override fun getTransformation(
            currentTime: Long,
            outTransformation: Transformation,
            scale: Float
        ): Boolean {
            if (mPaused && mElapsedAtPause == 0L) {
                mElapsedAtPause = currentTime - startTime
            }
            if (mPaused) {
                startTime = currentTime - mElapsedAtPause
            }
            return super.getTransformation(currentTime, outTransformation, scale)
        }

        fun pause() {
            if (mPaused) return
            mElapsedAtPause = 0
            mPaused = true
        }

        fun resume() {
            mPaused = false
        }
    }

    companion object {
        private const val DEFAULT_PROGRESS_DURATION = 5000
    }
}
