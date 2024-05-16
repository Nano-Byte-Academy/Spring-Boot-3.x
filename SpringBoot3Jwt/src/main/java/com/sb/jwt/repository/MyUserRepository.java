package com.sb.jwt.repository;

import com.sb.jwt.entity.MyRoles;
import com.sb.jwt.entity.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MyUserRepository extends JpaRepository<MyUser, Integer> {

    Optional<MyUser> findByEmail(String username);

    MyUser findByRole(MyRoles role);

}
