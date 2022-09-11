package compilation;

import compilation.dto.CompilationDto;
import compilation.model.Compilation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CompilationRepository extends JpaRepository<Compilation, Integer> {

    @Query("select c from Compilations c where c.pinned is ?1")
    List<CompilationDto> findAllByPinned(Boolean pinned, Pageable pageable);
}
