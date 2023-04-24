import {Component, OnInit} from '@angular/core';
import {AuthService} from "../auth/auth.service";
import {Accommodation} from "../model/accommodation.model";
import {AccommodationService} from "../service/accommodation.service";
import {UntypedFormBuilder, UntypedFormGroup} from "@angular/forms";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  accommodations: Accommodation[] = [];
  public form!: UntypedFormGroup;

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
      },
      error: (error) => {
        console.log("Error " + JSON.stringify(error));
      }
    })
  }

  private getAccommodationsFromServer() {
    this.accommodationService.getAll().subscribe({
      next: (data) => {
        this.accommodations = data.data;
      },
      error: (error) => {
        console.log("Error " + JSON.stringify(error));
      }
    })
  }
}
