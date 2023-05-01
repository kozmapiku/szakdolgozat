import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule, Routes} from "@angular/router";
import {AccommodationSearchComponent} from "../accommodation/accommodation-search/accommodation-search.component";
import {LoginComponent} from "../authorization/login/login.component";
import {AccommodationCreateComponent} from "../accommodation/accommodation-create/accommodation-create.component";
import {RegisterComponent} from "../authorization/register/register.component";
import {AccommodationDetailComponent} from "../accommodation/accommodation-detail/accommodation-detail.component";
import {AccommodationOwnComponent} from "../accommodation/accommodation-own/accommodation-own.component";
import {ReviewOwnComponent} from "../review/review-own/review-own.component";
import {ReservationOwnComponent} from "../reservation/reservation-own/reservation-own.component";
import {ProfileComponent} from "../authorization/profile/profile.component";
import {ReservationDetailsComponent} from "../reservation/reservation-details/reservation-details.component";
import {AccommodationMapSearch} from "../accommodation/accommodation-map-search/accommodation-map-search";
import {AccommodationUpdateComponent} from "../accommodation/accommodation-update/accommodation-update.component";

const routes: Routes = [
  {path: 'home', component: AccommodationSearchComponent},
  {path: '', redirectTo: '/home', pathMatch: 'full'},
  {path: 'login', component: LoginComponent},
  {path: 'accommodation/create', component: AccommodationCreateComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'accommodation/:id/details', component: AccommodationDetailComponent},
  {path: 'accommodation/mine', component: AccommodationOwnComponent},
  {path: 'review/mine', component: ReviewOwnComponent},
  {path: 'reservation/all', component: ReservationOwnComponent},
  {path: 'profile', component: ProfileComponent},
  {path: 'reservation/:id/details', component: ReservationDetailsComponent},
  {path: 'map-search', component: AccommodationMapSearch},
  {path: 'accommodation/:id/modify', component: AccommodationUpdateComponent}
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
