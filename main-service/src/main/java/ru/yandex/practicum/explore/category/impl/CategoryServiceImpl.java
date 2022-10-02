package ru.yandex.practicum.explore.category.impl;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.explore.category.CategoryMapper;
import ru.yandex.practicum.explore.category.CategoryNotFoundException;
import ru.yandex.practicum.explore.category.CategoryRepository;
import ru.yandex.practicum.explore.category.CategoryService;
import ru.yandex.practicum.explore.category.dto.CategoryDto;
import ru.yandex.practicum.explore.category.model.Category;
import ru.yandex.practicum.explore.exception.ConflictEntityException;

import java.util.List;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryDto> getCategories(Integer from, Integer size) {
        int page = from / size;
        Pageable pageable = PageRequest.of(page, size);
        return CategoryMapper.toDtoList(categoryRepository.findAll(pageable));
    }

    @Override
    public CategoryDto getCategory(Integer id) {
        final Category inDb = categoryRepository.findById(id).orElseThrow(() ->
                new CategoryNotFoundException(String
                        .format("Category with id=%d was not found.", id)));
        return CategoryMapper.toDto(inDb);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto updateDto) {
        final Category inDb = categoryRepository.findById(updateDto.getId())
                .orElseThrow(() -> new CategoryNotFoundException(String
                        .format("Category with id=%d was not found.", updateDto.getId())));
        if (updateDto.getName() != null) inDb.setName(updateDto.getName());
        final Category savedCategory = categoryRepository.save(inDb);
        log.info("Category {} updated", savedCategory);
        return CategoryMapper.toDto(savedCategory);
    }

    @Override
    public CategoryDto createCategory(CategoryDto newDto) {
        final Category savedCategory = categoryRepository.save(CategoryMapper.toCategory(newDto));
        log.info("Category {} created", savedCategory);
        return CategoryMapper.toDto(savedCategory);
    }

    @Override
    public void deleteCategory(Integer id) {
        final Category inDb = categoryRepository.findById(id).orElseThrow(() ->
                new CategoryNotFoundException(String
                        .format("Category with id=%d was not found.", id)));
        try {
            categoryRepository.delete(inDb);
        } catch (ConstraintViolationException e) {
            log.error("Category {} couldn't be deleted", id);
            throw new ConflictEntityException(String.format("Category %d couldn't be deleted", id));
        }
        log.info("Category {} deleted", inDb);
    }
}
