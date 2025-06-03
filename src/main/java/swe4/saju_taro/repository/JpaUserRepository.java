package swe4.saju_taro.repository;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import swe4.saju_taro.domain.User;

import java.util.Optional;

@Repository
public class JpaUserRepository implements UserRepository{

    private  final EntityManager em;

    public JpaUserRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<User> findById(Integer id) {
        User user =  em.find(User.class, id);
        return Optional.ofNullable(user);
    }

    @Override
    public User save(User user) {
        em.persist(user);
        return user;
    }
}
