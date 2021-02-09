package net.simplifiedcoding.mvvmsampleapp.ui.activity

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_details.*
import net.simplifiedcoding.mvvmsampleapp.R
import net.simplifiedcoding.mvvmsampleapp.data.db.AppDatabase
import net.simplifiedcoding.mvvmsampleapp.data.db.entities.Quote
import net.simplifiedcoding.mvvmsampleapp.data.network.responses.MovieModel
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class DetailsActivity : AppCompatActivity(), KodeinAware, View.OnClickListener {

    private var saveId: Int? = null
    override val kodein by kodein()
    val db: AppDatabase by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        initListener()
        getIntentData()
    }

    private fun initListener() {
        ivBack.setOnClickListener(this)
        ivLike.setOnClickListener(this)
        ivUnLike.setOnClickListener(this)
    }
    override fun onBackPressed() {
        super.onBackPressed()

    }
    /*set click event */
    override fun onClick(v: View?){
        when (v) {
            ivBack -> {
                onBackPressed()
            }
            ivUnLike -> {
                ivLike.visibility = View.VISIBLE
                ivUnLike.visibility = View.INVISIBLE
                saveId?.let {
                    /*use this line for Like */
                    db.getQuoteDao().insertQuote(Quote(it))
                    Log.d("DEBUG", "Saved")
                }
            }
            ivLike -> {
                ivLike.visibility = View.INVISIBLE
                ivUnLike.visibility = View.VISIBLE
                saveId?.let {
                    /*use this line for UnLike */
                    db.getQuoteDao().deleteQuote(Quote(it))
                    Log.d("DEBUG", "Delete")
                }
            }
        }
    }
      /*get all data*/
      private fun getIntentData(){
        val details = intent.getParcelableExtra<MovieModel.MovieResponse>("detail")
        details?.let {
            val image = it.backdrop_path
            if (image != null && image.isNotEmpty()){
                setImage(progressBar, "https://image.tmdb.org/t/p/original/$image", ivMovieDetails)
            } else {
                progressBar.visibility=View.GONE
                Glide.with(this).load(R.drawable.ic_launcher_background)
                    .into(ivMovieDetails)
            }
            tvTitle.text = it.title
            tvDes.text = it.overview
            saveId = it.id
            val id = db.getQuoteDao().getQuote(it.id)?.quoteId?:-1
            if (id == -1) {
                ivLike.visibility = View.INVISIBLE
                ivUnLike.visibility = View.VISIBLE
                //Toast.makeText(this, "Not liked", Toast.LENGTH_SHORT).show()
            } else {
                ivLike.visibility = View.VISIBLE
                ivUnLike.visibility = View.INVISIBLE
                //Toast.makeText(this, "liked", Toast.LENGTH_SHORT).show()
            }
            try {
                ratingBar.rating = it.vote_average!!.toFloat()/2
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /*call progress bar */
    private fun setImage(
        progressBar: ProgressBar,
        imageUri: String,
        imageView: ImageView
    ) {
        progressBar.visibility = View.VISIBLE
        Glide.with(this)
            .load(imageUri)
            .centerCrop()
            .listener(object : RequestListener<Drawable?> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable?>?,
                    isFirstResource: Boolean
                ): Boolean {
                    imageView.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable?>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    progressBar.visibility = View.GONE
                    return false
                }

            }).into(imageView)
    }
}