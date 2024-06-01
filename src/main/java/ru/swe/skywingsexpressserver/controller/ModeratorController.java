package ru.swe.skywingsexpressserver.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.swe.skywingsexpressserver.service.ModeratorService;

@RestController
@RequestMapping("/api/moderator/")
public class ModeratorController {
    private final ModeratorService moderatorService;

    public ModeratorController(ModeratorService moderatorService) {
        this.moderatorService = moderatorService;
    }

    public ResponseEntity<Object> addTimetable() {
        //TODO: доделай
        return ResponseEntity.ok("");
    }
}