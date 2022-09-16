package ru.yandex.practicum.compilation;

import ru.yandex.practicum.compilation.model.Compilation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CompilationRepository extends JpaRepository<Compilation, Integer> {

    @Query("select c from Compilation c where c.pinned = ?1")
    List<Compilation> findAllByPinned(Boolean pinned, Pageable pageable);
}
