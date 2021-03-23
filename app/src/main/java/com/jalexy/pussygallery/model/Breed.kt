package com.jalexy.pussygallery.model

import com.google.gson.annotations.SerializedName

data class Breed(
    val id: String, val name: String, val temperament: String,
    @SerializedName("life_span") val lifeSpan: String,
    @SerializedName("alt_names") val altNames: String,
    @SerializedName("wikipedia_url") val wikipediaUrl: String,
    val origin: String,
    @SerializedName("weight_imperial") val weightImperial: String,
    @SerializedName("experimental") val isExperimental: Int,
    @SerializedName("hairless") val isHairless: Int,
    @SerializedName("natural") val isNatural: Int,
    @SerializedName("rare") val isRare: Int,
    @SerializedName("rex") val isRex: Int,
    @SerializedName("suppress_tail") val isSuppressTail: Int,
    @SerializedName("short_legs") val isShortLegs: Int,
    @SerializedName("hypoallergenic") val isHypoallergenic: Int,
    val adaptability: Int,
    @SerializedName("affection_level") val affectionLevel: Int,
    @SerializedName("country_code") val countryCode: String,
    @SerializedName("child_friendly") val childFriendly: Int,
    @SerializedName("dog_friendly") val dogFriendly: Int,
    @SerializedName("energy_level") val energyLevel: Int,
    val grooming: Int,
    @SerializedName("health_issues") val healthIssues: Int,
    val intelligence: Int,
    @SerializedName("shedding_level") val sheddingLevel: Int,
    @SerializedName("social_needs") val socialNeeds: Int,
    @SerializedName("stranger_friendly") val strangerFriendly: Int,
    val vocalisation: Int
) {
    companion object {
        // константа чтобы определять трушность недобуленовских полей типа "short_legs" и т.п.
        // пример if (isShortLegs == Breed.TRUE)
        const val TRUE = 1
    }
}