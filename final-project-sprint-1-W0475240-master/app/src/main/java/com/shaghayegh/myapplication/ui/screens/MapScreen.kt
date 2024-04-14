package com.shaghayegh.myapplication.ui.screens

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import com.mapbox.common.MapboxOptions.accessToken
import com.mapbox.geojson.Point
import com.mapbox.maps.*
import com.shaghayegh.myapplication.R

@Composable
fun MapScreen() {
    AndroidView(
        factory = { context: Context ->
            MapView(
                context,
                MapInitOptions(context).apply {
                    // Set the access token
                    accessToken = context.getString(R.string.mapbox_access_token)
                }
            ).apply {
                // Setup initial camera position
                val halifaxLocation = CameraOptions.Builder()
                    .center(Point.fromLngLat(-63.5752, 44.6488)) // Longitude and latitude of Halifax
                    .zoom(12.0) // Appropriate zoom level for city view
                    .pitch(0.0) // Tilt of the camera in degrees
                    .bearing(0.0) // Direction the camera is pointing in (North by default)
                    .build()

                // Applying the camera settings to the map
                mapboxMap.setCamera(halifaxLocation)

                // Loading the map style
                mapboxMap.loadStyleUri(Style.MAPBOX_STREETS) { result ->
                    // Handle style loading result here
                }
            }
        },
        update = { mapView ->
            // Updates to the mapView can be handled here if needed
        }
    )
}
