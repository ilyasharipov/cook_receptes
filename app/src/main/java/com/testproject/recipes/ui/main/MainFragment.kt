package com.testproject.recipes.ui.main

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.testproject.recipes.R
import com.testproject.recipes.data.RecipeData
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.main_fragment.*
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

class MainFragment : Fragment() {

    companion object {
        val TAG = "MAIN_FRAGMENT"

        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    private lateinit var listener: MainFragmentListener

    private val groupAdapter = GroupAdapter<GroupieViewHolder>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        if(activity is MainFragmentListener) {
            listener = activity as MainFragmentListener
        } else {
            throw RuntimeException("${context.toString()} must implement OnFragmentInteractionListener")
        }

        viewModel.clearEvents()
        viewModel.getAllRecipes()

        initRecyclerView()
        showProgressBar()

        observeErrors()
        observeGetAllRecipesComplete()
    }

    private fun initRecyclerView()
    {
        recipes_recycler.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = groupAdapter
        }
    }

    private fun loadRecipes(list: List<RecipeData>) {
        groupAdapter.apply {
            clear()
            for (item in list) {
                add(RecipeItem(
                    recipeData = item,
                    onClick = {
                        setObserveFragment(it)
                    }
                ))
            }
        }
    }

    private fun setObserveFragment(uuid: String) {
        listener.setObserveFragment(uuid)
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

    private fun observeGetAllRecipesComplete() {
        viewModel.getAllRecipesComplete.observe(this, Observer {
            hideProgressBar()
            loadRecipes(it)
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
        recipes_progressBar.visibility = View.GONE
    }

    private fun showProgressBar() {
        recipes_progressBar.visibility = View.VISIBLE
    }

    interface MainFragmentListener{
        fun setObserveFragment(uuid: String)
    }
}