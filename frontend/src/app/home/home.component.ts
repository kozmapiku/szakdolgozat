import {Component, OnInit} from '@angular/core';
import {AuthService} from "../auth/auth.service";
import {Accommodation} from "../model/accommodation.model";
import {AccommodationService} from "../service/accommodation.service";
import {FormBuilder, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  accommodations: Accommodation[] = [];
  public form!: FormGroup;

  constructor(private fb: FormBuilder, private authService: AuthService, private accommodationService: AccommodationService) {

  }

  ngOnInit(): void {
    this.getAccommodationsFromServer();
    this.form = this.fb.group({
      name: [null, []],
      guests: [null, []],
      fromDate: [null, []],
      endDate: [null, []]
    });
  }

  deleteRange() {
    this.form.get("fromDate")?.reset();
    this.form.get("endDate")?.reset();
  }

  search(form: FormGroup) {
    let from = Date.parse(this.form.get("fromDate")?.value);
    let end = Date.parse(this.form.get("endDate")?.value);
    this.accommodationService.getFiltered(form.get("name")?.value, form.get("guests")?.value, from, end).subscribe({
      next: (data) => {
        this.accommodations = data.data;
        console.log(data.data)
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
        console.log(data.data)
      },
      error: (error) => {
        console.log("Error " + JSON.stringify(error));
      }
    })
  }
}
