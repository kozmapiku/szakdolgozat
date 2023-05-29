import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {ReservationDialogData} from "../../model/reservation_dialog.model";

@Component({
	selector: 'app-reservation-dialog',
	templateUrl: './reservation-dialog.component.html',
	styleUrls: ['./reservation-dialog.component.css']
})
export class ReservationDialogComponent implements OnInit {

	constructor(
		public dialogRef: MatDialogRef<ReservationDialogComponent>,
		@Inject(MAT_DIALOG_DATA) public data: ReservationDialogData) {
	}

	onNoClick(): void {
		this.dialogRef.close();
	}

	ngOnInit(): void {
	}

}
