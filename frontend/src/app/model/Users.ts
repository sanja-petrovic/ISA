type Patient = {
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
  password: string
}

type MedicalStaff = {
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
  bloodBank: string
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

type Credentials = {
  email: string,
  password: string
}


export { Patient, Credentials, User, MedicalStaff };
