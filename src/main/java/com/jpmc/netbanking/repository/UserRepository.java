package com.jpmc.netbanking.repository;
import com.jpmc.netbanking.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    Users findByEmail(String email);

    @Query("Select u from Users u where u.name=:name")
    Users findByName(@Param(("name")) String name);
}