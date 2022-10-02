package ru.yandex.practicum.explore.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.explore.category.dto.CategoryDto;
import ru.yandex.practicum.explore.validation.OnCreate;
import ru.yandex.practicum.explore.validation.OnUpdate;

@RestController
@Slf4j
@RequestMapping(path = "/admin")
public class AdminCategoryController {

    private final AdminCategoryService adminService;

    public AdminCategoryController(AdminCategoryService adminService) {
        this.adminService = adminService;
    }

    @PatchMapping("/categories")
    public CategoryDto updateCategory(
            @Validated(OnUpdate.class) @RequestBody CategoryDto updateDto) {
        log.info("Update category updateDto={}", updateDto);
        return adminService.updateCategory(updateDto);
    }

    @PostMapping("/categories")
    public CategoryDto createCategory(
            @Validated(OnCreate.class) @RequestBody CategoryDto newDto) {
        log.info("Create category createDto={}", newDto);
        return adminService.createCategory(newDto);
    }

    @DeleteMapping("/categories/{catId}")
    public void deleteCategory(
            @PathVariable Integer catId) {
        log.info("Delete category={}", catId);
        adminService.deleteCategory(catId);
    }
}
