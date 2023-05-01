import {Component, OnInit, QueryList, ViewChildren} from '@angular/core';
import {UntypedFormBuilder, UntypedFormGroup} from "@angular/forms";
import {AuthService} from "../auth/auth.service";
import {AccommodationService} from "../service/accommodation.service";
import {Accommodation} from "../model/accommodation.model";
import {MapInfoWindow, MapMarker} from "@angular/google-maps";
import LatLng = google.maps.LatLng;

@Component({
  selector: 'app-map-search',
  templateUrl: './map-search.component.html',
  styleUrls: ['./map-search.component.css']
})
export class MapSearchComponent implements OnInit {
  @ViewChildren(MapInfoWindow) infoWindowsView!: QueryList<MapInfoWindow>;
  accommodations: Accommodation[] = [];
  public form!: UntypedFormGroup;

  googleMapsOptions: google.maps.MapOptions = {
    disableDoubleClickZoom: true,
    streetViewControl: false,
  };
  markers!: any[];
  center: google.maps.LatLngLiteral = {lat: 47, lng: 19};

  constructor(private fb: UntypedFormBuilder, private authService: AuthService, private accommodationService: AccommodationService) {
  }

  ngOnInit(): void {
    this.getAccommodationsFromServer();
    this.form = this.fb.group({
      name: [null, []],
      address: [null, []],
      guests: [null, []],
      fromDate: [null, []],
      endDate: [null, []]
    });
  }

  public deleteRange() {
    this.form.get("fromDate")?.reset();
    this.form.get("endDate")?.reset();
    this.search(this.form);
  }

  public search(form: UntypedFormGroup) {
    let from = Date.parse(this.form.get("fromDate")?.value);
    let end = Date.parse(this.form.get("endDate")?.value);
    this.accommodationService.getFiltered(form.get("name")?.value, form.get("address")?.value, form.get("guests")?.value, from, end).subscribe({
      next: (data) => {
        this.accommodations = data.data;
        this.markers = this.accommodations.map(accommodation => {
          return new google.maps.Marker({
            position: new LatLng(accommodation.lat, accommodation.lng)
          })
        })
      },
      error: (error) => {
        console.log("Error " + JSON.stringify(error));
      }
    })
  }

  openInfoWindow(marker: MapMarker, windowIndex: number) {
    let curIdx = 0;
    console.log(this.infoWindowsView)
    console.log(marker)
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

  getStar(accommodation: Accommodation) {
    return accommodation.reviews.map(review => review.star).reduce((a, b) => a + b, 0) / accommodation.reviews.length;
  }

  private getAccommodationsFromServer() {
    this.accommodationService.getAll().subscribe({
      next: (data) => {
        this.accommodations = data.data;
        this.markers = this.accommodations.map(accommodation => {
          return new google.maps.Marker({
            position: new LatLng(accommodation.lat, accommodation.lng)
          });
        })
      },
      error: (error) => {
        console.log("Error " + JSON.stringify(error));
      }
    })
  }
}
