package com.example.billwise.models

data class Bill(
    val wattage: String? = null,
    val quantity: String? = null,
    val dailyUsage: String? = null,
    val unitPrice: String? = null,
    val email : String? = null,
    val results : Double? = null
)
