package vn.diemdanh.hethong.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
@Slf4j
public class JwtTokenProvider {

    // Use @Value to load from application.properties, with fallback
    @Value("${jwt.secret:9103a077c27ff38313534f0d26b6cd14165f20b1f5b7b4c3eb5988ff9f12f614194996e285436666060f0ee056c40d6e98fe8d44fc812a49016b90f233d50459}")
    private String JWT_SECRET;

    // Load from properties with default value
    @Value("${jwt.expiration:604800000}") // 7 days in milliseconds
    private long JWT_EXPIRATION;

    // Tạo SecretKey từ chuỗi secret
    private SecretKey getSigningKey() {
        byte[] keyBytes = JWT_SECRET.getBytes(StandardCharsets.UTF_8);
        SecretKey key = Keys.hmacShaKeyFor(keyBytes);
        log.debug("Secret key length: {} bits", keyBytes.length * 8);
        return key;
    }

    // Tạo ra jwt từ thông tin user
    public String generateToken(CustomUserDetails userDetails) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);

        try {
            String token = Jwts.builder()
                    .subject(Long.toString(userDetails.getId()))
                    .claim("userType", "USER")
                    .claim("userId", userDetails.getId())
                    .claim("email", userDetails.getUsername()) // Store email
                    .claim("role", userDetails.getRole())
                    .claim("username", userDetails.getUserRealUsername()) // Add real username
                    .issuedAt(now)
                    .expiration(expiryDate)
                    .signWith(getSigningKey(), Jwts.SIG.HS512)
                    .compact();

            log.info("Generated JWT token for user ID: {}, email: {}, expires: {}",
                    userDetails.getId(), userDetails.getUsername(), expiryDate);
            return token;
        } catch (Exception e) {
            log.error("Error generating token for user: {}", userDetails.getId(), e);
            throw new RuntimeException("Error generating JWT token", e);
        }
    }

    // Tạo ra jwt từ thông tin admin
    public String generateTokenForAdmin(CustomAdminDetails adminDetails) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);

        try {
            String token = Jwts.builder()
                    .subject(Integer.toString(adminDetails.getId()))
                    .claim("userType", "ADMIN")
                    .claim("adminId", adminDetails.getId())
                    .claim("email", adminDetails.getUsername()) // Store email
                    .claim("role", adminDetails.getRole())
                    .claim("username", adminDetails.getUserRealUsername()) // Add real username
                    .claim("fullName", adminDetails.getFullName()) // Add full name
                    .issuedAt(now)
                    .expiration(expiryDate)
                    .signWith(getSigningKey(), Jwts.SIG.HS512)
                    .compact();

            log.info("Generated JWT token for admin ID: {}, email: {}, expires: {}",
                    adminDetails.getId(), adminDetails.getUsername(), expiryDate);
            return token;
        } catch (Exception e) {
            log.error("Error generating token for admin: {}", adminDetails.getId(), e);
            throw new RuntimeException("Error generating JWT token for admin", e);
        }
    }

    // Lấy thông tin user từ jwt
    public Long getUserIdFromJWT(String token) {
        try {
            Claims claims = parseToken(token);

            // Try to get from userId claim first, then fallback to subject
            Object userIdClaim = claims.get("userId");
            if (userIdClaim != null) {
                if (userIdClaim instanceof Number) {
                    return ((Number) userIdClaim).longValue();
                }
                return Long.parseLong(userIdClaim.toString());
            }

            return Long.parseLong(claims.getSubject());
        } catch (Exception e) {
            log.error("Error extracting user ID from JWT: {}", e.getMessage());
            throw new RuntimeException("Error extracting user ID from JWT", e);
        }
    }

    // Lấy thông tin admin từ jwt
    public Integer getAdminIdFromJWT(String token) {
        try {
            Claims claims = parseToken(token);

            // Try to get from adminId claim first, then fallback to subject
            Object adminIdClaim = claims.get("adminId");
            if (adminIdClaim != null) {
                if (adminIdClaim instanceof Number) {
                    return ((Number) adminIdClaim).intValue();
                }
                return Integer.parseInt(adminIdClaim.toString());
            }

            return Integer.parseInt(claims.getSubject());
        } catch (Exception e) {
            log.error("Error extracting admin ID from JWT: {}", e.getMessage());
            throw new RuntimeException("Error extracting admin ID from JWT", e);
        }
    }

    // Lấy loại user từ JWT
    public String getUserTypeFromJWT(String token) {
        try {
            Claims claims = parseToken(token);
            String userType = claims.get("userType", String.class);

            // If userType is not present, try to determine from other claims
            if (userType == null || userType.isEmpty()) {
                // Check if it has admin-specific claims
                if (claims.get("adminId") != null) {
                    log.debug("Determined user type as ADMIN from adminId claim");
                    return "ADMIN";
                } else if (claims.get("userId") != null) {
                    log.debug("Determined user type as USER from userId claim");
                    return "USER";
                } else {
                    // Fallback: assume USER if no specific type found
                    log.debug("No specific user type found, defaulting to USER");
                    return "USER";
                }
            }

            log.debug("User type from JWT: {}", userType);
            return userType;
        } catch (Exception e) {
            log.error("Error extracting user type from JWT: {}", e.getMessage());
            // Return default type instead of throwing exception
            return "USER";
        }
    }

    public boolean validateToken(String authToken) {
        if (authToken == null || authToken.trim().isEmpty()) {
            log.debug("JWT token is null or empty");
            return false;
        }

        try {
            Claims claims = parseToken(authToken);

            // Additional validation - check if token is expired
            Date expiration = claims.getExpiration();
            if (expiration != null && expiration.before(new Date())) {
                log.debug("JWT token is expired: {}", expiration);
                return false;
            }

            log.debug("JWT token validation successful for subject: {}", claims.getSubject());
            return true;
        } catch (io.jsonwebtoken.security.SignatureException ex) {
            log.error("Invalid JWT signature - token may have been generated with different secret: {}", ex.getMessage());
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token format: {}", ex.getMessage());
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token: {}", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token: {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty: {}", ex.getMessage());
        } catch (Exception ex) {
            log.error("JWT token validation error: {}", ex.getMessage());
        }
        return false;
    }

    // Helper method to parse token safely
    private Claims parseToken(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }


}