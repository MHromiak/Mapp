package com.cs3300.locationsearch.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cs3300.locationsearch.services.GoogleMapsService;
import com.google.maps.model.PlacesSearchResult;

@Controller
public class MainPageController {
	
	@RequestMapping("/searchMap")
	public boolean searchMap() {
		double lat = 10000;
		double lng = 10000;
		int radius = 10;
		
		GoogleMapsService googleMapsService = new GoogleMapsService();
		PlacesSearchResult[] result = googleMapsService.getRequestWithLatLongAndRadius(lat, lng, radius);
		if (result == null) {
			return false;
		}
		return true;
	}
	
}
