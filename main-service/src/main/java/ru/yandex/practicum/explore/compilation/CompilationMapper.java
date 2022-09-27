package ru.yandex.practicum.explore.compilation;

import ru.yandex.practicum.explore.compilation.dto.CompilationDto;
import ru.yandex.practicum.explore.compilation.model.Compilation;

public class CompilationMapper {

    public static CompilationDto toDto(Compilation compilation) {
        return CompilationDto.builder()
                .id(compilation.getId())
                .pinned(compilation.getPinned())
                .title(compilation.getTitle())
                .build();
    }

    public static Compilation toCompilation(CompilationDto dto) {
        return Compilation.builder()
                .id(dto.getId())
                .pinned(dto.getPinned())
                .title(dto.getTitle())
                .build();
    }
}
