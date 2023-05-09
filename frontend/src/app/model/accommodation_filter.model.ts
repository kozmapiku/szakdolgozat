export class AccommodationFilter {
  public name!: string;
  public address!: string;
  public fromDate!: number;
  public endDate!: number;
  public guests!: number;
  public showOwn!: boolean;

  constructor(showOwn: boolean) {
    this.showOwn = showOwn;
  }
}
