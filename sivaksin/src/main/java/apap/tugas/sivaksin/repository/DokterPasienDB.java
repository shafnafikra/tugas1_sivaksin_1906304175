package apap.tugas.sivaksin.repository;

import apap.tugas.sivaksin.model.DokterPasienModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DokterPasienDB extends JpaRepository<DokterPasienModel, Long> {
    Optional<DokterPasienModel> findByIdDokterPasien(Long idDokterPasien);
    List<DokterPasienModel> findAll();
    List<DokterPasienModel> findAllByIdFaskes(Long idFaskes);
}