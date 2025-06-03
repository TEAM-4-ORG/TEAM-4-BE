package swe4.saju_taro.repository;

import jakarta.persistence.EntityManager;
import swe4.saju_taro.domain.User;

import java.util.Optional;

public class JpaUserRepository implements UserRepository{

    private  final EntityManager em;

    public JpaUserRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<User> findById(Long id) {
        User user =  em.find(User.class, id);
        return Optional.ofNullable(user);
    }
}
