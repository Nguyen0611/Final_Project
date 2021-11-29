package fpt.toeic.security.jwt;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;

import com.google.common.base.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import io.github.jhipster.config.JHipsterProperties;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class TokenProvider {


    private final Logger log = LoggerFactory.getLogger( TokenProvider.class );

    private Key key;
    private static final String AUTHORITIES_KEY = "auth";
    private long tokenValidityInMilliseconds = 0;
    private long tokenValidityInMillisecondsForRememberMe = 30 * 60;
    private long tokenValidityInMillisecondsForLGSP = 86400 * 365 * 3;
    private final JHipsterProperties jHipsterProperties;
    private final Environment env;

    public TokenProvider(JHipsterProperties jHipsterProperties, Environment env) {
        this.jHipsterProperties = jHipsterProperties;
        this.env = env;
    }

    @PostConstruct
    public void init() {
        byte[] keyBytes;
        String secret = jHipsterProperties.getSecurity().getAuthentication().getJwt().getSecret();
        if (!StringUtils.isEmpty( secret )) {
            log.warn( "Warning: the JWT key used is not Base64-encoded. " +
                "We recommend using the `jhipster.security.authentication.jwt.base64-secret` key for optimum security." );
            keyBytes = secret.getBytes( StandardCharsets.UTF_8 );
        } else {
            log.debug( "Using a Base64-encoded JWT secret key" );
            keyBytes = Decoders.BASE64.decode( jHipsterProperties.getSecurity().getAuthentication().getJwt().getBase64Secret() );
        }
        this.key = Keys.hmacShaKeyFor( keyBytes );

        this.tokenValidityInMilliseconds =
            1000 * jHipsterProperties.getSecurity().getAuthentication().getJwt().getTokenValidityInSeconds();
        this.tokenValidityInMillisecondsForRememberMe =
            1000 * jHipsterProperties.getSecurity().getAuthentication().getJwt()
                .getTokenValidityInSecondsForRememberMe();

    }

    public Authentication getAuthentication1(String token) {
        Claims claims = Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody();

        Collection<? extends GrantedAuthority> authorities =
            Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public String createToken(Authentication authentication, boolean rememberMe) {
        String authorities = authentication.getAuthorities().stream()
            .map( GrantedAuthority::getAuthority ) // Lấy user authentication
            .collect( Collectors.joining( "," ) );

        long now = System.currentTimeMillis();
        Date validity;
        if (rememberMe) {
            validity = new Date( now + tokenValidityInMillisecondsForRememberMe);
        } else {
            validity = new Date( now + tokenValidityInMilliseconds);
        }

        return Jwts.builder()
            .setSubject( authentication.getName() ) // mã hoá tên >> Gửi ra thông tin username
            .claim( AUTHORITIES_KEY, authorities ) // mã hoá quyền của user
            .signWith( key, SignatureAlgorithm.HS512 ) // mã hoá key trong token gửi ra
            .setExpiration( validity ) // set time token
            .compact();
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey( key ).build().parseClaimsJws( authToken );
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.info( "Invalid JWT token." );
            log.trace( "Invalid JWT token trace.", e );
        }
        return false;
    }
    // retrieve username from jwt token
    public String getAuthentication(String token) {
        return getClaimFromToken( token, Claims::getSubject );
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken( token );
        return claimsResolver.apply( claims );
    }

    // Lấy thông tin từ token phải dùng khoá bí mật
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder()
            .setSigningKey( key )
            .build()
            .parseClaimsJws( token )
            .getBody();
    }

    // Tạo token cho từng user - generate token for user
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken( claims, userDetails.getUsername() );
    }

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor( key.getEncoded() );
    }

    // while creating the token -
    // 1. Define claims of the token, like Issuer, Expiration, Subject, and the ID
    // 2. Sign the JWT using the HS512 algorithm and secret key.
    // 3. According to JWS Compact
    // Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
    // compaction of the JWT to a URL-safe string

    private String doGenerateToken(Map<String, Object> claims, String subject) {
        String token = Jwts.builder().setClaims( claims ).setSubject( subject ).setIssuedAt( new Date( System.currentTimeMillis() ) )
            .setExpiration( new Date( System.currentTimeMillis() + tokenValidityInMillisecondsForRememberMe * 1000 ) )
            .signWith( getSigningKey() ).compact();

        return token;
    }

    // validate token
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getAuthentication( token );
        return (username.equals( userDetails.getUsername() ) && validateToken( token ));
    }
}
