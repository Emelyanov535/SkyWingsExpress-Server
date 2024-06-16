package ru.swe.skywingsexpressserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.swe.skywingsexpressserver.model.user.BenefitModel;

public interface BenefitRepository extends JpaRepository<BenefitModel, Long> { }
