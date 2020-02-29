package com.cs3300.locationsearch.controllers;


import com.cs3300.locationsearch.services.GoogleMapsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.maps.model.PlacesSearchResult;



@Controller
@RequestMapping("/submitMapSearchInfo")
public class MapController {
	
	@Autowired
	GoogleMapsService maps;
	
	/**
	 * Microservice that returns search results using given parameters
	 * 
	 * endpoint format: /submitMapSearchInfo?lat={}&lon={}&rad={}
	 * 
	 * @param latitude
	 * @param longitude
	 * @param radius
	 * @return
	 */
	@GetMapping
	public PlacesSearchResult[] loginUser(@RequestParam(value="lat", required=true) String latitude, 
			@RequestParam(value="lon", required=true) String longitude, @RequestParam(value="rad", required=true) String radius) {
		double lat = Double.parseDouble(latitude);
		double lon = Double.parseDouble(longitude);
		int rad = Integer.parseInt(radius);
		PlacesSearchResult[] places = maps.getRequestWithLatLongAndRadius(lat, lon, rad);
		return places;
		
	}

}