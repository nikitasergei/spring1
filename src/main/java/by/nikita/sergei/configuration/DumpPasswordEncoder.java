package by.nikita.sergei.configuration;

import org.springframework.security.crypto.password.PasswordEncoder;

/* This is the class 4 coding user's password */
public class DumpPasswordEncoder implements PasswordEncoder {


    @Override
    public String encode(CharSequence charSequence) {
        return String.format("secret: '%s'", charSequence);
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return false;
    }
}
