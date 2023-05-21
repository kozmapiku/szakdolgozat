import {Component, OnInit} from '@angular/core';
import {UntypedFormBuilder, UntypedFormGroup, Validators} from "@angular/forms";
import {AuthService} from "../../service/auth.service";
import {Router} from "@angular/router";
import Validation from "../../utils/validation";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  form!: UntypedFormGroup;
  user!: any;

  constructor(private fb: UntypedFormBuilder, private authService: AuthService, private router: Router) {
  }

  ngOnInit(): void {
    this.form = this.fb.group({
      email: [null, [Validators.required, Validators.pattern("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$")]],
      firstName: [null, [Validators.required]],
      lastName: [null, [Validators.required]],
      currentPassword: [null, [Validators.required]],
      password: [null, [Validators.minLength(8)]],
      confirmPassword: [null],
    }, {validators: [Validation.match('password', 'confirmPassword')]});
    this.getUserData();
  }

  update(form: UntypedFormGroup): void {
    this.authService.update(form.get('email')?.value, form.get('firstName')?.value, form.get('lastName')?.value, form.get('currentPassword')?.value, form.get('password')?.value).subscribe({
        next: (data) => {
          console.log(JSON.stringify(data));
          alert("Felhasználói adatok megváltoztatása sikeres!");
          if (data.status == "OK") {
            this.router.navigateByUrl("/home");
          }
        },
        error: (error) => {
          console.log("Error " + JSON.stringify(error));
          alert(error.error.error);
        }
      }
    )
  }

  getUserData(): void {
    this.authService.getUserData().subscribe({
        next: (data) => {
            console.log(JSON.stringify(data));
            this.user = data.data;
            console.log(this.user);
            this.form.get("email")?.setValue(this.user.email);
            this.form.get("firstName")?.setValue(this.user.firstName);
            this.form.get("lastName")?.setValue(this.user.lastName);
        },
        error: (error) => {
          console.log("Error " + JSON.stringify(error));
          alert(error.error.error);
        }
      }
    )
  }

}
