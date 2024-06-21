    package com.restaurant.restaurantservice.controller;

    import com.restaurant.restaurantservice.dto.DeleteRestaurantRequest;
    import com.restaurant.restaurantservice.dto.RestaurantDTO;
    import com.restaurant.restaurantservice.exception.ConstraintException;
    import com.restaurant.restaurantservice.model.response.Response;
    import com.restaurant.restaurantservice.service.RestaurantService;

    import java.util.List;

    import io.swagger.v3.oas.annotations.Operation;
    import io.swagger.v3.oas.annotations.Parameter;
    import io.swagger.v3.oas.annotations.media.Content;
    import io.swagger.v3.oas.annotations.media.ExampleObject;
    import io.swagger.v3.oas.annotations.responses.ApiResponse;
    import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Başarılı",
                        content = @Content(mediaType = "application/json",
                                examples = @ExampleObject(value = "{}"))),
                @ApiResponse(responseCode = "500", description = "Sunucu hatası",
                        content = @Content(mediaType = "application/json",
                                examples = @ExampleObject(value = "{\"statusCode\": \"500\", \"message\": \"Internal Server Error\", \"details\": \"uri=/restaurant\"}")))
        })
        @GetMapping
        @ResponseStatus(HttpStatus.OK)
        public ResponseEntity<Response> getAllRestaurants() {
            return restaurantService.getAllRestaurants();
        }

        @Operation(summary = "Restoranı ID ile bul", description = "Bu endpoint belirli bir ID'ye sahip restoranı döner")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Başarılı",
                        content = @Content(mediaType = "application/json",
                                examples = @ExampleObject(value = "{}"))),
                @ApiResponse(responseCode = "404", description = "Restoran bulunamadı",
                        content = @Content(mediaType = "application/json",
                                examples = @ExampleObject(value = "{\"statusCode\": \"404\", \"message\": \"Restaurant not found with id: ?\", \"details\": \"uri=/restaurant\"}"))),
        })
        @GetMapping("/{id}")
        @ResponseStatus(HttpStatus.OK)
        public ResponseEntity<Response> findRestaurantByID(
                @Parameter(description = "Restoran ID'si", required = true)
                @PathVariable Long id) {
            return restaurantService.getRestaurantByID(id);
        }

        @Operation(summary = "Yeni bir restoran oluştur", description = "Bu endpoint yeni bir restoran oluşturur")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Restoran oluşturuldu",
                        content = @Content(mediaType = "application/json",
                                examples = @ExampleObject(value = "{}"))),
                @ApiResponse(responseCode = "400", description = "Geçersiz giriş",
                        content = @Content(mediaType = "application/json",
                                examples = @ExampleObject(value = "{\"statusCode\": \"400\", \"message\": \"Email cannot be null\", \"details\": \"uri=/restaurant\"}"))),
                @ApiResponse(responseCode = "401", description = "Yetkisiz işlem",
                        content = @Content(mediaType = "application/json",
                                examples = @ExampleObject(value = "{\"statusCode\": \"401\", \"message\": \"The user not allowed to create restaurant\", \"details\": \"uri=/restaurant\"}"))),
                @ApiResponse(responseCode = "404", description = "Kullanıcı bulunamadı",
                        content = @Content(mediaType = "application/json",
                                examples = @ExampleObject(value = "{\"statusCode\": \"404\", \"message\": \"User not found with id: ?\", \"details\": \"uri=/restaurant\"}"))),
                @ApiResponse(responseCode = "500", description = "Sunucu hatası",
                        content = @Content(mediaType = "application/json",
                                examples = @ExampleObject(value = "{\"statusCode\": \"500\", \"message\": \"Internal Server Error\", \"details\": \"uri=/restaurant\"}")))
        })
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
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Güncelleme başarılı",
                        content = @Content(mediaType = "application/json",
                                examples = @ExampleObject(value = "{}"))),
                @ApiResponse(responseCode = "400", description = "Geçersiz giriş",
                        content = @Content(mediaType = "application/json",
                                examples = @ExampleObject(value = "{\"statusCode\": \"400\", \"message\": \"Email cannot be null\", \"details\": \"uri=/restaurant\"}"))),
                @ApiResponse(responseCode = "401", description = "Yetkisiz işlem",
                        content = @Content(mediaType = "application/json",
                                examples = @ExampleObject(value = "{\"statusCode\": \"401\", \"message\": \"The user not allowed to update restaurant\", \"details\": \"uri=/restaurant\"}"))),
                @ApiResponse(responseCode = "404", description = "Restoran ya da kullanıcı bulunamadı",
                        content = @Content(mediaType = "application/json",
                                examples = @ExampleObject(value = "{\"statusCode\": \"404\", \"message\": \"Restaurant not found with id: ?\", \"details\": \"uri=/restaurant\"}"))),
                @ApiResponse(responseCode = "500", description = "Sunucu hatası",
                        content = @Content(mediaType = "application/json",
                                examples = @ExampleObject(value = "{\"statusCode\": \"500\", \"message\": \"Internal Server Error\", \"details\": \"uri=/restaurant\"}")))
        })
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
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Silme başarılı",
                        content = @Content(mediaType = "application/json",
                                examples = @ExampleObject(value = "{}"))),
                @ApiResponse(responseCode = "401", description = "Yetkisiz işlem",
                        content = @Content(mediaType = "application/json",
                                examples = @ExampleObject(value = "{\"statusCode\": \"401\", \"message\": \"The user not allowed to delete restaurant\", \"details\": \"uri=/restaurant\"}"))),
                @ApiResponse(responseCode = "404", description = "Restoran ya da kullanıcı bulunamadı",
                        content = @Content(mediaType = "application/json",
                                examples = @ExampleObject(value = "{\"statusCode\": \"404\", \"message\": \"Restaurant not found with id: ?\", \"details\": \"uri=/restaurant\"}"))),
                @ApiResponse(responseCode = "500", description = "Sunucu hatası",
                        content = @Content(mediaType = "application/json",
                                examples = @ExampleObject(value = "{\"statusCode\": \"500\", \"message\": \"Internal Server Error\", \"details\": \"uri=/restaurant\"}")))
        })
        @DeleteMapping
        @ResponseStatus(HttpStatus.OK)
        public ResponseEntity<Response> deleteRestaurant(
                @Parameter(description = "Silme talebi", required = true)
                @RequestBody DeleteRestaurantRequest deleteRestaurantRequest) {
            return restaurantService.deleteRestaurant(deleteRestaurantRequest);
        }
    }
