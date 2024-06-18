package ru.swe.skywingsexpressserver.controller;

import org.springframework.http.ResponseEntity;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.swe.skywingsexpressserver.service.FavoritesService;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/favorites")
public class FavoritesController {
    private final FavoritesService favoritesService;

    @GetMapping
    public ResponseEntity<Object> getFavorites(){
        return ResponseEntity.ok(favoritesService.getUserFavorites());
    }

    @GetMapping("/add")
    public ResponseEntity<Object> addToFavorites(
            @RequestParam String flightId
    ){
        favoritesService.addToUserFavorites(Long.parseLong(flightId));
        return ResponseEntity.ok("Полёт добавлен в избранное!");
    }

    @GetMapping("/remove")
    public ResponseEntity<Object> removeFromFavorites(
            @RequestParam String flightId
    ){
        favoritesService.removeFromUserFavorites(Long.parseLong(flightId));
        return ResponseEntity.ok("Полёт удалён из избранного!");
    }
}
