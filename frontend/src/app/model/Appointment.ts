import { BloodBank } from './BloodBank';
import { BloodDonor } from './Users';

export interface Appointment {
  id: string;
  status: string;
  dateTime: Date;
  duration: number;
  bloodBank: BloodBank;
  bloodDonor: BloodDonor;
  bloodDonorId: String;
  bloodBankId: String;
  bloodBankTitle: String;
  qrCode: String;
}
