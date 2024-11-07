package org.ambati.user_service.repository;

import org.ambati.user_service.model.CustomUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<CustomUser,Long> {
    CustomUser findByUsername(String userName);

}
