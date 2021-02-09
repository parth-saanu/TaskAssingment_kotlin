package net.simplifiedcoding.mvvmsampleapp.ui.activity.home

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import net.simplifiedcoding.mvvmsampleapp.R
import net.simplifiedcoding.mvvmsampleapp.data.network.responses.MovieModel
import net.simplifiedcoding.mvvmsampleapp.databinding.ActivityMainBinding
import net.simplifiedcoding.mvvmsampleapp.ui.adapter.MovieAdapter
import net.simplifiedcoding.mvvmsampleapp.ui.bottomsheet.FilterSheet
import net.simplifiedcoding.mvvmsampleapp.ui.callback.MovieListener
import net.simplifiedcoding.mvvmsampleapp.util.hide
import net.simplifiedcoding.mvvmsampleapp.util.show
import net.simplifiedcoding.mvvmsampleapp.util.snackbar
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import java.util.*

class MainActivity : AppCompatActivity(), MovieListener, KodeinAware, View.OnClickListener {
    override val kodein by kodein()
    private val factory : MainViewModelFactory by instance()
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private var radioButtonId =0
    private var results: MutableList<MovieModel.MovieResponse>? = null
    private var movieAdapter: MovieAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProviders.of(this, factory).get(MainViewModel::class.java)
        viewModel.authListener = this
        initListener()
        viewModel.getMovieList("2ba8497e5816fd9e6b52f8aa37adff46","en-US","1")
    }
    private fun initListener() {
        binding.ivFilter.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v){
            binding.ivFilter -> {
                val bottomSheet = FilterSheet()
                val bundle = Bundle()
                bundle.putInt("id", radioButtonId)
                bottomSheet.arguments = bundle
                bottomSheet.show(supportFragmentManager, "")
                bottomSheet.setOnCommonListener(this)
            }
        }
    }

    override fun onStarted() {
        binding.progressBar.show()
    }
       /* call recycler View for show listing data with grid */
    override fun onSuccess(results: MutableList<MovieModel.MovieResponse>?) {
        binding.progressBar.hide()
        this.results = results
        binding.rvMovie.layoutManager = GridLayoutManager(this,2)
        movieAdapter = MovieAdapter(this,results)
        binding.rvMovie.adapter = movieAdapter
    }

    override fun onFailure(message: String) {
        binding.progressBar.hide()
        binding.clMovie.snackbar(message)
    }
    /*Filter Method for sorting work with data and sorting based on Vote Average*/
    override fun onApplyFilter(radioButtonId: Int) {
        this.radioButtonId=radioButtonId
        if(radioButtonId != -1){
            if(radioButtonId == R.id.rbHigh){
                results?.sortWith(Comparator { response1, response2 ->
                    response2.vote_average!!.toFloat().compareTo(response1.vote_average!!.toFloat())
                })
            }else{
                results?.sortWith(Comparator { response1, response2 ->
                    response1.vote_average!!.toFloat().compareTo(response2.vote_average!!.toFloat())
                })
            }
            movieAdapter?.update(results)
        }
    }
}