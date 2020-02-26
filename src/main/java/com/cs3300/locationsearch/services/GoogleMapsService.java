package com.cs3300.locationsearch.services;

import com.google.maps.GeoApiContext;
import com.google.maps.NearbySearchRequest;
import com.google.maps.model.LatLng;
import com.google.maps.model.PlacesSearchResponse;
import com.google.maps.model.PlacesSearchResult;

public class GoogleMapsService {
	
	private GeoApiContext context;
	
	public GoogleMapsService() {
		context = new GeoApiContext.Builder()
				.apiKey(System.getenv("PLACES_API_KEY"))
				.build();
	}
	
	public PlacesSearchResult[] getRequestWithLatLongAndRadius(double lat, double lng, int radius) {
		try {
			LatLng location = new LatLng(lat, lng);
			NearbySearchRequest request = new NearbySearchRequest(context)
					.location(location)
					.radius(radius);
			PlacesSearchResponse resp = request.await();
			return resp.results;
		} catch (Exception e) {
			System.out.println("Error getting nearby locations");
			e.printStackTrace();
			return null;
		}
	}
}
