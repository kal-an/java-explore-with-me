package category.impl;

import category.CategoryMapper;
import category.CategoryNotFoundException;
import category.CategoryRepository;
import category.CategoryService;
import category.dto.CategoryDto;
import category.model.Category;
import exception.ConflictEntityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
        return categoryRepository.findAll(pageable).stream()
                .map(CategoryMapper::toDto)
                .collect(Collectors.toList());
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
        if (inDb.getName() != null) inDb.setName(inDb.getName());
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
        log.info("Category {} deleted", inDb);
        categoryRepository.delete(inDb);
    }
}
