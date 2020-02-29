let map;

// init map with arbitrary values
function initMap() {
  let uluru = {lat: -25.344, lng: 131.036};
  map = new google.maps.Map(
      document.getElementById('maprectangle'), {zoom: 4, center: uluru});
}

function loadUserInputMap() {
  let lat = document.getElementById("latitude").value;
  let longitude = document.getElementById("longitute").value;
  let radius = document.getElementById("range").value;
  
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
    for (var i = 0; i < results.length; i++) {
      let place = results[i];
      let marker = createMarker(results[i]);
      addResultToList(results[i], marker);
    }
  }
}


// to do: on button click for list: open marker

// add results to list group
function addResultToList(result, marker) {
	let resultlist = document.getElementById("resultslist");
	resultlist.innerHTML += 
		'<button class="list-group-item" id="' + result.id + '">' +  result.name + '</button>';
//	let elem = document.getElementById(result.id);
//	elem.addEventListener("click", function() {
//		console.log("asdjkalsda");
//	});
}

function openMarker() {
//	alert("hi");
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
		infowindow.open(map, this);
	});
}
