package com.example.loginoauth2;

import com.example.loginoauth2.service.ApothecaireUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final ApothecaireUserService apothecaireUserService;

    public SecurityConfig(ApothecaireUserService apothecaireUserService) {
        this.apothecaireUserService = apothecaireUserService;
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/css/**", "/images/**").permitAll()            // accueil public
                        .requestMatchers("/gestion/**").hasRole("MAITRE")  // réservé au Maître
                        .anyRequest().authenticated()                 // le reste : connecté
                )
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(apothecaireUserService)      // ← notre service de rôles
                        )
                )
                .logout(logout -> logout            // ← NOUVEAU
                        .logoutSuccessUrl("/")          // après déconnexion → accueil
                        .invalidateHttpSession(true)    // détruit la session serveur
                        .clearAuthentication(true)      // efface l'authentification
                );
        return http.build();
    }
}



