import {BloodBank} from "./BloodBank";
import {BloodDonor} from "./Users";

type Appointment = {
  id: string,
  status: string,
  dateTime: Date,
  duration: number,
  bloodBank: BloodBank,
  bloodDonor: BloodDonor,
  bloodDonorId: String,
  bloodBankId: String,
  bloodBankTitle: String
}

export { Appointment }
