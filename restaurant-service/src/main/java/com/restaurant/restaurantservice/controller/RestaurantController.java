    package com.restaurant.restaurantservice.controller;

    import com.restaurant.restaurantservice.dto.DeleteRestaurantRequest;
    import com.restaurant.restaurantservice.dto.RestaurantDTO;
    import com.restaurant.restaurantservice.exception.ConstraintException;
    import com.restaurant.restaurantservice.model.response.Response;
    import com.restaurant.restaurantservice.service.RestaurantService;

    import java.util.List;

    import io.swagger.v3.oas.annotations.Operation;
    import io.swagger.v3.oas.annotations.Parameter;
    import io.swagger.v3.oas.annotations.tags.Tag;
    import jakarta.validation.Valid;
    import lombok.RequiredArgsConstructor;
    import org.springframework.context.support.DefaultMessageSourceResolvable;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.validation.BindingResult;
    import org.springframework.web.bind.annotation.*;

    @Tag(name = "Restaurant Service", description = "Restoran işlemlerini yöneten servis")
    @RestController
    @RequestMapping("/restaurant")
    @RequiredArgsConstructor
    public class RestaurantController {

        private final RestaurantService restaurantService;

        @Operation(summary = "Tüm restoranları getir", description = "Bu endpoint tüm restoranları döner")
        @GetMapping
        @ResponseStatus(HttpStatus.OK)
        public ResponseEntity<Response> getAllRestaurants() {
            return restaurantService.getAllRestaurants();
        }

        @Operation(summary = "Restoranı ID ile bul", description = "Bu endpoint belirli bir ID'ye sahip restoranı döner")
        @GetMapping("/{id}")
        @ResponseStatus(HttpStatus.OK)
        public ResponseEntity<Response> findRestaurantByID(
                @Parameter(description = "Restoran ID'si", required = true)
                @PathVariable Long id) {
            return restaurantService.getRestaurantByID(id);
        }

        @Operation(summary = "Yeni bir restoran oluştur", description = "Bu endpoint yeni bir restoran oluşturur")
        @PostMapping
        @ResponseStatus(HttpStatus.OK)
        public ResponseEntity<Response> createRestaurant(
                @Parameter(description = "Restoran bilgileri", required = true)
                @RequestBody @Valid RestaurantDTO restaurantDTO, BindingResult bindingResult) {
            if (bindingResult.hasErrors()) {
                List<String> errors = bindingResult.getAllErrors()
                        .stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .toList();
                throw new ConstraintException(errors);
            }
            return restaurantService.createRestaurant(restaurantDTO);
        }

        @Operation(summary = "Restoranı güncelle", description = "Bu endpoint mevcut bir restoranı günceller")
        @PutMapping
        @ResponseStatus(HttpStatus.OK)
        public ResponseEntity<Response> updateRestaurant(
                @Parameter(description = "Güncellenmiş restoran bilgileri", required = true)
                @RequestBody @Valid RestaurantDTO restaurantDTO, BindingResult bindingResult) {
            if (bindingResult.hasErrors()) {
                List<String> errors = bindingResult.getAllErrors()
                        .stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .toList();
                throw new ConstraintException(errors);
            }
            return restaurantService.updateRestaurant(restaurantDTO);
        }

        @Operation(summary = "Restoranı sil", description = "Bu endpoint mevcut bir restoranı siler")
        @DeleteMapping
        @ResponseStatus(HttpStatus.OK)
        public ResponseEntity<Response> deleteRestaurant(
                @Parameter(description = "Silme talebi", required = true)
                @RequestBody DeleteRestaurantRequest deleteRestaurantRequest) {
            return restaurantService.deleteRestaurant(deleteRestaurantRequest);
        }
    }
