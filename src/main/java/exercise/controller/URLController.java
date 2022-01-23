package exercise.controller;

import exercise.service.URLServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.URL;

@RestController
@RequestMapping("/short")
public class URLController {

    @Autowired
    private URLServiceImpl urlService;


    @Operation(summary = "Получение полной ссылки по короткой")
    @GetMapping(path = "/{shortUrl}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Длинная ссылка"),
            @ApiResponse(responseCode = "404", description = "Короткая ссылка не найдена"),
            @ApiResponse(responseCode = "410", description = "Короткая ссылка истекла")
    })
    public URL getFullUrlByShort(@PathVariable String shortUrl) {
        return urlService.getFullUrlByShort(shortUrl);
    }

    @Operation(summary = "Получение информации с количеством переходов.Требует роль ADMIN.")
    @GetMapping(path = "/{shortUrl}/info")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "JSON представление объекта"),
            @ApiResponse(responseCode = "401", description = "Не авторизованный пользователь"),
            @ApiResponse(responseCode = "404", description = "Короткая ссылка не найдена")
    })
    public exercise.model.URL getInfoByShort(@PathVariable String shortUrl) {
        return urlService.getInfoByShort(shortUrl);
    }

    @Operation(summary = "Получение короткой ссылки по полной ссылке.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Короткая ссылка")
    })
    @PostMapping(path = "/")
    public URL createShortURL(@RequestBody URL url, @RequestParam(defaultValue = "86400", required = false) Integer timeLive) {
        return urlService.createShortURL(url, timeLive);
    }

    @Operation(summary = "Удаление короткой ссылки.Требует роль ADMIN.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Удаление прошло успешно"),
            @ApiResponse(responseCode = "401", description = "Не авторизованный пользователь"),
            @ApiResponse(responseCode = "404", description = "Короткая ссылка не найдена")
    })
    @DeleteMapping(path = "/{shortUrl}")
    public void deleteShortURL(@PathVariable String shortUrl) {
        urlService.deleteShortURL(shortUrl);
    }
}
