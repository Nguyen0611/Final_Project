package fpt.toeic.repository;

import fpt.toeic.domain.Users;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Users entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UsersRepository extends JpaRepository<Users, Long>, JpaSpecificationExecutor<Users> {
    @Query(value = " select su " +
        " from Users su " +
        " where 1=1 and ( su.status = 1) " +
        " and (:name is null or su.name = :name ) " +
        " and (:mail is null or lower(su.mail) = lower(:mail) )" +
        " and (:resetKey is null or su.resetKey = :resetKey ) ")
    Users searchUsersId(@Param("name") String name, @Param("mail") String mail, @Param("resetKey") String resetKey);
    Users findByNameOrMail(String name,String email);

    Users findByMail(String mail);


    // select * from where phone = ?
    List<Users> findAllByPhone(String phone);
}
