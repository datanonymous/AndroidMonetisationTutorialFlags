package ko.alex.androidmonetisationtutorial.activity

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import ko.alex.androidmonetisationtutorial.R
import ko.alex.androidmonetisationtutorial.model.Country
import ko.alex.androidmonetisationtutorial.util.getProgressDrawable
import ko.alex.androidmonetisationtutorial.util.loadImage
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    lateinit var country: Country

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        if(intent.hasExtra(PARAM_COUNTRY) && intent.getParcelableExtra<Country>(PARAM_COUNTRY) != null){
            country = intent.getParcelableExtra(PARAM_COUNTRY)
        } else {
            finish()
        }

        populate()

    }

    fun populate(){
        countryFlag.loadImage(country.flag, getProgressDrawable(this))
        textName.text = country.countryName
        textCapital.text = "Capital: ${country.capital}"
        textArea.text = "Area: ${country.area}"
        textPopulation.text = "Population: ${country.population}"
        textRegion.text = "Region: ${country.region}"
    }

    companion object{
        val PARAM_COUNTRY = "country"

        fun getIntent(context: Context, country: Country): Intent{
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(PARAM_COUNTRY, country)
            return intent
        }
    }

}
