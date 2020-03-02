let map;
let markers;
let places;
let openinfowindow;

// init map with arbitrary values
function initMap() {
  let uluru = {lat: -25.344, lng: 131.036};
  map = new google.maps.Map(
      document.getElementById('maprectangle'), {zoom: 4, center: uluru});
}

function openMarker(id) {
	for (var i = 0; i < places.length; i++) {
		if (places[i].id == id) {
			google.maps.event.trigger(markers[i], 'click');
			currentOpenMarker = markers[i];
			return;
		}
	}
}

//add results to list group
function addResultToList(result) {
	let resultlist = document.getElementById("resultslist");
	resultlist.innerHTML +=
		'<button onclick="openMarker(this.id)" class="list-group-item" id="' + result.id + '">' + 
		result.name + '</button>';
}

function createMarker(place) {
	var marker = new google.maps.Marker({
		map: map,
		title: place.name,
		position: place.geometry.location
	});
	setMarkerListener(place, marker);
	return marker;
}

function setMarkerListener(place, marker) {
	let infowindow = new google.maps.InfoWindow();
	google.maps.event.addListener(marker, 'click', function() {
		infowindow.setContent('<div><strong>' + place.name + '<strong><br>' +
				place.formatted_address + '</div>');
		if (openinfowindow) openinfowindow.close();
		infowindow.open(map, this);
		openinfowindow = infowindow;
	});
}

function loadUserInputMap() {
  let lat = document.getElementById("latitude").value;
  let longitude = document.getElementById("longitute").value;
  let radius = document.getElementById("range").value;

  let isEmpty = !lat || !longitude || !radius;
  let isNotNumber = isNaN(lat) || isNaN(longitude) || isNaN(radius);
  
  if (isEmpty || isNotNumber) {
	  alert('Please enter valid values for input!');
	  return;
  }
  
  let latlong = new google.maps.LatLng(lat, longitude);
  
  map = new google.maps.Map(
	      document.getElementById('maprectangle'), {
	    	  center: latlong,
	    	  zoom: 15
	      });
  
  let request = {
    location: latlong,
    radius: radius,
    query: 'restaurant'
  };
  
  service = new google.maps.places.PlacesService(map);
  service.textSearch(request, callback);
}

function callback(results, status) {
  if (status == google.maps.places.PlacesServiceStatus.OK) {
	markers = new Array(results.length);
	places = new Array(results.length);
    for (var i = 0; i < results.length; i++) {
      place = results[i];
      marker = createMarker(results[i]);
      markers[i] = marker;
      places[i] = place;
      addResultToList(results[i]);
    }
  }
}
