package ru.swe.skywingsexpressserver.repository.survey;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.swe.skywingsexpressserver.dto.survey.PassedSurveyDto;
import ru.swe.skywingsexpressserver.model.UserSurveyAssociation;

public interface UserSurveyRepository extends JpaRepository<UserSurveyAssociation, Long> {
    @Query("SELECT new ru.swe.skywingsexpressserver.dto.survey.PassedSurveyDto(sa.survey.id, s.title, sa.correctAnswersCount) " +
        "FROM UserSurveyAssociation sa JOIN SurveyModel s ON sa.survey.id = s.id" +
        " WHERE sa.user.id = :userId")
    Page<PassedSurveyDto> findSurveyIdAndCorrectAnswersCountByUserId(@Param("userId") Long userId, Pageable pageable);
}
