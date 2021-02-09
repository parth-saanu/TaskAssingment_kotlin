package net.simplifiedcoding.mvvmsampleapp.ui.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_filter.*
import kotlinx.android.synthetic.main.bottom_filter.view.*
import net.simplifiedcoding.mvvmsampleapp.R
import net.simplifiedcoding.mvvmsampleapp.ui.callback.MovieListener

class FilterSheet : BottomSheetDialogFragment(), View.OnClickListener {

    private lateinit var layoutView: View
    private var movieListener: MovieListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        layoutView = inflater.inflate(R.layout.bottom_filter, container, false)
        intiListener()
        getIntentData()
        return layoutView.rootView
    }

    private fun intiListener() {
        layoutView.btnApply.setOnClickListener(this)
    }
     /*check filter option for filter*/
    override fun onClick(v: View?) {
        when(v){
            layoutView.btnApply -> {
                movieListener?.onApplyFilter(
                    layoutView.rgPrice.checkedRadioButtonId
                )
                dismiss()
            }
        }
    }
    /*This method call for filter of list sort by Vote avrage*/
    private fun getIntentData() {
        val bundle = arguments
        if (bundle != null) {
            when (bundle.getInt("id")) {
                R.id.rbHigh -> layoutView.rbHigh.isChecked = true
                R.id.rbLow -> layoutView.rbLow.isChecked = true
            }
        }
    }
    fun setOnCommonListener(listener: MovieListener) {
        movieListener = listener
    }
}