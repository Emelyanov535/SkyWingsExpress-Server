package ru.swe.skywingsexpressserver.repository.survey;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.swe.skywingsexpressserver.model.survey.SurveyModel;

public interface SurveyRepository
    extends JpaRepository<SurveyModel, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE SurveyModel s SET s.inArchive = :inArchive WHERE s.id = :id")
    void moveToArchive(Long id);

    @Query("SELECT s FROM SurveyModel s WHERE s.inArchive = false ORDER BY s.id")
    Page<SurveyModel> findAllActiveSurveys(Pageable pageable);

    @Query("SELECT s FROM SurveyModel s WHERE LOWER(s.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    Page<SurveyModel> findByTitleContaining(String title, Pageable pageable);
}
