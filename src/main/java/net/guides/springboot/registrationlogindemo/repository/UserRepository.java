package net.guides.springboot.registrationlogindemo.repository;

import net.guides.springboot.registrationlogindemo.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);
    void deleteByEmail(String email);
    void deleteById(long id);
}

