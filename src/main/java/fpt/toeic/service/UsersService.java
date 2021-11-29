package fpt.toeic.service;

import fpt.toeic.config.CustomUserDetails;
import fpt.toeic.domain.Users;
import fpt.toeic.repository.UsersRepository;
import fpt.toeic.service.dto.UsersClientDTO;
import fpt.toeic.service.dto.UsersDTO;
import fpt.toeic.service.mapper.UsersMapper;
import org.apache.commons.lang.StringUtils;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Users}.
 */
@Service
@Transactional
public class UsersService {

    private final Logger log = LoggerFactory.getLogger(UsersService.class);

    private final UsersRepository usersRepository;

    private final UsersMapper usersMapper;

    private final RolesService rolesService;

    public UsersService(UsersRepository usersRepository,
                        UsersMapper usersMapper,
                        RolesService rolesService) {
        this.usersRepository = usersRepository;
        this.usersMapper = usersMapper;
        this.rolesService = rolesService;
    }

    /**
     * Save a users.
     *
     * @param usersDTO the entity to save.
     * @return the persisted entity.
     */
    public Users save(UsersDTO usersDTO, CustomUserDetails principal) {
        if (usersDTO.getMail() != null) {
            usersDTO.setName(usersDTO.getMail());
        }
        log.debug("Request to save Users : {}", usersDTO);
        Users users1 = usersRepository.findByNameOrMail(usersDTO.getName(), usersDTO.getMail());// Tìm user DBtheo tên và email dựa vào user trong
        if (users1 != null) {
            if (usersDTO.getName().equals(users1.getName())) {
                throw new IllegalArgumentException("Email already exist !");
            } else if (usersDTO.getMail().equals(users1.getMail())) {
                throw new IllegalArgumentException("Mail da ton tai");
            } else {
                throw new IllegalArgumentException("Error while adđing");
            }
        }
        usersDTO.setRolesId(1L);
        Users users = usersMapper.toEntity(usersDTO);
        users.setCreationTime(ZonedDateTime.now());
        users.setCreator(principal.getUsername());
        users = usersRepository.save(users);
        return users;
    }

    public Users saveClient(UsersDTO usersDTO) {
        log.debug("Request to save Users : {}", usersDTO);
        Users users1 = usersRepository.findByNameOrMail(usersDTO.getName(), usersDTO.getMail());
        if (users1 != null) {
            if (usersDTO.getName().equals(users1.getName())) {
                throw new IllegalArgumentException("Email already used, please try again ");
            } else if (usersDTO.getMail().equals(users1.getMail())) {
                throw new IllegalArgumentException("Email already used, please try again");
            } else {
                throw new IllegalArgumentException("Error while adding");
            }
        }
        usersDTO.setRolesId(2L);
        Users users = usersMapper.toEntity(usersDTO);
        users.setCreationTime(ZonedDateTime.now());
        users.setCreator("Student account");
        users = usersRepository.save(users);
        return users;
    }


    public UsersDTO update(UsersDTO obj, String url) {
        try {
            CustomUserDetails principal = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Long check = 0L;
            Users users = usersRepository.findById(obj.getId()).get();
            if (users == null) {
                throw new IllegalArgumentException("Account does not exist , please try again!");
            } else {

                if (null != principal && principal.getUser().getId() == obj.getId()) {
                    if (!obj.getMail().equals(principal.getUser().getMail()) && obj.getMail().equals(users.getMail())) {
                        throw new IllegalArgumentException("Email already registered , please try again !");
                    }

                    if (StringUtils.isNotBlank(obj.getFullName().trim())) {
                        users.setFullName(obj.getFullName());
                    }
                    if (StringUtils.isNotBlank(obj.getPhone().trim())) {
                        users.setPhone(obj.getPhone());
                    }
                    if (StringUtils.isNotBlank(obj.getAddress().trim())) {
                        users.setAddress(obj.getAddress());
                    }
                    if (StringUtils.isNotBlank(obj.getMail().trim())) {
                        users.setMail(obj.getMail());
                        users.setName(obj.getMail());
                    }
                    if (StringUtils.isNotBlank(url)) {
                        users.setPathUrl(url.replace("\\", "/"));
                    }
                    if (obj.getDateOfBirth() != null) {
                        users.setDateOfBirth(obj.getDateOfBirth());
                    }
                    check = 1L;
//                    throw new IllegalArgumentException("Bạn không có quyền chỉnh sửa thông tin");
                }

                // chỉ trường hợp user là admin mới đc quyền thay đổi trạng thái của user về trạng thái khóa
                if (null != principal && principal.getUser().getRolesId() == 1) {
                    if (obj.getStatus() != null) {
                        users.setStatus(obj.getStatus());
                    }
                }

                usersRepository.save(users);
                UsersDTO usersDTO1 = usersMapper.toDto(users);
                usersDTO1.setAddress(users.getAddress());
                usersDTO1.setIsCheck(check);
                return usersDTO1;
            }

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage());
        }
    }

    public UsersDTO saveClient(UsersClientDTO usersDTO) {
        log.debug("Request to save Users : {}", usersDTO);
        Users users = usersDTO.users(usersDTO);
        users = usersRepository.save(users);
        return usersMapper.toDto(users);
    }

    /**
     * Get all the users.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<UsersDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Users");
        return usersRepository.findAll(pageable)
            .map(usersMapper::toDto);
    }

    @Transactional(readOnly = true)
    public List<Users> findByPhone(String phone) {
        log.debug("Request to get all Roles");
        return usersRepository.findAllByPhone(phone);
    }


    /**
     * Get one users by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UsersDTO> findOne(Long id) {
        log.debug("Request to get Users : {}", id);
        return usersRepository.findById(id)
            .map(usersMapper::toDto);
    }

    /**
     * Delete the users by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Users : {}", id);
        usersRepository.deleteById(id);
    }
}


