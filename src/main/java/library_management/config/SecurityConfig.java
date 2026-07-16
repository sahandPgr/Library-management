package library_management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import library_management.service.CustomUserDetailsService;

@Configuration
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider() {

      DaoAuthenticationProvider provider =
            new DaoAuthenticationProvider(userDetailsService);

        provider.setPasswordEncoder(passwordEncoder());

        return provider;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(
                                "/login",
                                "/css/**",
                                "/js/**",
                                "/images/**"
                        ).permitAll()

                        .requestMatchers("/users/**")
                        .hasRole("ADMIN")

                        .requestMatchers(
                                "/books/**",
                                "/authors/**",
                                "/categories/**",
                                "/publishers/**",
                                "/borrows/**"
                        ).hasAnyRole("ADMIN", "LIBRARIAN")

                        .anyRequest()
                        .authenticated()
                )

                .formLogin(form -> form

                        .loginPage("/login")

                        .defaultSuccessUrl("/", true)

                        .permitAll()
                )

                .logout(logout -> logout

                        .logoutSuccessUrl("/login?logout")

                        .permitAll()
                );

        return http.build();
    }

}