import {BloodBank} from "./BloodBank";

type BloodRequest = {
  id: string,
  bloodBank: BloodBank,
  amount: number,
  bloodType: string,
  urgent: boolean,
  sendOnDate: string
}


export { BloodRequest }
