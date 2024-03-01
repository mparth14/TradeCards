package dal.asdc.tradecards.Repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import dal.asdc.tradecards.Model.DAO.UserDao;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository  extends CrudRepository<UserDao, String> {
    UserDao findByEmailID(String emailID);

    @Modifying
    @Transactional
    @Query(value = "update user set is_verified = 1 where email_id = ?1", nativeQuery = true)
    int setIsVerified(String emailID);

    @Modifying
    @Transactional
    @Query(value = "update user set password = ?2 where email_id = ?1", nativeQuery = true)
    int setPassword(String emailID, String password);
}

