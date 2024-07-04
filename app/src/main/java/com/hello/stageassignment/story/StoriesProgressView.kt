package com.hello.stageassignment.story

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.hello.stageassignment.R

class StoriesProgressView : LinearLayout {
    private val progressBarLayoutParams = LayoutParams(0, LayoutParams.WRAP_CONTENT, 1f)
    private val spaceLayoutParams = LayoutParams(5, LayoutParams.WRAP_CONTENT)

    private val progressBars: MutableList<PausableProgressBar> = ArrayList()

    private var storiesCount = -1

    private var current = -1
    private var storiesListener: StoriesListener? = null
    var isComplete: Boolean = false
        private set

    private var isSkipStart = false
    private var isReverseStart = false

    interface StoriesListener {
        fun onNext()

        fun onPrev()

        fun onComplete()
    }

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet? = null) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        orientation = HORIZONTAL
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.StoriesProgressView)
        storiesCount = typedArray.getInt(R.styleable.StoriesProgressView_progressCount, 0)
        typedArray.recycle()
        bindViews()
    }

    private fun bindViews() {
        progressBars.clear()
        removeAllViews()

        for (i in 0 until storiesCount) {
            val p = createProgressBar()
            progressBars.add(p)
            addView(p)
            if ((i + 1) < storiesCount) {
                addView(createSpace())
            }
        }
    }

    private fun createProgressBar(): PausableProgressBar {
        val p = PausableProgressBar(context)
        p.layoutParams = progressBarLayoutParams
        return p
    }

    private fun createSpace(): View {
        val v = View(context)
        v.layoutParams = spaceLayoutParams
        return v
    }

    fun setStoriesCount(storiesCount: Int) {
        this.storiesCount = storiesCount
        reset()
        bindViews()
    }

    private fun reset() {
        isComplete = false
        isSkipStart = false
        isReverseStart = false
        current = -1
    }

    fun setStoriesListener(storiesListener: StoriesListener?) {
        this.storiesListener = storiesListener
    }

    fun skip() {
        if (isSkipStart || isReverseStart) return
        if (isComplete) return
        if (current < 0) return
        val p = progressBars[current]
        isSkipStart = true
        p.setMax()
    }

    fun reverse() {
        if (isSkipStart || isReverseStart) return
        if (isComplete) return
        if (current < 0) return
        val p = progressBars[current]
        isReverseStart = true
        p.setMin()
    }

    fun setStoryDuration(duration: Long) {
        for (i in progressBars.indices) {
            progressBars[i].setDuration(duration)
            progressBars[i].setCallback(callback(i))
        }
    }

    private fun callback(index: Int): PausableProgressBar.Callback {
        return object : PausableProgressBar.Callback {
            override fun onStartProgress() {
                current = index
            }

            override fun onFinishProgress() {
                if (isReverseStart) {
                    storiesListener?.onPrev()
                    if (0 <= (current - 1)) {
                        val p = progressBars[current - 1]
                        p.setMinWithoutCallback()
                        progressBars[--current].startProgress()
                    } else {
                        progressBars[current].startProgress()
                    }
                    isReverseStart = false
                    return
                }
                val next = current + 1
                if (next <= (progressBars.size - 1)) {
                    storiesListener?.onNext()
                    progressBars[next].startProgress()
                } else {
                    isComplete = true
                    storiesListener?.onComplete()
                }
                isSkipStart = false
            }
        }
    }

    fun startStories(from: Int) {
        for (i in 0 until from) {
            progressBars[i].setMaxWithoutCallback()
        }
        progressBars[from].startProgress()
    }

    fun destroy() {
        for (p in progressBars) {
            p.clear()
        }
    }

    fun pause() {
        if (current < 0) return
        progressBars[current].pauseProgress()
    }

    fun resume() {
        if (current < 0) return
        progressBars[current].resumeProgress()
    }
}

