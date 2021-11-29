package fpt.toeic.security;


import fpt.toeic.config.CustomUserDetails;
import fpt.toeic.repository.ObjectsRepository;
import fpt.toeic.repository.UsersRepository;
import fpt.toeic.security.UserDetailsServiceImpl;
import fpt.toeic.security.jwt.TokenProvider;
import fpt.toeic.service.dto.DsRolesDTO;
import fpt.toeic.service.dto.UsersDTO;
import fpt.toeic.utils.DataUtil;
import fpt.toeic.utils.Translator;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

// thuc ra no chi de choc xuong database de lay du lieu, - lay role ra
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    TokenProvider tokenProvider;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    ObjectsRepository objectsRepository;

    @Autowired
    UsersRepository UsersRepository;

    @Autowired
    UserDetailsServiceImpl userDetailService;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsersDTO loginRequest = (UsersDTO) authentication.getPrincipal();
        CustomUserDetails customUserDetails = (CustomUserDetails) userDetailService.loadUserByUsername( loginRequest.getName() );
        UsersDTO userDTO = customUserDetails.getUser(); // Lấy thông tin user từ DTO
        //login xác thực user
        UsernamePasswordAuthenticationToken result = null;
        if (userDTO.getName().equals( loginRequest.getName() )) { // Kiểm tra trong DB và thông tin đăng nhập
            List<DsRolesDTO> listTreeDTO = getRoles(userDTO);// lấy role của user , có 1 list
            customUserDetails.setList( listTreeDTO ); // set list quyền của user vào customdetail
            customUserDetails.setUser( userDTO );
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken( customUserDetails.getUser().getName(), customUserDetails.getUser().getPass() );// lưu user vs password ở holderLoated
            String jwt = tokenProvider.createToken( authenticationToken, true );// Tạo 1 token
            customUserDetails.setJwt( jwt );// set tokem cho customUser
            result = new UsernamePasswordAuthenticationToken( customUserDetails, customUserDetails.getPassword(), // Lưu authentication user và password
                    customUserDetails.getAuthorities() ); // Lưu user mà có quyền vừa lấy
        } else {
            throw new ServiceException( Translator.toLocale( "login.false" ) );
        }
        return result;
    }
    private List<DsRolesDTO> getRoles(UsersDTO userDTO) {
        List<DsRolesDTO> lstObject = objectsRepository.getRoleAction( userDTO.getId() )
                .stream().map( e -> {
                DsRolesDTO dto = new DsRolesDTO();
                    dto.setId( DataUtil.safeToLong( e[0] ) );
                    dto.setCode( DataUtil.safeToString( e[1] ) );
                    dto.setTitle( DataUtil.safeToString( e[2] ) );
                    dto.setStatus( DataUtil.safeToLong( e[3] ) );
                    dto.setType(DataUtil.safeToLong( e[4] ) );
                    dto.setIcon( DataUtil.safeToString( e[5] ) );
                    dto.setLink( DataUtil.safeToString( e[6] ) );
                    dto.setParenId( DataUtil.safeToLong( e[7] ) );
                    return dto;
                } ).collect( Collectors.toList() );
        return lstObject;
    }

    @Override
    public boolean supports(Class<?> aClass) {

        return false;
    }
}
