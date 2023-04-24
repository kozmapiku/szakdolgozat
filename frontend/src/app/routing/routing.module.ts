import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule, Routes} from "@angular/router";
import {HomeComponent} from "../home/home.component";
import {LoginComponent} from "../auth/login/login.component";
import {CreateAccommodationComponent} from "../create-accommodation/create-accommodation.component";
import {RegisterComponent} from "../auth/register/register.component";
import {AccommodationDetailComponent} from "../accommodation-detail/accommodation-detail.component";
import {MyAccommodationsComponent} from "../my-accommodations/my-accommodations.component";
import {MyReviewsComponent} from "../my-reviews/my-reviews.component";
import {MyReservationsComponent} from "../my-reservations/my-reservations.component";
import {ProfileComponent} from "../profile/profile.component";
import {ReservationDetailsComponent} from "../reservation-details/reservation-details.component";
import {MapSearchComponent} from "../map-search/map-search.component";

const routes: Routes = [
  {path: 'home', component: HomeComponent},
  {path: '', redirectTo: '/home', pathMatch: 'full'},
  {path: 'login', component: LoginComponent},
  {path: 'accommodation/create', component: CreateAccommodationComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'accommodation/:id/details', component: AccommodationDetailComponent},
  {path: 'accommodation/mine', component: MyAccommodationsComponent},
  {path: 'review/mine', component: MyReviewsComponent},
  {path: 'reservation/all', component: MyReservationsComponent},
  {path: 'profile', component: ProfileComponent},
  {path: 'reservation/:id/details', component: ReservationDetailsComponent},
  {path: 'map-search', component: MapSearchComponent}
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
