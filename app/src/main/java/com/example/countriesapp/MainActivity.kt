package com.example.countriesapp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.countriesapp.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private var country: Country? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val saveCountry = savedInstanceState?.getParcelable<Country>("Country")
        if (saveCountry != null) {
            lifecycleScope.launch {
                displayCountry(saveCountry)
            }
        }

        binding.searchButton.setOnClickListener {
            val countryName = binding.countryNameEditText.text.toString()

            lifecycleScope.launch {
                try {
                    val countries = restCountiesApi.getCountryByName(countryName)
                    val country = countries[0]

                    displayCountry(country)
                } catch (e: Exception) {
                    binding.statusTextView.text = "Страна не найдена"
                    binding.statusImageView.setImageResource(R.drawable.ic_baseline_error_outline_24)
                    binding.resultLayout.visibility = View.INVISIBLE
                    binding.statusLayout.visibility = View.VISIBLE

                }
            }
        }
    }

    private suspend fun displayCountry(country: Country) {
        binding.countyNameTextView.text = country.name
        binding.capitalTextView.text = country.capital
        binding.populationTextView.text = formatNumber(country.population)
        binding.areaTextView.text = formatNumber(country.area)
        binding.languagesTextView.text = country.languages.joinToString { it.name }

        loadSvg(binding.imageView, country.flag)

        binding.resultLayout.visibility = View.VISIBLE
        binding.statusLayout.visibility = View.GONE

        this@MainActivity.country = country // сохр страны в поле

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable("Country", country)

    }
}