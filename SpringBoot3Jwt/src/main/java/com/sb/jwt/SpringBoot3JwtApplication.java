package com.sb.jwt;

import com.sb.jwt.entity.MyRoles;
import com.sb.jwt.entity.MyUser;
import com.sb.jwt.repository.MyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SpringBoot3JwtApplication implements CommandLineRunner {

    @Autowired
    private MyUserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(SpringBoot3JwtApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        MyUser myAdmin = userRepository.findByRole(MyRoles.ADMIN);
        if (null == myAdmin) {
            MyUser user = new MyUser();
            user.setFirstname("fNameAdmin");
            user.setLastname("lNameAdmin");
            user.setEmail("my.admin@gmail.com");
            user.setRole(MyRoles.ADMIN);
            user.setPassword(new BCryptPasswordEncoder().encode("myAdminPass"));
            userRepository.save(user);
        }
    }
}
