package ko.alex.androidmonetisationtutorial.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ko.alex.androidmonetisationtutorial.R
import ko.alex.androidmonetisationtutorial.model.Country
import ko.alex.androidmonetisationtutorial.util.getProgressDrawable
import ko.alex.androidmonetisationtutorial.util.loadImage
import kotlinx.android.synthetic.main.row_layout.view.*

class CountryListAdapter(var countries: ArrayList<Country>, val clickListener:CountryClickListener): RecyclerView.Adapter<CountryListAdapter.CountryViewHolder>() {

    fun updateCountries(newCountries: ArrayList<Country>){
        countries.clear()
        countries.addAll(newCountries)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): CountryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_layout, parent, false)
        return CountryViewHolder(view, clickListener)
    }

    override fun getItemCount() = countries.size

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(countries[position])
    }

    class CountryViewHolder(view: View, var clickListener: CountryClickListener): RecyclerView.ViewHolder(view){

        private val layout = view.layout
        private val imageView = view.imageView
        private val countryName = view.name
        private val countryCapital = view.capital

        fun bind(country: Country){
            countryName?.text = country.countryName
            countryCapital?.text = country.capital
            imageView.loadImage(country.flag, getProgressDrawable(imageView.context))

            layout.setOnClickListener{clickListener.onCountryClick(country)}
        }
    }

}