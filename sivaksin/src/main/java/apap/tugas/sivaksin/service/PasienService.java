package apap.tugas.sivaksin.service;
import apap.tugas.sivaksin.model.PasienModel;
import java.util.List;

public interface PasienService {
    void addPasien(PasienModel pasien);
    void updatePasien(PasienModel pasien);
    List<PasienModel> getPasienList();
    PasienModel getPasienByIdPasien(Long idPasien);
    void deletePasien(PasienModel pasien);
//    List<PasienModel> findPasienByVaksin(String jenisVaksin);
//    List<PasienModel> findPasienByFaskes(String namaFaskes);
//    List<PasienModel> findPasienByVaksinFaskes(String jenisVaksin, String namaFaskes);
}