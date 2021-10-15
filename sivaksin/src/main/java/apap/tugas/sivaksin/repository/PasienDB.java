package apap.tugas.sivaksin.repository;

import apap.tugas.sivaksin.model.DokterModel;
import apap.tugas.sivaksin.model.PasienModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PasienDB extends JpaRepository<PasienModel, Long> {
    Optional<PasienModel> findByIdPasien(Long idPasien);
}