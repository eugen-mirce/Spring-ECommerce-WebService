package com.project.app.ws.io.repositories;

import com.project.app.ws.io.entity.PasswordResetTokenEntity;
import org.springframework.data.repository.CrudRepository;

public interface PasswordResetTokenRepository extends CrudRepository<PasswordResetTokenEntity,Long> {
    PasswordResetTokenEntity findByToken(String token);
}
