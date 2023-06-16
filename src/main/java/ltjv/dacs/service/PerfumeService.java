package ltjv.dacs.service;

import lombok.RequiredArgsConstructor;
import ltjv.dacs.Repository.IPerfumeRepository;
import ltjv.dacs.entity.Perfume;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PerfumeService {
    private final IPerfumeRepository perfumeRepository;
    @PreAuthorize("hasAnyAuthority('USER') or hasAnyAuthority('ADMIN')")
    public List<Perfume> getAllPerfumes(Integer pageNo,
                                     Integer pageSize,
                                     String sortBy) {

        return perfumeRepository.findAllPerfumes(pageNo, pageSize, sortBy);
    }
    @PreAuthorize("hasAnyAuthority('USER') or hasAnyAuthority('ADMIN')")
    public Perfume getPerfumeById(Long id){
        Optional<Perfume> optional = perfumeRepository.findById(id);
        return optional.orElse(null);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public void addPerfume(Perfume perfume) {
        perfumeRepository.save(perfume);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public void updatePerfume(@NotNull Perfume perfume) {
        Perfume existingBook = perfumeRepository.findById(perfume.getId()).orElse(null);
        Objects.requireNonNull(existingBook).setTitle(perfume.getTitle());
        existingBook.setDes(perfume.getDes());
        existingBook.setPrice(perfume.getPrice());
        existingBook.setCategory(perfume.getCategory());
        perfumeRepository.save(existingBook);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public void deletePerfumeById(Long id) {
        perfumeRepository.deleteById(id);
    }

    @PreAuthorize("hasAnyAuthority('USER') or hasAnyAuthority('ADMIN')")
    public List<Perfume> searchPerfume(String keyword) {
        return perfumeRepository.searchPerfume(keyword); }
}
