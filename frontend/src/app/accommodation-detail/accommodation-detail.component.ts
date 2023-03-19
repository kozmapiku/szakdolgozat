import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {AccommodationService} from "../service/accommodation.service";
import {Accommodation} from "../model/accommodation.model";
import {DateRange, MatCalendarCellCssClasses} from "@angular/material/datepicker";
import {MatDialog} from "@angular/material/dialog";
import {ReservationDialogComponent} from "./reservation-dialog/reservation-dialog.component";

@Component({
  selector: 'app-accommodation-detail',
  templateUrl: './accommodation-detail.component.html',
  styleUrls: ['./accommodation-detail.component.css']
})
export class AccommodationDetailComponent implements OnInit {

  id!: string | null;
  accommodation!: Accommodation;
  selectedDateRange: DateRange<Date> = new DateRange<Date>(null, null);
  guests!: number;

  constructor(private route: ActivatedRoute, private accommodationService: AccommodationService, public dialog: MatDialog) {
  }

  ngOnInit(): void {
    this.id = this.route.snapshot.paramMap.get('id');
    this.getDetailsFromServer()
  }

  public getDetailsFromServer() {
    this.accommodationService.getDetails(this.id).subscribe({
      next: (data) => {
        console.log(JSON.stringify(data));
        this.accommodation = data.data;
      },
      error: (error) => {
        console.log("Error " + JSON.stringify(error));
      }
    });
  }

  dateClass() {
    return (date: Date): MatCalendarCellCssClasses => {
      const highlightDate = this.accommodation.announceDateList
          .flatMap(announceDate => this.getDates(new Date(announceDate.from), new Date(announceDate.end)))
          .some(d => d.getDate() === date.getDate() && d.getMonth() === date.getMonth() && d.getFullYear() === date.getFullYear());

      return highlightDate ? 'available-date' : 'reserved-date';
    };
  }

  onSelectedChange(date: Date | null): void {
    if (date == null) {
      return;
    }
    if (
        this.selectedDateRange &&
        this.selectedDateRange.start &&
        date > this.selectedDateRange.start &&
        !this.selectedDateRange.end
    ) {
      this.selectedDateRange = new DateRange(
          this.selectedDateRange.start,
          date
      );
    } else {
      this.selectedDateRange = new DateRange(date, null);
    }
  }

  openReservationDialog() {
    const dialogRef = this.dialog.open(ReservationDialogComponent, {
      width: '400px',
      data: {
        from: this.selectedDateRange.start,
        end: this.selectedDateRange.end,
        maxGuests: this.accommodation.max_guests
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      this.guests = result;
      this.reserve();
    });
  }

  dateSelected() {
    return this.selectedDateRange.start != null && this.selectedDateRange.end != null;
  }

  private getDates(startDate: Date, stopDate: Date) {
    var dateArray = [];
    var currentDate = startDate;
    while (currentDate <= stopDate) {
      dateArray.push(new Date(currentDate));
      currentDate = this.addDay(currentDate);
    }
    return dateArray;
  }

  private addDay(date: Date): Date {
    var newDate = new Date(date);
    newDate.setDate(date.getDate() + 1)
    return newDate;
  }

  private reserve() {
    if (this.guests == null || this.selectedDateRange.start == null || this.selectedDateRange.end == null) {
      return;
    }
    this.accommodationService.reserve(this.accommodation.id, Date.parse(this.selectedDateRange.start.toDateString()),
        Date.parse(this.selectedDateRange.end.toDateString()), this.guests).subscribe({
      next: (data) => {
        console.log(JSON.stringify(data));
      },
      error: (error) => {
        console.log("Error " + JSON.stringify(error));
      }
    })
  }
}

export interface ReservationDialogData {
  from: Date;
  end: Date;
  maxGuests: number;
  guests: number;
}
