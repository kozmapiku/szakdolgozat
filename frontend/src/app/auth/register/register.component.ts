import { Component, OnInit } from '@angular/core';
import {AuthService} from "../auth.service";
import {Router} from "@angular/router";
import {
  AbstractControl,
  FormBuilder,
  FormControl,
  FormGroup, FormGroupDirective, NgForm,
  ValidationErrors,
  ValidatorFn,
  Validators
} from "@angular/forms";
import {ErrorStateMatcher} from "@angular/material/core";
import Validation from "../../utils/validation";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  form!: FormGroup;

  constructor(private fb: FormBuilder, private authService: AuthService, private router: Router) { }

  ngOnInit(): void {
    this.form = this.fb.group({
      email: [null, [Validators.required, Validators.pattern("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$")]],
      firstName: [null, [Validators.required]],
      lastName: [null, [Validators.required]],
      password: [null, [Validators.required, Validators.minLength(8)]],
      confirmPassword: [null],
    }, { validators: [Validation.match('password', 'confirmPassword')] });
  }

  register(form: FormGroup): void {
    this.authService.register(form.get('email')?.value, form.get('firstName')?.value, form.get('lastName')?.value, form.get('password')?.value).subscribe({
      next: (data)=> {
        console.log(JSON.stringify(data));
        if(data.status == "OK") {
          this.router.navigateByUrl("/login");
        }
      },
      error: (error) => {
        console.log("Error " + JSON.stringify(error));
      }
    }
    )
  }

}
