package fpt.toeic.security;



import fpt.toeic.config.CustomUserDetails;
import fpt.toeic.domain.Users;
import fpt.toeic.repository.UsersRepository;
import fpt.toeic.security.jwt.TokenProvider;
import fpt.toeic.service.dto.UsersDTO;
import fpt.toeic.service.mapper.UsersMapper;
import fpt.toeic.utils.Translator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * Authenticate a user from the database.
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    private TokenProvider tokenProvider;

    @Autowired
    UsersRepository usersRepository;

    private final UsersMapper usersMapper;

    public UserDetailsServiceImpl(TokenProvider tokenProvider, UsersMapper usersMapper) {
        this.tokenProvider = tokenProvider;
        this.usersMapper = usersMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Users rs = usersRepository.searchUsersId(username, null, null);
        UsersDTO usersDTO = usersMapper.toDto(rs);
        if (usersDTO == null) {
            throw new UsernameNotFoundException(Translator.toLocale("user.notexist"));
        }
        return new CustomUserDetails(usersDTO, null);
    }
}
