package com.example.isa.repository;

import com.example.isa.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    User findByEmail(String username);
    Optional<User> findAllByPersonalId(String personalId);
    @Query("from User u where u.firstName like CONCAT('%', ?1, '%') and u.lastName like CONCAT('%', ?2, '%')")
    List<User> search(String name, String lastName);
}
