import {Component, OnInit} from '@angular/core';
import {Accommodation} from "../model/accommodation.model";
import {AuthService} from "../auth/auth.service";
import {AccommodationService} from "../service/accommodation.service";

@Component({
  selector: 'app-my-accommodations',
  templateUrl: './my-accommodations.component.html',
  styleUrls: ['./my-accommodations.component.css']
})
export class MyAccommodationsComponent implements OnInit {

  accommodations: Accommodation[] = [];

  constructor(private authService: AuthService, private accommodationService: AccommodationService) {
  }

  ngOnInit(): void {
    this.getMyAccommodationsFromServer();
  }

  private getMyAccommodationsFromServer() {
    this.accommodationService.getMyAccommodations().subscribe({
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
