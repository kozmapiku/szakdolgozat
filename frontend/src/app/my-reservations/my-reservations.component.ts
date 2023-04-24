import {Component, OnInit} from '@angular/core';
import {AuthService} from "../auth/auth.service";
import {ReservationService} from "../service/reservation.service";
import {Reservation} from "../model/reservation.model";

@Component({
  selector: 'app-my-reservations',
  templateUrl: './my-reservations.component.html',
  styleUrls: ['./my-reservations.component.css']
})
export class MyReservationsComponent implements OnInit {

  reservations: Reservation[] = [];

  constructor(private authService: AuthService, private reservationService: ReservationService) {
  }

  ngOnInit(): void {
    this.getMyReservationsFromServer();
  }

  private getMyReservationsFromServer() {
    this.reservationService.getMyReservations().subscribe({
      next: (data) => {
        this.reservations = data.data;
        console.log(data.data)
      },
      error: (error) => {
        console.log("Error " + JSON.stringify(error));
      }
    })
  }

  public expired(index: number) {
    return this.reservations[index].end < Date.parse(new Date().toDateString());
  }
}
