package com.personal.website.repository;

import com.personal.website.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>
{

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE user AS s SET s.userName=:newUserName, s.firstName=:newFirstName, s.lastName=:newLastName, s.email=:newEmail, s.password=:newPass WHERE s.userName=:userName")
    void updateUser(@Param("newUserName") String newUserName, @Param("newFirstName") String newFirstName,@Param("newLastName") String newLastName,@Param("newEmail") String newEmail, @Param("newPass")String password, @Param("userName")  String userName);

    @Query(value="SELECT COUNT(*) FROM user_entity WHERE user_entity.is_online=TRUE", nativeQuery = true)
    int getTotalOnlineUsers();

    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByUid(String uid);
    Optional<UserEntity> findByUserName(String userName);
    Optional<UserEntity> findByUserNameOrEmail(String username, String email);
    boolean existsByEmail(String email);
    boolean existsByUserName(String userName);


}
