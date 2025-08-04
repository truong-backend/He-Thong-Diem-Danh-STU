package vn.diemdanh.hethong.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import vn.diemdanh.hethong.service.AdminService;
import vn.diemdanh.hethong.service.UserService;

import java.util.Arrays;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true) // Kích hoạt method-level security
public class WebSecurityConfig {

    @Autowired
    private UserService userService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    public DaoAuthenticationProvider userAuthenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    @Bean
    public DaoAuthenticationProvider adminAuthenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(adminService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return new ProviderManager(Arrays.asList(
                userAuthenticationProvider(),
                adminAuthenticationProvider()
        ));
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
        configuration.setExposedHeaders(Arrays.asList("x-auth-token"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authz -> authz
                        // ==================== PUBLIC ENDPOINTS ====================

                        // Swagger documentation
                        .requestMatchers(
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/api-docs/**"
                        ).permitAll()

                        // Auth endpoints - LOGIN APIs
                        .requestMatchers(
                                "/api/sinhvien/login",    // Sinh viên login
                                "/api/giangvien/login",   // Giảng viên login
                                "/api/admin/login"        // Admin login
                        ).permitAll()

                        // Password reset endpoints
                        .requestMatchers(
                                "/api/password-reset/**"  // Tất cả endpoints reset password
                        ).permitAll()

                        // Legacy login endpoints (nếu còn dùng)
                        .requestMatchers(
                                "/api/user/login",
                                "/api/register"
                        ).permitAll()

                        // ==================== PROTECTED ENDPOINTS ====================

                        // Admin endpoints - chỉ ADMIN
                        .requestMatchers("/apiAdmin/**").hasRole("ADMIN")

                        // Teacher endpoints - ADMIN hoặc TEACHER
                        .requestMatchers("/api/teacher/**").hasAnyRole("ADMIN", "TEACHER")

                        // Student endpoints - ADMIN, TEACHER hoặc STUDENT
                        .requestMatchers("/api/student/**").hasAnyRole("ADMIN", "TEACHER", "STUDENT")

                        // ==================== TẤT CẢ API KHÁC CẦN XÁC THỰC ====================
                        .requestMatchers("/api/**").authenticated()  // Thay đổi từ permitAll() thành authenticated()

                        // Mặc định cho phép tất cả các request khác
                        .anyRequest().permitAll()
                );

        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}