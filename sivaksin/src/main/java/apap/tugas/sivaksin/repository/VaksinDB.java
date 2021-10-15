package apap.tugas.sivaksin.repository;

import apap.tugas.sivaksin.model.DokterModel;
import apap.tugas.sivaksin.model.VaksinModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VaksinDB extends JpaRepository<VaksinModel, Long> {
    Optional<VaksinModel> findByIdVaksin(Long idVaksin);
    VaksinModel findByJenisVaksin(String jenisVaksin);
}