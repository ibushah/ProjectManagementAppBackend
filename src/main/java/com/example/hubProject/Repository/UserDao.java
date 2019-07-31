package com.example.hubProject.Repository;

import com.example.hubProject.DTO.UserDto;
import com.example.hubProject.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserDao extends JpaRepository<User, Long> {



    User findByEmail(String username);



    void deleteById(Long id);
}
