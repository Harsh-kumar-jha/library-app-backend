package com.backend.restapi.spring_boot_library.configuration;

import com.okta.spring.boot.oauth.Okta;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Value("${okta.oauth2.issuer}")
    private String issuer;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(request -> {
                    var source = new CorsConfiguration();
                    source.setAllowCredentials(true);
                    source.addAllowedOrigin("http://localhost:3000"); // Adjust as needed
                    source.addAllowedHeader("*");
                    source.addAllowedMethod("*");
                    return source;
                }))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/books/secure/**").authenticated()
                        .anyRequest().permitAll())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.decoder(jwtDecoder())));

        // Configure Okta to handle unauthorized responses
        Okta.configureResourceServer401ResponseBody(http);

        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        String issuerUri = issuer ;
        return NimbusJwtDecoder.withJwkSetUri(issuerUri + "/v1/keys").build();
    }
}