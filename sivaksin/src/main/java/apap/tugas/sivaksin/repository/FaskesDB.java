package apap.tugas.sivaksin.repository;

import apap.tugas.sivaksin.model.DokterModel;
import apap.tugas.sivaksin.model.FaskesModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FaskesDB extends JpaRepository<FaskesModel, Long> {
    Optional<FaskesModel> findByIdFaskes(Long idFaskes);
    List<FaskesModel> findAllByVaksin_JenisVaksin(String jenisVaksin);
    FaskesModel findByNamaFaskes(String namaFaskes);
}