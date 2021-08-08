package guru.sfg.brewery.web.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

public class PasswordEncodingTest {

    static final String PASSWORD = "password";

    @Test
    void testBCrypt() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(15);
        System.out.println(passwordEncoder.encode(PASSWORD));
        System.out.println(passwordEncoder.encode(PASSWORD));
        System.out.println(passwordEncoder.encode("tiger"));
    }

    @Test
    void testSha256() {
        PasswordEncoder passwordEncoder = new StandardPasswordEncoder();
        System.out.println(passwordEncoder.encode(PASSWORD));
        System.out.println(passwordEncoder.encode(PASSWORD));
    }

    @Test
    void testLDAP() {
        PasswordEncoder passwordEncoder = new LdapShaPasswordEncoder();
        System.out.println(passwordEncoder.encode(PASSWORD));
        System.out.println(passwordEncoder.encode(PASSWORD));
        System.out.println(passwordEncoder.encode("tiger"));

        String encodedPassword = passwordEncoder.encode(PASSWORD);

        Assertions.assertTrue(passwordEncoder.matches(PASSWORD, encodedPassword));
    }

    @Test
    void testNoOp() {
        PasswordEncoder passwordEncoder = NoOpPasswordEncoder.getInstance();
        System.out.println(passwordEncoder.encode(PASSWORD));
    }

    @Test
    void hashingExample() {
        System.out.println(DigestUtils.md5DigestAsHex(PASSWORD.getBytes(StandardCharsets.UTF_8)));

        String salted = PASSWORD + "ThisIsMySaltValue";

        System.out.println(DigestUtils.md5DigestAsHex(salted.getBytes(StandardCharsets.UTF_8)));
    }
}
