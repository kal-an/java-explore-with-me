package request;

import org.springframework.data.jpa.repository.JpaRepository;
import request.model.Request;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Integer> {

    List<Request> findByRequesterId(Integer id);

    List<Request> findByEventId(Integer id);

}
