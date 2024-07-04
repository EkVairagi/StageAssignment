package com.hello.stageassignment.ui.fragments

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.hello.stageassignment.databinding.FragmentSecondBinding
import com.hello.stageassignment.data.model.StoryEntity
import com.hello.stageassignment.story.StoriesProgressView

class ViewStoryFragment : Fragment(), StoriesProgressView.StoriesListener {

    private lateinit var stories: List<StoryEntity> // Change to StoryEntity
    private lateinit var imageUrls: List<String>
    private lateinit var username: String
    private lateinit var userProfile: String
    private lateinit var storyTimes: List<String>

    private var pressTime = 0L
    private val limit = 500L

    private var storyCounter = 0
    private var imageCounter = 0

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!

    @SuppressLint("ClickableViewAccessibility")
    private val onTouchListener = View.OnTouchListener { _, motionEvent ->
        when (motionEvent.action) {
            MotionEvent.ACTION_DOWN -> {
                pressTime = System.currentTimeMillis()
                binding.stories.pause()
                false
            }
            MotionEvent.ACTION_UP -> {
                val now = System.currentTimeMillis()
                binding.stories.resume()
                limit < now - pressTime
            }
            else -> false
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initializing Variables through bundle
        initialize()

        // Initialize the first story
        loadStory()
    }

    private fun loadStory() {
        if (storyCounter < stories.size) {
            val currentStory = stories[storyCounter]
            imageUrls = currentStory.imageUrls
            storyTimes = currentStory.storyTimes
            username = currentStory.username
            userProfile = currentStory.userProfile

            imageCounter = 0

            // Initialize stories view
            binding.stories.setStoriesCount(imageUrls.size)
            binding.stories.setStoryDuration(3000L)
            binding.stories.setStoriesListener(this)
            binding.stories.startStories(imageCounter)

            // Set the first image
            glideImage(imageUrls[imageCounter], username, storyTimes[imageCounter])

            // Set touch listeners for reverse and skip views
            binding.reverse.setOnClickListener {
                Log.e("CLICKED_FOR_REVERSE","STORY_ITEM")
                binding.stories.reverse()
            }
            binding.reverse.setOnTouchListener(onTouchListener)

            binding.skip.setOnClickListener {
                Log.e("CLICKED_FOR_NEXT","STORY_ITEM")
                binding.stories.skip()
            }
            binding.skip.setOnTouchListener(onTouchListener)
        } else {
            // All stories are completed
            parentFragmentManager.popBackStack()
        }
    }

    override fun onNext() {
        if (imageCounter < imageUrls.size - 1) {
            glideImage(imageUrls[++imageCounter], username, storyTimes[imageCounter])
        } else {
            // Move to the next story
            storyCounter++
            loadStory()
        }
    }

    override fun onPrev() {
        if ((imageCounter - 1) >= 0) {
            glideImage(imageUrls[--imageCounter], username, storyTimes[imageCounter])
        }
    }

    override fun onComplete() {
        // Move to the next story when the current story is complete
        storyCounter++
        loadStory()
    }

    override fun onDestroyView() {
        binding.stories.destroy()
        super.onDestroyView()
        _binding = null
    }

    private fun glideImage(url: String, username: String, time: String) {
        Glide.with(this)
            .load(userProfile)
            .apply(RequestOptions.circleCropTransform())
            .into(binding.profileImage)

        Glide.with(this)
            .load(url)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: com.bumptech.glide.request.target.Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    Toast.makeText(requireContext(), "Failed to load image.", Toast.LENGTH_SHORT).show()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: com.bumptech.glide.request.target.Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }
            })
            .into(binding.image)

        binding.usernameTV.text = username
        binding.storyTimeTV.text = time
    }

    private fun initialize() {
        try {
            arguments?.let {
                stories = it.getParcelableArrayList("STORIES_LIST") ?: emptyList()
            }

            // Log the size of the stories list to debug
            Log.d("ViewStoryFragment", "Received ${stories.size} stories")

            if (stories.isEmpty()) {
                Log.e("ViewStoryFragment", "No stories found in the bundle")
            }

        } catch (e: Exception) {
            Log.e("ViewStoryFragment", "Error initializing stories: ${e.message}")
        }
    }
}
