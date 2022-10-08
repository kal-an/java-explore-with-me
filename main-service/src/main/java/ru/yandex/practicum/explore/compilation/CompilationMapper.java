package ru.yandex.practicum.explore.compilation;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.explore.compilation.dto.CompilationDto;
import ru.yandex.practicum.explore.compilation.model.Compilation;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
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

    public static List<CompilationDto> toDtoList(Iterable<Compilation> requests) {
        List<CompilationDto> dtos = new ArrayList<>();
        for (Compilation compilation : requests) {
            dtos.add(toDto(compilation));
        }
        return dtos;
    }
}
