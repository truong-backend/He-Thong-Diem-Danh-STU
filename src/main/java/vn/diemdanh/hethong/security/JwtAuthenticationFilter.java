package vn.diemdanh.hethong.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import vn.diemdanh.hethong.service.user_man_and_login.AdminService;
import vn.diemdanh.hethong.service.user_man_and_login.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserService userService;

    @Autowired
    private AdminService adminService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        log.debug("Processing request: {} {}", request.getMethod(), requestURI);

        try {
            // Lấy jwt từ request
            String jwt = getJwtFromRequest(request);

            if (StringUtils.hasText(jwt)) {
                log.debug("JWT token found, validating...");

                if (tokenProvider.validateToken(jwt)) {
                    log.debug("JWT token is valid, extracting user information...");

                    // Lấy loại user từ JWT để quyết định cách xử lý
                    String userType = tokenProvider.getUserTypeFromJWT(jwt);
                    log.debug("User type from JWT: {}", userType);

                    UserDetails userDetails = loadUserDetails(jwt, userType);

                    if (userDetails != null) {
                        // Nếu người dùng hợp lệ, set thông tin cho Security Context
                        UsernamePasswordAuthenticationToken authentication =
                                new UsernamePasswordAuthenticationToken(
                                        userDetails, null, userDetails.getAuthorities());
                        authentication.setDetails(
                                new WebAuthenticationDetailsSource().buildDetails(request));

                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        log.debug("Successfully authenticated user: {} with roles: {}",
                                userDetails.getUsername(), userDetails.getAuthorities());
                    } else {
                        log.warn("Failed to load user details for token");
                        clearSecurityContext();
                    }
                } else {
                    log.debug("JWT token validation failed");
                    clearSecurityContext();
                }
            } else {
                log.debug("No JWT token found in request for: {}", requestURI);
            }
        } catch (io.jsonwebtoken.security.SignatureException ex) {
            log.error("Invalid JWT signature for request {}: {}", requestURI, ex.getMessage());
            handleJwtError(response, "Invalid JWT signature");
        } catch (io.jsonwebtoken.ExpiredJwtException ex) {
            log.error("Expired JWT token for request {}: {}", requestURI, ex.getMessage());
            handleJwtError(response, "Expired JWT token");
        } catch (io.jsonwebtoken.MalformedJwtException ex) {
            log.error("Malformed JWT token for request {}: {}", requestURI, ex.getMessage());
            handleJwtError(response, "Malformed JWT token");
        } catch (io.jsonwebtoken.UnsupportedJwtException ex) {
            log.error("Unsupported JWT token for request {}: {}", requestURI, ex.getMessage());
            handleJwtError(response, "Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty for request {}: {}", requestURI, ex.getMessage());
            handleJwtError(response, "Invalid JWT claims");
        } catch (Exception ex) {
            log.error("Failed to set user authentication for request {}: {}", requestURI, ex.getMessage(), ex);
            handleJwtError(response, "Authentication failed");
        }

        filterChain.doFilter(request, response);
    }

    private UserDetails loadUserDetails(String jwt, String userType) {
        try {
            if ("ADMIN".equals(userType)) {
                // Xử lý cho Admin
                Integer adminId = tokenProvider.getAdminIdFromJWT(jwt);
                log.debug("Loading admin details for ID: {}", adminId);

                UserDetails adminDetails = adminService.loadAdminById(adminId);
                if (adminDetails != null) {
                    log.debug("Successfully loaded admin details for ID: {}", adminId);
                } else {
                    log.warn("Admin not found for ID: {}", adminId);
                }
                return adminDetails;
            } else {
                // Xử lý cho User (mặc định)
                Long userId = tokenProvider.getUserIdFromJWT(jwt);
                log.debug("Loading user details for ID: {}", userId);

                UserDetails userDetails = userService.loadUserById(userId);
                if (userDetails != null) {
                    log.debug("Successfully loaded user details for ID: {}", userId);
                } else {
                    log.warn("User not found for ID: {}", userId);
                }
                return userDetails;
            }
        } catch (Exception e) {
            log.error("Error loading user details for userType: {}, error: {}", userType, e.getMessage());
            return null;
        }
    }

    private void clearSecurityContext() {
        SecurityContextHolder.clearContext();
        log.debug("Cleared security context");
    }

    private void handleJwtError(HttpServletResponse response, String errorMessage) {
        clearSecurityContext();
        // Optionally set response headers for client-side handling
        response.setHeader("X-JWT-Error", errorMessage);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            String jwt = bearerToken.substring(7);
            log.debug("Extracted JWT token (first 20 chars): {}...",
                    jwt.substring(0, Math.min(20, jwt.length())));
            return jwt;
        }

        // Also check for token in query parameter (for WebSocket connections, etc.)
        String tokenParam = request.getParameter("token");
        if (StringUtils.hasText(tokenParam)) {
            log.debug("Found JWT token in query parameter");
            return tokenParam;
        }

        return null;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        String method = request.getMethod();

        // Skip JWT filter for public endpoints
        boolean shouldSkip = (path.equals("/api/user/login") && method.equals("POST")) ||
                (path.equals("/api/register") && method.equals("POST")) ||
                (path.equals("/apiAdmin/login") && method.equals("POST")) ||
                path.startsWith("/public/") ||
                path.startsWith("/swagger-ui/") ||
                path.startsWith("/v3/api-docs") ||
                path.equals("/favicon.ico");

        if (shouldSkip) {
            log.debug("Skipping JWT filter for public endpoint: {} {}", method, path);
        }

        return shouldSkip;
    }
}