package com.testproject.recipes.ui.observe

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Build
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import com.testproject.recipes.R
import com.testproject.recipes.data.RecipeData
import kotlinx.android.synthetic.main.observe_fragment.*
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

class ObserveFragment(private val uuid: String) : Fragment() {

    companion object {
        val TAG = "OBSERVE_FRAGMENT"

        fun newInstance(uuid: String) = ObserveFragment(uuid)
    }

    private lateinit var viewModel: ObserveViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.observe_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ObserveViewModel::class.java)
        viewModel.clearEvents()

        observeErrors()
        observeGetRecipeByUUIDComplete()

        if (uuid.isNotBlank()){
            showProgressBar()
            viewModel.getRecipeByUUID(uuid)
        }
    }

    private fun pastRecipeData(data: RecipeData) {
        activity?.title = data.name
        observe_description.text = data.description
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            observe_instruction.text = Html.fromHtml(data.instructions, Html.FROM_HTML_MODE_COMPACT)
        } else {
            observe_instruction.text = Html.fromHtml(data.instructions)
        }
        observe_difficulty.text = "${data.difficulty}/5"



        observe_image_pager.adapter = ImagePagerAdapter(data.images, activity!!)
        observe_image_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int){}

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                observe_image_counter.text = "${position+1}/${data.images.size}"
            }
        })
    }

    private fun observeGetRecipeByUUIDComplete() {
        viewModel.getGetRecipeByUUIDComplete.observe(this, Observer {
            hideProgressBar()
            pastRecipeData(it)
        })
    }

    private fun observeErrors() {
        viewModel.error.observe(this, Observer {
            when (it)
            {
                is UnknownHostException -> setConnectionLostMessage()
                is TimeoutException -> setConnectionLostMessage()
            }
            hideProgressBar()
        })
    }

    private fun setConnectionLostMessage()
    {
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
        builder.setTitle(R.string.connection_lost_error)
            .setMessage(R.string.connection_lost_error_message)
            .setCancelable(true)
            .setPositiveButton(R.string.ok) { dialog: DialogInterface, _ -> dialog.cancel()}
        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }

    private fun hideProgressBar() {
        observe_progressBar.visibility = View.GONE
    }

    private fun showProgressBar() {
        observe_progressBar.visibility = View.VISIBLE
    }
}