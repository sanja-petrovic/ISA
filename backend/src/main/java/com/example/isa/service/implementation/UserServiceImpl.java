package com.example.isa.service.implementation;

import com.example.isa.model.AccountStatus;
import com.example.isa.model.Email;
import com.example.isa.model.Patient;
import com.example.isa.model.User;
import com.example.isa.repository.UserRepository;
import com.example.isa.service.interfaces.UserService;
import com.example.isa.util.EmailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final EmailSender emailSender;

    public UserServiceImpl(UserRepository repository, EmailSender emailSender) {
        this.repository = repository;
        this.emailSender = emailSender;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        } else {
            return user;
        }
    }


    @Override
    public List<User> getAll() {
        return repository.findAll();
    }

    @Override
    public void register(Patient patient) {
        repository.save(patient);
        String link = "http://localhost:4200/verify/" + patient.getEmail();
        String content = "<h1>Thanks for registering!</h1><p>To verify your account, please click on <a href=" + link + ">this link</a>.</p>";
        emailSender.send(new Email(patient.getEmail(), "Verify your registration", content));
    }

    @Override
    public void verifyAccount(User user) {
        user.setVerified(true);
        repository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        User user = repository.findByEmail(username);
        return user;
    }
}
