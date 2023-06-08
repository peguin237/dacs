package ltjv.dacs.Repository;

import ltjv.dacs.entity.Perfume;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPerfumeRepository extends PagingAndSortingRepository<Perfume, Long>, JpaRepository<Perfume, Long> {
    default List<Perfume> findAllPerfumes(Integer pageNo,
                                       Integer pageSize,
                                       String sortBy) {
        return findAll(PageRequest.of(pageNo, pageSize, Sort.by(sortBy))).getContent();
    }

    @Query("""
         SELECT b FROM Perfume b
         WHERE b.title LIKE %?1%
         OR b.des LIKE %?1%
         OR b.category.name LIKE %?1%
         """)
    List<Perfume> searchPerfume(String keyword);
}
