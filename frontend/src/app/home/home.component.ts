import { Component, OnInit } from '@angular/core';
import {AuthService} from "../auth/auth.service";
import {Accommodation} from "../model/accommodation.model";
import {AccommodationService} from "../service/accommodation.service";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  accommodations: Accommodation[] = [];

  constructor(private authService:AuthService, private accommodationService: AccommodationService) { }

  ngOnInit(): void {
    this.getAccommodationsFromServer()
  }

  private getAccommodationsFromServer() {
    this.accommodationService.getAll().subscribe({
      next: (data)=> {
        this.accommodations = data.data;
      },
      error: (error) => {
        console.log("Error " + JSON.stringify(error));
      }
    })
  }
}
