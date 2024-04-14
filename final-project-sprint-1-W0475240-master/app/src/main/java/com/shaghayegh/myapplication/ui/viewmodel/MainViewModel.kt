package com.shaghayegh.myapplication.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.transit.realtime.GtfsRealtime
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.net.URL

class MainViewModel : ViewModel() {

    // StateFlow to hold vehicle positions
    private val _feedStateFlow = MutableStateFlow<GtfsRealtime.FeedMessage?>(null)
    val feedStateFlow: StateFlow<GtfsRealtime.FeedMessage?> = _feedStateFlow

    // StateFlow to hold transit alerts
    private val _alertFeedStateFlow = MutableStateFlow<GtfsRealtime.FeedMessage?>(null)
    val alertFeedStateFlow: StateFlow<GtfsRealtime.FeedMessage?> = _alertFeedStateFlow

    // Method to load bus positions asynchronously
    fun loadBusPositions() {
        Thread {
            try {
                // Fetch vehicle positions
                val vehicleUrl = URL("https://gtfs.halifax.ca/realtime/Vehicle/VehiclePositions.pb")
                val vehicleFeed = GtfsRealtime.FeedMessage.parseFrom(vehicleUrl.openStream())

                // Fetch alerts
                val alertUrl = URL("https://gtfs.halifax.ca/realtime/Alert/Alerts.pb")
                val alertFeed = GtfsRealtime.FeedMessage.parseFrom(alertUrl.openStream())

                // Update the StateFlow with new data
                _feedStateFlow.value = vehicleFeed
                _alertFeedStateFlow.value = alertFeed

                // Log vehicle positions for debugging
                vehicleFeed.entityList.forEach { entity ->
                    if (entity.hasVehicle()) {
                        val vehicle = entity.vehicle
                        if (vehicle.hasTrip()) {
                            val trip = vehicle.trip
                            val routeId = trip.routeId ?: "Unknown"
                            val latitude = vehicle.position.latitude
                            val longitude = vehicle.position.longitude
                            Log.i("BusPosition", "Route: $routeId, Latitude: $latitude, Longitude: $longitude")
                        }
                    }
                }

                // Log alerts for debugging
                alertFeed.entityList.forEach { entity ->
                    if (entity.hasAlert()) {
                        val alert = entity.alert
                        val routeIdAffected = alert.informedEntityList[0].routeId
                        val cause = alert.cause
                        val effect = alert.effect
                        val description = alert.headerText.translationList[0].text
                        Log.i("Alert", "Route: $routeIdAffected, Cause: $cause, Effect: $effect, Description: $description")
                    }
                }
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error loading GTFS data", e)
            }
        }.start()
    }
}
