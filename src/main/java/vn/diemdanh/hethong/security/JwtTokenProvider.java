package vn.diemdanh.hethong.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
@Slf4j
public class JwtTokenProvider {
    // Đoạn JWT_SECRET này là bí mật, chỉ có phía server biết
    private final String JWT_SECRET = "3970d2c9bb7d0e42bdb91d333313d005131ecc626c19372188fa494759490e284dcabd41fa1aa0c62b9efefce13e4756f6b01d7d81624066d5921bbe85d4540b830712f58e9410d8ec498713f5434c02fa97b7b715cf6e9302aee604751d368115cdf7ed854b3af789a47398c8e12f256238cd76daabe89e260e4a58e6b44241d2ec267ad9b47d563d29a8ef54bc9e8f64a1ec9133e5a6eb2e5489e553dd807f6254188fbbdd8cca60aeef5c226da237dada3e16065ca14901a1855adf6987dac190c8a9df39bee835d244ce457cba7ea5ff7ab29b8c80e6e02c0934fd2920442d6a45460c221d8e29ad9499404a1a7846c8b8c6237ad309b697785b8a507b59a2381b52a7a62babb6fa65eb028afba3835deb6a204617e61d1435b37d03f8720f63eff88eecd1ca173e648191b620a95606bc60efd84952d134705308f77fb8094a867a7112094dede76dd1fee9a2640decb34eee1ff8dd8f55ad454672c738921d36baddb5fb8464a8a3f2d0d0fcf89c0b22b193a2bcaf83eeac79a3b1caca0ee83b652de8f076a3591556e96a29c4a7b19f96517e5e062a96cda44f8949f01d97e3c42f20f02523a5eb031b336a8d0de90f23936c880e4f4d1bebf81fe9388f9eba648283afb9c92e8cf49923432e4c856ed80d941ca71f09d0b9d6ae193e706e6e54ce465b919b65f6cbcffa40c67057c8d3aa469957f6e7766b2bfa4824";

    //Thời gian có hiệu lực của chuỗi jwt
    private final long JWT_EXPIRATION = 604800000L; // 7 ngày

    // Tạo SecretKey từ chuỗi secret
    private SecretKey getSigningKey() {
        byte[] keyBytes = JWT_SECRET.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Tạo ra jwt từ thông tin user
    public String generateToken(CustomUserDetails userDetails) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);
        // Tạo chuỗi json web token từ id của user.
        return Jwts.builder()
                .subject(Long.toString(userDetails.getId()))
                .claim("userType", "USER") // Thêm claim để phân biệt loại user
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(getSigningKey())
                .compact();
    }

    // Tạo ra jwt từ thông tin admin
    public String generateTokenForAdmin(CustomAdminDetails adminDetails) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);
        // Tạo chuỗi json web token từ id của admin.
        return Jwts.builder()
                .subject(Integer.toString(adminDetails.getId()))
                .claim("userType", "ADMIN") // Thêm claim để phân biệt loại user
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(getSigningKey())
                .compact();
    }

    // Lấy thông tin user từ jwt
    public Long getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return Long.parseLong(claims.getSubject());
    }

    // Lấy thông tin admin từ jwt
    public Integer getAdminIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return Integer.parseInt(claims.getSubject());
    }

    // Lấy loại user từ JWT
    public String getUserTypeFromJWT(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.get("userType", String.class);
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(authToken);
            return true;
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return false;
    }
}