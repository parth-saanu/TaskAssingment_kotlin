package net.simplifiedcoding.mvvmsampleapp.ui.adapter

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.layout_movie_item.view.*
import net.simplifiedcoding.mvvmsampleapp.R
import net.simplifiedcoding.mvvmsampleapp.data.network.responses.MovieModel
import net.simplifiedcoding.mvvmsampleapp.ui.activity.DetailsActivity

class MovieAdapter(
    private var context: Context,
    private var results: MutableList<MovieModel.MovieResponse>?
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view  = LayoutInflater.from(context).inflate(R.layout.layout_movie_item,parent,false)
        return MovieViewHolder(
            view
        )
    }
    override fun getItemCount(): Int {
        return results?.size?:0
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.itemView.tvMovieName.text = results?.get(position)?.title
        holder.itemView.tvTime.text = results?.get(position)?.release_date
        val image = results?.get(position)?.backdrop_path
        if(image!=null && image.isNotEmpty()){
            setImage(holder.itemView.progressbar, "https://image.tmdb.org/t/p/original/$image", holder.itemView.ivMovie)
        }else{
            holder.itemView.progressbar.visibility = View.GONE
            Glide.with(context).load(R.drawable.ic_launcher_background)
                .into(holder.itemView.ivMovie)
        }
        holder.itemView.cvMovie.setOnClickListener {
            val intent = Intent(context,DetailsActivity::class.java)
            intent.putExtra("detail",results?.get(position))
            context.startActivity(intent)
        }
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private fun setImage(
        progressBar: ProgressBar,
        imageUri: String,
        imageView: ImageView
    ) {
        progressBar.visibility = View.VISIBLE
        Glide.with(context.applicationContext)
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

    fun update(results: MutableList<MovieModel.MovieResponse>?){
        this.results = results
        notifyDataSetChanged()
    }
}