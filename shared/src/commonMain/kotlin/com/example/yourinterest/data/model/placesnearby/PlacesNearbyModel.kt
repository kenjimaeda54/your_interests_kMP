package com.example.yourinterest.data.model.placesnearby


data class PlacesNearbyModel(
    val category: CategoryModel,
    val geocode: GeocodeModel,
    val distance: Int,
    val fsqId: String
)


data class CategoryModel(
    val id : String,
    val shortName: String,
    val icon: String
)

data class  GeocodeModel(
    val latitude: Double,
    val longitude: Double
)


fun PlacesNearbyResultResponse.toPlacesNearbyModel() = PlacesNearbyModel(
    fsqId = fsq_id,
    category = if( categories.isNotEmpty())  CategoryModel(
        id = categories.first().id,
        shortName = categories.first().short_name.ifEmpty { categories.first().name },
        icon = "${categories.first().icon.prefix}${categories.first().icon.suffix}"
    ) else CategoryModel(id = "",shortName = "",icon = ""),
    distance = distance,
    geocode = GeocodeModel(
        latitude = geocodes.main.latitude,
        longitude =  geocodes.main.longitude
    )
)