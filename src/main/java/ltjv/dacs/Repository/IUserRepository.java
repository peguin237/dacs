package ltjv.dacs.Repository;

import jakarta.transaction.Transactional;
import ltjv.dacs.entity.Perfume;
import ltjv.dacs.entity.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserRepository extends PagingAndSortingRepository<User, String>,JpaRepository<User, String> {

    default List<User> findAllUsers(Integer pageNo,
                                          Integer pageSize,
                                          String sortBy) {
        return findAll(PageRequest.of(pageNo, pageSize, Sort.by(sortBy))).getContent();
    }

    @Query("SELECT u FROM User u WHERE u.username =?1")
    User findByUsername(String username);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO user_role (user_id, role_id)" + "VALUES (?1, ?2)",nativeQuery = true)
    void addRoleToUser(Long userId, Long roleId);
    @Query("SELECT u.id FROM User u WHERE u.username = ?1")
    Long getUserIdByUsername(String username);
    @Query(value = "SELECT r.role_name FROM role r INNER JOIN user_role ur " +
            "ON r.role_id = ur.role_id WHERE ur.user_id = ?1", nativeQuery = true)
    String[] getRoleOfUser(Long userId);
}
