package ru.yandex.practicum.compilation;

import ru.yandex.practicum.compilation.dto.CompilationDto;
import ru.yandex.practicum.compilation.model.Compilation;

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
