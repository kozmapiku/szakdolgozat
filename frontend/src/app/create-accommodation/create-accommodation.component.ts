import {Component, OnInit} from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, ValidationErrors, ValidatorFn, Validators} from "@angular/forms";
import {AccommodationService} from "../service/accommodation.service";
import {NgxImageCompressService} from "ngx-image-compress";

@Component({
  selector: 'app-create-accommodation',
  templateUrl: './create-accommodation.component.html',
  styleUrls: ['./create-accommodation.component.css']
})
export class CreateAccommodationComponent implements OnInit {

  public form!: FormGroup;
  city_list: string[] = [];
  selectedFiles!: FileList;
  listOfFiles: any[] = [];
  previews: string[] = [];
  listOfDates: any[] = [];
  base64textString: any[] = [];
  imageNames: string[] = [];
  imageUpload: any[] = [];

  constructor(private fb: FormBuilder,
              private imageCompress: NgxImageCompressService,
              private accommodationService: AccommodationService) {
    accommodationService.getCities().subscribe(
      data => this.city_list = data.data)
  }

  ngOnInit(): void {
    this.form = this.fb.group({
      name: [null, [Validators.required, Validators.minLength(6)]],
      city: [null, [Validators.required]],
      address: [null, [Validators.required]],
      maxGuest: [null, [Validators.required, Validators.max(100), Validators.min(1)]],
      fromDate: [null, [this.customValidatorFn()]],
      endDate: [null, []],
      imageInput: [null, [Validators.required]]
    });
  }

  async saveAccommodation(form: FormGroup) {
    const formData = new FormData()
    formData.append('accommodation', JSON.stringify({
      name: form.get("name")?.value,
      address: form.get("address")?.value,
      max_guests: form.get("maxGuest")?.value,
      city: form.get("city")?.value,
      announceDateList: this.listOfDates
    }))
    this.listOfFiles.forEach(f => formData.append('files', f));
    this.uploadToServer(formData);
  }

  pFileReader(file: any){
    return new Promise((resolve, reject) => {
      let fr = new FileReader();
      fr.onload = () => {
        // @ts-ignore
        this.imageUpload.push({"image" : btoa(fr.result), "name" : file.name});
        resolve(1);
      };
      fr.onerror = reject;
    });
  }

  private customValidatorFn(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const valid: boolean = this.listOfDates.length > 0;
      return valid ? null : {amountMinError: true};
    };
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
  }

  addToDateRangeList() {
    if(this.checkValidDate()) {
      let from = Date.parse(this.form.get("fromDate")?.value);
      let end = Date.parse(this.form.get("endDate")?.value);
      this.listOfDates.push({'fromDate': from , 'endDate': end, 'price': 0})
      this.form.get("fromDate")?.reset();
      this.form.get("endDate")?.reset();
    }
  }

  checkValidDate() : boolean | undefined{
    let from = this.form.get("fromDate");
    let end = this.form.get("endDate");
    return from?.value != null && end?.value != null;
  }

  removeFromDateList(index: number) {
    this.listOfDates.splice(index, 1);
  }

  private uploadToServer(form: FormData) {
    this.accommodationService
      .newAccommodation(form)
      .subscribe({
        next: (data)=> {
          console.log(JSON.stringify(data));
        },
        error: (error) => {
          console.log("Error " + JSON.stringify(error));
        }
      });
  }
}
