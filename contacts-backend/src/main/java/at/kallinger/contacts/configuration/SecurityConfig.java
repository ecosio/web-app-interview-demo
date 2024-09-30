package at.kallinger.contacts.configuration;

import static org.springframework.security.config.Customizer.withDefaults;

import java.time.Duration;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    final CorsConfiguration corsConfig = new CorsConfiguration();

    corsConfig.setAllowedHeaders(List.of("*"));
    corsConfig.setAllowedMethods(List.of("*"));
    corsConfig.setAllowedOrigins(List.of("*"));
    corsConfig.setExposedHeaders(List.of("*"));
    corsConfig.setMaxAge(Duration.ofDays(1));
    //configuration.setAllowedHeaders(ImmutableList.of("Authorization", "Cache-Control", "Content-Type"));

    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", corsConfig);

    http
      .authorizeHttpRequests(authRegistry -> authRegistry.anyRequest().authenticated())
      .httpBasic(withDefaults())
      .csrf(AbstractHttpConfigurer::disable)
      .cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(source));
    return http.build();
  }

  @Bean
  public InMemoryUserDetailsManager userDetailsService() {
    UserDetails user = User
      .withUsername("john")
      .password("p@ssword1")
      .authorities(new SimpleGrantedAuthority("USER"), new SimpleGrantedAuthority("ADMIN"))
      .build();
    return new InMemoryUserDetailsManager(user);
  }

  @Bean
  @SuppressWarnings("deprecation")
  public PasswordEncoder passwordEncoder() {
    return NoOpPasswordEncoder.getInstance();
  }
}
