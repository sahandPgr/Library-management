package library_management.service;

import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import library_management.entity.User;
import library_management.entity.UserRole;
import library_management.repository.UserRepository;
import library_management.security.CustomUserDetails;

@Service
public class CustomUserDetailsService implements UserDetailsService {

        private final UserRepository userRepository;

        public CustomUserDetailsService(UserRepository userRepository) {
                this.userRepository = userRepository;
        }

        @Override
        public UserDetails loadUserByUsername(String email)
                        throws UsernameNotFoundException {

                User user = userRepository.findByEmail(email)
                                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
                if (user.getRole() == UserRole.STUDENT) {

                        throw new DisabledException(
                                        "Student accounts cannot access this system.");
                }
                return new CustomUserDetails(user);
        }
}