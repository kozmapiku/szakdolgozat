import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule, Routes} from "@angular/router";
import {HomeComponent} from "../home/home.component";
import {LoginComponent} from "../auth/login/login.component";
import {CreateAccommodationComponent} from "../create-accommodation/create-accommodation.component";
import {RegisterComponent} from "../auth/register/register.component";
import {AccommodationDetailComponent} from "../accommodation-detail/accommodation-detail.component";

const routes: Routes = [
  {path: 'home', component: HomeComponent},
  {path: '', redirectTo: '/home', pathMatch: 'full'},
  {path: 'login', component: LoginComponent},
  {path: 'accommodation/create', component: CreateAccommodationComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'accommodation/:id/details', component: AccommodationDetailComponent}
];

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    RouterModule.forRoot(routes)
  ],
  exports: [RouterModule]
})
export class RoutingModule { }
