import {Component, OnInit} from '@angular/core';
import {UntypedFormArray, UntypedFormBuilder, UntypedFormGroup, Validators} from "@angular/forms";
import {AccommodationService} from "../service/accommodation.service";
import {NgxImageCompressService} from "ngx-image-compress";
import {Router} from "@angular/router";

@Component({
  selector: 'app-create-accommodation',
  templateUrl: './create-accommodation.component.html',
  styleUrls: ['./create-accommodation.component.css']
})
export class CreateAccommodationComponent implements OnInit {

  form!: UntypedFormGroup;
  city_list: string[] = [];
  listOfFiles: File[] = [];
  previews: string[] = [];
  listOfDates: Array<{ from: number, end: number, price: number }> = [];
  primaryImageIndex: number | null = null;

  constructor(private fb: UntypedFormBuilder,
              private imageCompress: NgxImageCompressService,
              private accommodationService: AccommodationService,
              private router: Router) {
    accommodationService.getCities().subscribe(
      data => this.city_list = data.data)
  }

  get announceDates() {
    return this.form.controls["announceDates"] as UntypedFormArray;
  }

  ngOnInit(): void {
    this.form = this.fb.group({
      name: [null, [Validators.required, Validators.minLength(6)]],
      city: [null, [Validators.required]],
      address: [null, [Validators.required]],
      maxGuest: [null, [Validators.required, Validators.max(100), Validators.min(1)]],
      announceDates: this.fb.array([this.fb.group({
        fromDate: [null, [Validators.required]],
        endDate: [null, [Validators.required]],
        price: [null, [Validators.required]],
      })]),
      imageInput: [null, [Validators.required]]
    });

    //TODO: Remove
    this.form.get("name")?.setValue("Példa szállás");
    this.form.get("address")?.setValue("Ez egy cím");
    this.form.get("maxGuest")?.setValue(4);
  }

  addAnnounceDate() {
    const announceDateForm = this.fb.group({
      fromDate: [null, [Validators.required]],
      endDate: [null, [Validators.required]],
      price: [null, [Validators.required]],
    });
    this.announceDates.push(announceDateForm);
  }

  deleteAnnounceDate(index: number) {
    if (this.announceDates.length > 1)
      this.announceDates.removeAt(index);
  }

  selectFile(event: any) {
    console.log(event.target.files.length);
    this.listOfFiles.push(event.target.files[0]);
    const reader = new FileReader();

    reader.onload = (e: any) => {
      this.previews.push(e.target.result);
    };

    reader.readAsDataURL(event.target.files[0]);
  }

  removeSelectedFile(index: number) {
    this.listOfFiles.splice(index, 1);
    this.previews.splice(index, 1);
    if (this.primaryImageIndex === index)
      this.primaryImageIndex = null;
    if (this.listOfFiles.length == 0)
      this.form.get("imageInput")?.reset();
  }

  setPrimaryImage(index: number) {
    this.primaryImageIndex = index;
    console.log(index);
  }

  async saveAccommodation(form: UntypedFormGroup) {
    const formData = new FormData()
    console.log(this.announceDates);
    formData.append('accommodation', JSON.stringify({
      name: form.get("name")?.value,
      address: form.get("address")?.value,
      max_guests: form.get("maxGuest")?.value,
      city: form.get("city")?.value,
      announceDateList: this.mapAnnounceDates(),
      mainImageIndex: this.primaryImageIndex
    }))
    this.listOfFiles.forEach(f => formData.append('files', f));
    this.uploadToServer(formData);
  }

  private uploadToServer(formData: FormData) {
    this.accommodationService
      .newAccommodation(formData)
      .subscribe({
        next: (data) => {
          console.log(data)
          alert(data.data);
          this.router.navigateByUrl("/");
        },
        error: (error) => {
          console.log("Error " + JSON.stringify(error));
        }
      });
  }

  private mapAnnounceDates(): Array<string> {
    return this.announceDates.value.map((announceDate: { fromDate: string, endDate: string, price: number }) => {
      return {from: Date.parse(announceDate.fromDate), end: Date.parse(announceDate.endDate), price: announceDate.price}
    })
  }
}
