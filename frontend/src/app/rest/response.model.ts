export interface Response<T>{
  timestamp: string;
  status: string;
  data: T;
}
