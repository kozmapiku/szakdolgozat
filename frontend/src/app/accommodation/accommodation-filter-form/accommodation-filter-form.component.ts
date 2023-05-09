import {Component, EventEmitter, Output} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {AccommodationFilter} from "../../model/accommodation_filter.model";
import {Accommodation} from "../../model/accommodation.model";
import {AccommodationService} from "../../service/accommodation.service";
import {AuthService} from "../../service/auth.service";
import {MatCheckboxChange} from "@angular/material/checkbox";

@Component({
  selector: 'app-accommodation-filter-form',
  templateUrl: './accommodation-filter-form.component.html',
  styleUrls: ['./accommodation-filter-form.component.css']
})
export class AccommodationFilterFormComponent {

  public form: FormGroup = this.fb.group({
    name: ["", []],
    address: ["", []],
    guests: [null, []],
    fromDate: [null, []],
    endDate: [null, []]
  });

  @Output() filterChanged: EventEmitter<Accommodation[]> = new EventEmitter<Accommodation[]>();

  public showOwn: boolean = false;

  constructor(public fb: FormBuilder, public accommodationService: AccommodationService, public authService: AuthService) {
    this.search(new AccommodationFilter(this.showOwn));
  }


  public search(filter: AccommodationFilter) {
    this.accommodationService.getAccommodations(filter).subscribe({
      next: (response) => {
        let accommodations = response.data
        this.filterChanged.emit(accommodations);
      },
      error: (error) => {
        console.log("Error " + JSON.stringify(error));
      }
    })
  }

  public deleteRange() {
    this.form.get("fromDate")?.reset();
    this.form.get("endDate")?.reset();
    this.createFilter(this.form);
  }

  public createFilter(form: FormGroup) {
    let filter = new AccommodationFilter(this.showOwn);
    filter.name = form.controls['name'].value;
    filter.address = form.controls['address'].value;
    filter.guests = form.controls['guests'].value;
    filter.fromDate = Date.parse(form.controls['fromDate'].value);
    filter.endDate = Date.parse(form.controls['endDate'].value);
    this.search(filter);
  }

  public valueChange($event: MatCheckboxChange) {
    this.showOwn = $event.checked;
    this.createFilter(this.form);
  }
}
