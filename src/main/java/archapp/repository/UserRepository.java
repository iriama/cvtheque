package archapp.repository;

import archapp.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Page<User> findAll(Pageable pageable);
    @Query("select distinct u from User u where upper(u.firstname) like upper(concat(:name, '%')) or upper(u.lastname) like upper(concat(:name, '%'))")
    Page<User> searchByName(@Param("name") String name,Pageable pageable);
    @Query("select distinct u from Activity a, User u where a.owner = u and a.type = archapp.enumeration.ActivityType.PROFESSIONAL and upper(a.title) like upper(concat('%', :activityTitle, '%'))")
    Page<User> searchByActivityTitle(@Param("activityTitle") String title, Pageable pageable);
    User findUserById(Long id);
    User findByEmail(String email);
    boolean existsByEmail(String email);
    void deleteByEmail(String email);
}

