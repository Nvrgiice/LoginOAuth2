package com.example.loginoauth2.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ApothecaireUserService
        implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    // Les pseudos GitHub qui ont le rôle "Maître" donc moi
    private static final Set<String> MAITRES = Set.of("Nvrgiice");

    // On délègue d'abord le travail standard (récupérer le profil GitHub)
    private final DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request)
            throws OAuth2AuthenticationException {

        // 1. Spring récupère le profil GitHub normalement
        OAuth2User user = delegate.loadUser(request);

        // 2. On lit le pseudo GitHub
        String login = user.getAttribute("login");

        // 3. On ajoute notre rôle métier
        Set<GrantedAuthority> authorities = new HashSet<>(user.getAuthorities());
        if (MAITRES.contains(login)) {
            authorities.add(new SimpleGrantedAuthority("ROLE_MAITRE"));
        } else {
            authorities.add(new SimpleGrantedAuthority("ROLE_APPRENTI"));
        }

        // 4. On renvoie l'utilisateur enrichi (clé "login" = nom d'affichage)
        return new DefaultOAuth2User(authorities, user.getAttributes(), "login");
    }
}