package apap.tugas.sivaksin.service;

import apap.tugas.sivaksin.model.DokterModel;
import apap.tugas.sivaksin.model.DokterPasienModel;
import apap.tugas.sivaksin.model.FaskesModel;
import apap.tugas.sivaksin.model.PasienModel;

import java.util.List;

public interface DokterPasienService {
    void addDokterPasien(DokterPasienModel dokterPasien, DokterModel dokter, PasienModel pasien, FaskesModel faskes);
    void updateDokterPasien(DokterPasienModel dokterPasien);
    List<DokterPasienModel> getDokterPasienList();
    DokterPasienModel getDokterPasienByIdDokterPasien(Long idDokterPasien);
    void deleteDokterPasien(DokterPasienModel dokterPasien);
    String getDokterPasienIdBatch(DokterPasienModel dokterPasien);
    List<DokterPasienModel> getDokterPasienByIdFaskes(Long idFaskes);
    List<DokterPasienModel> getDokterPasienByJenisVaksin(String jenisVaksin);
    List<DokterPasienModel> getDokterPasienByIdFaskesJenisVaksin(String namaFaskes, String jenisVaksin);
}
