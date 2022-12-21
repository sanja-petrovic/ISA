type BloodDonor = {
  firstName: string,
  lastName: string,
  personalId: string,
  gender: string,
  occupation: string,
  institution: string,
  phoneNumber: string,
  homeAddress: string,
  city: string,
  country: string,
  email: string,
  password: string,
  loyaltyStatus: string
}

type MedicalStaff = {
  id: string,
  firstName: string,
  lastName: string,
  personalId: string,
  gender: string,
  occupation: string,
  institution: string,
  phoneNumber: string,
  homeAddress: string,
  city: string,
  country: string,
  email: string,
  password: string,
  bloodBank: any
}

type User = {
  id: string,
  firstName: string,
  lastName: string,
  personalId: string,
  gender: string,
  phoneNumber: string,
  email: string,
  isVerified: boolean
}
type Admin ={
  id: string,
  firstName: string,
  lastName: string,
  personalId: string,
  gender: string,
  phoneNumber: string,
  email: string,
  isVerified: boolean
}
type Credentials = {
  email: string,
  password: string
}

type PasswordDto ={
  oldPassword: string,
  newPassword: string
}

export { BloodDonor, Credentials, PasswordDto, User, MedicalStaff, Admin };
