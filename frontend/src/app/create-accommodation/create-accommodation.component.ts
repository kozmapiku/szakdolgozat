import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-create-accommodation',
  templateUrl: './create-accommodation.component.html',
  styleUrls: ['./create-accommodation.component.css']
})
export class CreateAccommodationComponent implements OnInit {

  public form!: FormGroup;
  city_list!: any[];

  constructor(private fb: FormBuilder) {
    this.city_list = ["Budapest", "Debrecen"];
  }

  ngOnInit(): void {
    this.form = this.fb.group({
      name: [null, [Validators.required, Validators.minLength(10)]],
      city: [null, [Validators.required, Validators.minLength(10)]],
      address: [null, [Validators.required, Validators.minLength(10)]],

      //email: [null, [Validators.required, Validators.pattern("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$")]],
      //password: [null, [Validators.required, Validators.minLength(6)]],
    });
  }

  saveAccommodation(form: FormGroup) {
    console.log("success");
    //TODO implement
  }
}
