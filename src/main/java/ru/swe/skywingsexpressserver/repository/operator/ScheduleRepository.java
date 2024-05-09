package ru.swe.skywingsexpressserver.repository.operator;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.swe.skywingsexpressserver.model.operator.ScheduleModel;

public interface ScheduleRepository
    extends JpaRepository<ScheduleModel, Long> {
}
