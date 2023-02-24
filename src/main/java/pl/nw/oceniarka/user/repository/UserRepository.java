package pl.nw.oceniarka.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import pl.nw.oceniarka.user.domain.User;


import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Optional<User> findByEmail(String username);

    @Query("SELECT a.enabled FROM User a WHERE a.username = ?1")
    Boolean isUsernameTaken(String username);

    @Query("SELECT a.enabled FROM User a WHERE a.email = ?1")
    Boolean isEmailTaken(String email);

    @Transactional
    @Modifying
    @Query("UPDATE User a " +
            "SET a.enabled = TRUE WHERE (a.email = ?1 " +
            "AND a.username = ?2)")
    void enableUser(String email, String username);

    @Transactional
    @Modifying
    @Query("DELETE FROM User a WHERE a.email = ?1 AND a.username != ?2")
    void deleteOtherUsers(String email, String username);

    Optional<User> findByToken(String token);
}
