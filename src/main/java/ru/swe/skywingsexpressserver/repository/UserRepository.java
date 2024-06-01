package ru.swe.skywingsexpressserver.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.swe.skywingsexpressserver.model.UserModel;

public interface UserRepository
    extends JpaRepository<UserModel, Long> {
}
