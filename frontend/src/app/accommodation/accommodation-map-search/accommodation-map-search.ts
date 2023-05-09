import {Component, QueryList, ViewChildren} from '@angular/core';
import {AuthService} from "../../service/auth.service";
import {Accommodation} from "../../model/accommodation.model";
import {MapInfoWindow, MapMarker} from "@angular/google-maps";
import LatLng = google.maps.LatLng;

@Component({
  selector: 'app-map-search',
  templateUrl: './accommodation-map-search.html',
  styleUrls: ['./accommodation-map-search.css']
})
export class AccommodationMapSearch {

  @ViewChildren(MapInfoWindow) infoWindowsView!: QueryList<MapInfoWindow>;

  accommodations: Accommodation[] = [];

  googleMapsOptions: google.maps.MapOptions = {
    disableDoubleClickZoom: true,
    streetViewControl: false,
  };

  markers: any[] = [];
  center: google.maps.LatLngLiteral = {lat: 47, lng: 19};

  constructor(private authService: AuthService) {
    console.log(authService.getUserName())
  }

  public openInfoWindow(marker: MapMarker, windowIndex: number) {
    let curIdx = 0;
    this.infoWindowsView.forEach((window: MapInfoWindow) => {
      window.close();
      if (windowIndex === curIdx) {
        window.open(marker);
        curIdx++;
      } else {
        curIdx++;
      }
    });
  }

  public getStar(accommodation: Accommodation) {
    return accommodation.reviews.map(review => review.star).reduce((a, b) => a + b, 0) / accommodation.reviews.length;
  }

  public filterAccommodations($event: Accommodation[]) {
    this.accommodations = $event;
    this.markers = this.accommodations.map(accommodation => {
      return new google.maps.Marker({
        position: new LatLng(accommodation.lat, accommodation.lng)
      });
    })
  }
}
