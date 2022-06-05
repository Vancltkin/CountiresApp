package com.example.countriesapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.RecyclerView
import com.example.countriesapp.databinding.CoutryItemBinding
import kotlinx.coroutines.launch

class CountriesAdapter(private val lifecycleScope: LifecycleCoroutineScope) :
    RecyclerView.Adapter<CountriesAdapter.CountryViewHolder>() {
    var items: List<Country> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        return CountryViewHolder(
            CoutryItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        with(holder.binding) {
            val country = items[position]
            countryNameView.text = country.name
            countryCapitalView.text = country.capital
            populationView.text = formatNumber(country.population)
            areaTextView.text = formatNumber(country.area)
            languagesTextView.text = country.languages.joinToString { it.name }

            lifecycleScope.launch {
                loadSvg(countryImageView, country.flag)
            }
        }
    }

    override fun getItemCount(): Int = items.size

    class CountryViewHolder(val binding: CoutryItemBinding) : RecyclerView.ViewHolder(binding.root)
}