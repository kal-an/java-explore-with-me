package compilation.impl;

import compilation.CompilationMapper;
import compilation.CompilationNotFoundException;
import compilation.CompilationRepository;
import compilation.CompilationService;
import compilation.dto.CompilationDto;
import compilation.model.Compilation;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompilationServiceImpl implements CompilationService {

    private final CompilationRepository compilationRepository;

    public CompilationServiceImpl(CompilationRepository compilationRepository) {
        this.compilationRepository = compilationRepository;
    }

    @Override
    public List<CompilationDto> getCompilations(Boolean pinned, Integer from, Integer size) {
        int page = from / size;
        Pageable pageable = PageRequest.of(page, size);
        return compilationRepository.findAllByPinned(pinned, pageable).stream()
                .map(CompilationMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CompilationDto getCompilation(Integer id) {
        final Compilation inDb = compilationRepository.findById(id).orElseThrow(() ->
                new CompilationNotFoundException(String
                        .format("Compilation with id=%d was not found.", id)));
        return CompilationMapper.toDto(inDb);
    }
}
