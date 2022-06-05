package com.example.countriesapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.countriesapp.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var countries: List<Country>? = null
    private val adapter = CountriesAdapter(lifecycleScope)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        savedInstanceState?.getParcelableArray("Country")
            ?.filterIsInstance<Country>()
            ?.let { counties ->
                lifecycleScope.launch {
                    displayCountries(counties)
                }
            }

        binding.countriesListView.adapter = adapter
        binding.countriesListView.layoutManager = LinearLayoutManager(this)

        binding.searchButton.setOnClickListener {
            val countryName = binding.countryNameEditText.text.toString()

            lifecycleScope.launch {
                try {
                    val countries = restCountiesApi.getCountryByName(countryName)
                    if (countries.isEmpty()) {
                        displayError()
                    } else {
                        displayCountries(countries)
                    }
                } catch (e: Exception) {
                    displayError()
                }
            }
        }
    }

    private fun displayError() {
        binding.statusTextView.text = "Страна не найдена"
        binding.statusImageView.setImageResource(R.drawable.ic_baseline_error_outline_24)
        binding.statusLayout.isVisible = true
        binding.countriesListView.isVisible = false
    }

    private fun displayCountries(countries: List<Country>) {
        this@MainActivity.countries = countries
        adapter.items = countries
        adapter.notifyDataSetChanged()
        binding.statusLayout.isVisible = false
        binding.countriesListView.isVisible = true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArray("Country", countries?.toTypedArray())
    }
}