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


type PasswordDto ={
  personalId: string,
  oldPassword: string,
  newPassword: string
}

export { Patient, Credentials, PasswordDto, User};

