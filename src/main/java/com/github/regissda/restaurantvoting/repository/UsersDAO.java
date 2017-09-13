package com.github.regissda.restaurantvoting.repository;

import com.github.regissda.restaurantvoting.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by MSI on 13.09.2017.
 */

@Repository
public interface UsersDAO extends JpaRepository<User,String> {
}
