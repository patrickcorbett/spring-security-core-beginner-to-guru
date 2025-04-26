package guru.sfg.brewery.web.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.util.DigestUtils;

public class PasswordEncodingTests {

    static final String PASSWORD = "password";

    @Test
    void testBcrypt() {
        PasswordEncoder bcrypt = new BCryptPasswordEncoder(10); // The strength of the encoder can be provided as a parameter, default is 10!

        System.out.println(bcrypt.encode(PASSWORD));
        // this hash isn't the same as the previous as a random salt is being used
        System.out.println(bcrypt.encode(PASSWORD));
        System.out.println(bcrypt.encode("guru"));
    }

    @Test
    void testSha256() {
        PasswordEncoder sha256 = new StandardPasswordEncoder();

        System.out.println(sha256.encode(PASSWORD));
        // this hash isn't the same as the previous as a random salt is being used
        System.out.println(sha256.encode(PASSWORD));
    }
    
    @Test
    void testLdap() {
        PasswordEncoder ldap = new LdapShaPasswordEncoder();
        System.out.println(ldap.encode(PASSWORD));

        // this hash isn't the same as the previous as a random salt is being used
        System.out.println(ldap.encode(PASSWORD));
        // The Salt is random but is included in the hashed result and can be extracted by the algorithm from the hashed password for use when matching

        System.out.println(ldap.encode("tiger"));


        // Spring security would handle the following logic
        // 1 - the given password will be encoded
        String encodedPassword = ldap.encode(PASSWORD);

        // 2 - Spring would call the Password enocder Matches method
        // the previously encoded password would come some datasource, the Spring UserDetailsProvider will retrieve this
        // the provided password will be matched against the saved hashed password,
        Assertions.assertTrue(ldap.matches(PASSWORD, encodedPassword));
    }

    @Test
    void testNoOp() {
        PasswordEncoder noOp = NoOpPasswordEncoder.getInstance();
        System.out.println(noOp.encode(PASSWORD));
    }

    @Test
    void hashingExample() {
        System.out.println(DigestUtils.md5DigestAsHex(PASSWORD.getBytes()));
        System.out.println(DigestUtils.md5DigestAsHex(PASSWORD.getBytes()));

        String salted = PASSWORD + "ThisIsMySALTVALUE";
        System.out.println(DigestUtils.md5DigestAsHex(salted.getBytes()));
    }

}
