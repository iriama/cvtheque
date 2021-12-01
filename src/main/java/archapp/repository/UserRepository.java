package archapp.repository;

import archapp.model.Activity;
import archapp.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Page<User> findAll(Pageable pageable);
    @Query("select u from User u where upper(u.firstname) like upper(concat(:name, '%')) or upper(u.lastname) like upper(concat(:name, '%'))")
    Page<User> searchByName(@Param("name") String name,Pageable pageable);
    @Query("select u from Activity a, User u where a.owner = u and upper(a.title) like upper(concat('%', :activityTitle, '%'))")
    Page<User> searchByActivityTitle(@Param("activityTitle") String title, Pageable pageable);
}

