import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../service/auth.service";
import {Router} from "@angular/router";
import {UntypedFormBuilder, UntypedFormGroup, Validators} from "@angular/forms";
import Validation from "../../utils/validation";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  form!: UntypedFormGroup;

  constructor(private fb: UntypedFormBuilder, private authService: AuthService, private router: Router) {
  }

  ngOnInit(): void {
    this.form = this.fb.group({
      email: [null, [Validators.required, Validators.pattern("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$")]],
      firstName: [null, [Validators.required]],
      lastName: [null, [Validators.required]],
      password: [null, [Validators.required, Validators.minLength(8)]],
      confirmPassword: [null],
    }, { validators: [Validation.match('password', 'confirmPassword')] });
  }

  register(form: UntypedFormGroup): void {
    this.authService.register(form.get('email')?.value, form.get('firstName')?.value, form.get('lastName')?.value, form.get('password')?.value).subscribe({
        next: (data) => {
          console.log(JSON.stringify(data));
          if (data.status == "OK") {
            this.router.navigateByUrl("/login");
          }
        },
        error: (error) => {
          alert(error.error.error);
          console.log("Error " + JSON.stringify(error));
        }
    }
    )
  }

}
