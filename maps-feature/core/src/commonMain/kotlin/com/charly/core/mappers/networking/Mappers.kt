package com.charly.core.mappers.networking

import com.charly.domain.model.networking.AddressComponents
import com.charly.domain.model.networking.Geocoding
import com.charly.domain.model.networking.Geometry
import com.charly.domain.model.networking.Location
import com.charly.domain.model.networking.Northeast
import com.charly.domain.model.networking.PlusCode
import com.charly.domain.model.networking.Results
import com.charly.domain.model.networking.Southwest
import com.charly.domain.model.networking.Viewport
import com.charly.networking.model.AddressComponentsData
import com.charly.networking.model.GeocodingData
import com.charly.networking.model.GeometryData
import com.charly.networking.model.LocationData
import com.charly.networking.model.NortheastData
import com.charly.networking.model.PlusCodeData
import com.charly.networking.model.ResultsData
import com.charly.networking.model.SouthwestData
import com.charly.networking.model.ViewportData

internal fun NortheastData?.mapToNortheast(): Northeast? {
    return if (this == null) {
        return null
    } else {
        Northeast(
            lat = lat,
            lng = lng
        )
    }
}

internal fun SouthwestData?.mapToSouthwest(): Southwest? {
    return if (this == null) {
        return null
    } else {
        Southwest(
            lat = lat,
            lng = lng
        )
    }
}

internal fun ViewportData?.mapToViewport(): Viewport? {
    return if (this == null) {
        return null
    } else {
        Viewport(
            northeast = northeast.mapToNortheast(),
            southwest = southwest.mapToSouthwest()
        )
    }
}

internal fun PlusCodeData?.mapToPlusCode(): PlusCode? {
    return if (this == null) {
        return null
    } else {
        PlusCode(
            compoundCode = compoundCode,
            globalCode = globalCode
        )
    }
}

internal fun LocationData?.mapToLocation(): Location? {
    return if (this == null) {
        return null
    } else {
        Location(
            lat = lat,
            lng = lng
        )
    }
}

internal fun GeometryData?.mapToGeometry(): Geometry? {
    return if (this == null) {
        return null
    } else {
        Geometry(
            location = location.mapToLocation(),
            locationType = locationType,
            viewport = viewport.mapToViewport()
        )
    }
}

internal fun AddressComponentsData?.mapToAddressComponents(): AddressComponents? {
    return if (this == null) {
        return null
    } else {
        AddressComponents(
            longName = longName,
            shortName = shortName,
            types = types
        )
    }
}

internal fun List<AddressComponentsData>?.mapToAddressComponentsList(): List<AddressComponents>? {
    return this?.mapNotNull { it.mapToAddressComponents() }
}

internal fun ResultsData?.mapToResults(): Results? {
    return if (this == null) {
        return null
    } else {
        Results(
            addressComponents = addressComponents.mapToAddressComponentsList(),
            formattedAddress = formattedAddress,
            geometry = geometry.mapToGeometry(),
            placeId = placeId,
            plusCode = plusCode.mapToPlusCode(),
            types = types
        )
    }
}

internal fun List<ResultsData>?.mapTpResultsList(): List<Results>? {
    return this?.mapNotNull { it.mapToResults() }
}

internal fun GeocodingData.mapToGeocoding(): Geocoding {
    return Geocoding(
        results = results.mapTpResultsList(),
        status = status
    )
}
