package fpt.toeic.security.jwt;

import fpt.toeic.config.*;
import fpt.toeic.domain.SysLogs;
import fpt.toeic.security.CustomAuthenticationProvider;
import fpt.toeic.security.UserDetailsServiceImpl;
import fpt.toeic.service.dto.UsersDTO;
import fpt.toeic.utils.DateUtil;
import fpt.toeic.utils.Translator;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.service.spi.ServiceException;
import org.owasp.encoder.Encode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.ZonedDateTime;

/**
 * thực hiện lọc tất cả các api mà bắy buộc xác thực
 * khi tất cả các api đi vào server sẽ phải đi qua lớp này

 * Filters incoming requests and installs a Spring Security principal if a header corresponding to a valid user is
 * found.
 */
public class JWTFilter extends OncePerRequestFilter {
    private final Logger log = LoggerFactory.getLogger(JWTFilter.class);

    public static final String AUTHORIZATION_HEADER = "Authorization";

    UserDetailsServiceImpl jwtUserDetailsService1 = ApplicationContextHolder.getContext().getBean(UserDetailsServiceImpl.class);
    CustomAuthenticationProvider authenticationProvider = ApplicationContextHolder.getContext().getBean(CustomAuthenticationProvider.class);
    LogConfig logConfig = ApplicationContextHolder.getContext().getBean(LogConfig.class);
    private TokenProvider tokenProvider;


    // constructer
    public JWTFilter(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    //init
    @Override
    protected void initFilterBean() throws ServletException {
        super.initFilterBean();
    }

    // ham xu ly filter

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
        throws ServletException, IOException, ServiceException {
        // thoi gian mac dinh
        ZonedDateTime dateTime = DateUtil.getDateC();
        log.info("url:" + request.getRequestURI());

        // lay token tu api gui vao qua header
        final String requestTokenHeader = request.getHeader(JWTFilter.AUTHORIZATION_HEADER);
        String username = null;
        String jwtToken = null;

        // kiem tra xem requestTokenHeader = null hay khac null
        // null - bo qua
        // khac null: di vao if
        //JWT Token is in the form "Bearer token". Remove Bearer word and only the Token
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(requestTokenHeader) && requestTokenHeader.startsWith(Constants.BEARER)) { // Kiểm tra xem có token hay ko
            jwtToken = requestTokenHeader.substring(7); // cat chuoi lay token bo BEARER
            try {
                username = tokenProvider.getAuthentication(jwtToken); // giai ma token nay ra, kiem tra token nay con han hay k? con thi cho token hop le, ko con token het han
            } catch (Exception e) {
                logger.info(e.getMessage(), e);
                response.setContentType("application/json;charset=UTF-8");
                response.sendError(401, Encode.forHtml(Translator.toLocale(Constants.URL_PART_FALSE)));
                return;
            }
        }


        // kiem tra xem username = null hay khac null
        // null - bo qua
        // khac null: di vao if
        // Nếu ko thì nhảy vào đây
        if (StringUtils.isNotEmpty(username) && SecurityContextHolder.getContext().getAuthentication() == null) {// kiểm tra username có tồn tại ko
            // sử dụng userDetailService để lấy thông tin User - get user
            UserDetails userDetails = jwtUserDetailsService1.loadUserByUsername(username);
            CustomUserDetails customUserDetails = (CustomUserDetails) userDetails; // trả về user detail
            UsersDTO userDTO = customUserDetails.getUser();
            // thanh cong cho buoc xac thuc

            // phan nay se tao 1 token moi cho api va tra ve giao dien luu lai
            //if token is valid configure Spring Security to manually set authentication
            if (tokenProvider.validateToken(jwtToken, userDetails)) {
                String refreshToken = tokenProvider.generateToken(userDetails);
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDTO, userDTO.getPass());
                usernamePasswordAuthenticationToken
                    // After setting the Authentication in the context, we specify that the current user is authenticated.
                    // So it passes the Spring Security Configurations successfully.
                    .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                Authentication authentication = authenticationProvider
                    .authenticate(usernamePasswordAuthenticationToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);// set được Spring context
                response.setHeader(JWTFilter.AUTHORIZATION_HEADER, Constants.BEARER + refreshToken);
            }
        }
        chain.doFilter(request, response);// thực hiện filter

        // sau khi thuc hien xong request thi ghi lai log
        if(!request.getRequestURI().equals("/api/sys-logs") && !request.getRequestURI().equals("/api/authenticate")){
            SysLogs logs1 = new SysLogs(dateTime, request, username, response);
            logConfig.submit(logs1);
        }
    }



}

