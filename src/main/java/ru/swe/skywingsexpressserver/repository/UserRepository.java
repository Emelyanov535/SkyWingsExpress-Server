package ru.swe.skywingsexpressserver.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.swe.skywingsexpressserver.model.user.UserModel;

public interface UserRepository
    extends JpaRepository<UserModel, Long> {
    UserModel getUserModelByEmail(String email);
}
