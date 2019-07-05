package ko.alex.androidmonetisationtutorial.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import ko.alex.androidmonetisationtutorial.R
import ko.alex.androidmonetisationtutorial.adapter.CountryClickListener
import ko.alex.androidmonetisationtutorial.adapter.CountryListAdapter
import ko.alex.androidmonetisationtutorial.model.Country
import ko.alex.androidmonetisationtutorial.presenter.CountriesPresenter
import ko.alex.androidmonetisationtutorial.util.BillingAgent
import ko.alex.androidmonetisationtutorial.util.BillingCallback
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), CountryClickListener, CountriesPresenter.View, BillingCallback {


    private val countriesList = arrayListOf<Country>()
    private val countriesAdapter = CountryListAdapter(arrayListOf(), this)

    private val presenter = CountriesPresenter(this)

    private var billingAgent: BillingAgent? = null
    private var clickedCountry: Country? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        list.apply{
            layoutManager = LinearLayoutManager(context)
            adapter = countriesAdapter
        }

        billingAgent = BillingAgent(this, this)

    }

    override fun onDestroy() {
        billingAgent?.onDestroy()
        billingAgent = null
        super.onDestroy()
    }

    override fun onCountryClick(country: Country) {
        startActivity(DetailActivity.getIntent(this, country))
        Toast.makeText(this, "You clicked on ${country.countryName}", Toast.LENGTH_SHORT).show()

        clickedCountry = country
        billingAgent?.purchaseView()

    }

    override fun onTokenConsumed() {
        startActivity(DetailActivity.getIntent(this@MainActivity, clickedCountry))
    }

    fun onRetry(v: View){
        presenter.onRetry()
        retryButton.visibility = View.GONE
        progress.visibility = View.VISIBLE
        list.visibility = View.GONE
    }

    override fun setCountries(countries: List<Country>?) {
        countriesList.clear()
        countries?.let{countriesList.addAll(it)}
        countriesAdapter.updateCountries(countriesList)
        retryButton.visibility = View.GONE
        progress.visibility = View.GONE
        list.visibility = View.VISIBLE
    }

    override fun onError() {
        Toast.makeText(this, "Unable to get Countries List.", Toast.LENGTH_SHORT).show()
        retryButton.visibility = View.VISIBLE
        progress.visibility = View.GONE
        list.visibility = View.GONE
    }

}
