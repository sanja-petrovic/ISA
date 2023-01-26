import { BloodRequest } from "../model/BloodRequest";
import {Injectable} from "@angular/core";
@Injectable({
  providedIn: 'root'
})
export class DataService {
  public bloodRequest: BloodRequest;
}
