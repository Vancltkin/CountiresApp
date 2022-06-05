package com.example.countriesapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Country(
    val name: String,
    val capital: String,
    val population: Long,
    val area: Long,
    val languages: List<Language>,
    val flag: String
) : Parcelable

@Parcelize
data class Language(
    val name: String
) : Parcelable