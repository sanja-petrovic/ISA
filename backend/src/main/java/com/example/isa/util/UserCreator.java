package com.example.isa.util;

import com.example.isa.model.*;
import com.example.isa.repository.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
<<<<<<< HEAD
=======
import java.text.SimpleDateFormat;
>>>>>>> 5b6ff167894d390116d4079d97de2b7b1fba52f7
import java.util.Date;
import java.util.UUID;

@Component
public class UserCreator {
    private final RoleRepository roleRepository;
    private final MedicalStaffRepository medicalStaffRepository;
    private final BloodDonorRepository bloodDonorRepository;
    private final AdminRepository adminRepository;
    private final BloodBankRepository bloodBankRepository;
    private final PasswordEncoder passwordEncoder;

    public UserCreator(RoleRepository roleRepository, MedicalStaffRepository medicalStaffRepository, BloodDonorRepository bloodDonorRepository, AdminRepository adminRepository, BloodBankRepository bloodBankRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.medicalStaffRepository = medicalStaffRepository;
        this.bloodDonorRepository = bloodDonorRepository;
        this.adminRepository = adminRepository;
        this.bloodBankRepository = bloodBankRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void create() {
        Role roleAdmin = new Role("ROLE_ADMIN");
        Role roleDonor = new Role("ROLE_DONOR");
        Role roleStaff = new Role("ROLE_STAFF");
        roleRepository.save(roleAdmin);
        roleRepository.save(roleDonor);
        roleRepository.save(roleStaff);
        Admin admin = new Admin("0000000000000", "isaproject202223@gmail.com", passwordEncoder.encode("ISA202223"),passwordEncoder.encode("ISA202223"), "Admin", "Admin", "0000000000", Gender.FEMALE, true, roleAdmin);
        adminRepository.save(admin);
        BloodDonor bloodDonor = new BloodDonor("2801001123456", "blooddonor@gmail.com", passwordEncoder.encode("BLOODDONOR"), "Jane", "Doe", "0681234567", Gender.FEMALE, true, "Student", new Address("Ulica 15", "Novi Sad", "Srbija"), "FTN", roleDonor);
        bloodDonor.setId(UUID.fromString("16e4a8c2-3e86-4e93-825f-24e36cb29645"));
        bloodDonorRepository.save(bloodDonor);

        BloodBank bloodBank = new BloodBank("Prva prava banka krvi", new Address("Daniciceva 15", "Novi Sad", "Srbija"), "Najopremljenija banka krvi ovih prostora", new Interval(new Date(12345), new Date(12345)), 4.8);
        bloodBankRepository.save(bloodBank);
        MedicalStaff medicalStaff1 = new MedicalStaff("0101010101010", "staff1@gmail.com", passwordEncoder.encode("STAFF1"), "Good", "Doctor", "0101010101", Gender.FEMALE, true, roleStaff, bloodBank);
        medicalStaffRepository.save(medicalStaff1);
        BloodBank bank = new BloodBank("banka puno krvi",new Address("Vojvode Misica 10","Kragujevac","Srbija"),new Interval(new Date(),new Date()),"Vampiri ovde povremeno borave",3.5);
        bank.setId(UUID.fromString("16e4a8c2-3e86-4e93-825f-24e36cb29655"));
        bloodBankRepository.save(bank);
        MedicalStaff medicalStaff2 = new MedicalStaff("0101010101010", "staff2@gmail.com", passwordEncoder.encode("STAFF2"), "Good", "Doctor", "0101010101", Gender.FEMALE, true, roleStaff, bank );
        medicalStaffRepository.save(medicalStaff2);
    }
}
