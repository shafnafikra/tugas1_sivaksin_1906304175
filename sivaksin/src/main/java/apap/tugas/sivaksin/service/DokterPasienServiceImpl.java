package apap.tugas.sivaksin.service;

import apap.tugas.sivaksin.model.DokterModel;
import apap.tugas.sivaksin.model.DokterPasienModel;
import apap.tugas.sivaksin.model.FaskesModel;
import apap.tugas.sivaksin.model.PasienModel;
import apap.tugas.sivaksin.repository.DokterPasienDB;
import apap.tugas.sivaksin.repository.FaskesDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.*;


@Service
@Transactional
public class DokterPasienServiceImpl implements DokterPasienService{
    @Autowired
    DokterPasienDB dokterPasienDB;

    @Autowired
    FaskesDB faskesDB;

    List<String> listIdBatch = new ArrayList<>();

    @Override
    public void addDokterPasien(DokterPasienModel dokterPasien, DokterModel dokter, PasienModel pasien, FaskesModel faskes) {
        dokterPasienDB.save(dokterPasien);
    }

    @Override
    public void updateDokterPasien(DokterPasienModel dokterPasien) {dokterPasienDB.save(dokterPasien);}

    @Override
    public List<DokterPasienModel> getDokterPasienList() {
        return dokterPasienDB.findAll();
    }

    @Override
    public DokterPasienModel getDokterPasienByIdDokterPasien(Long idDokterPasien) {
        Optional<DokterPasienModel> dokterPasien = dokterPasienDB.findByIdDokterPasien(idDokterPasien);
        if (dokterPasien.isPresent()) {
            return dokterPasien.get();
        }
        return null;
    }

    @Override
    public void deleteDokterPasien(DokterPasienModel dokterPasien) {
        dokterPasienDB.delete(dokterPasien);
    }

    @Override
    public String getDokterPasienIdBatch(DokterPasienModel dokterPasien){
        PasienModel pasien = dokterPasien.getPasien();
        String idBatch = "";

        if(pasien.getJenisKelamin()==0){
            idBatch += "1";
        }else{
            idBatch += "2";
        }

        String nama = pasien.getNamaPasien();
        String initial = nama.substring(nama.length()-1).toUpperCase();
        idBatch += initial;

        String tempatLahir = pasien.getTempatLahir();
        String initial_lahir = tempatLahir.substring(0,2).toUpperCase();
        idBatch += initial_lahir;

        String tglLahir = pasien.getTanggalLahir().toString();
        String init_tgl = tglLahir.substring(8,10) + tglLahir.substring(5,7);
        idBatch += init_tgl;

        String thnLahir = pasien.getTanggalLahir().toString().substring(0,4);
        Integer tahunLahir = Integer.valueOf(thnLahir);
        tahunLahir = tahunLahir / 10;
        String init_thn = tahunLahir.toString();
        idBatch += init_thn;

        String charAlphabeth = "abcdefghijklmnopqrstuvwxyz";
        charAlphabeth = charAlphabeth.toUpperCase();

        Random rnd = new Random();
        for (int i = 0; i < 2; i++) {
            int idx = rnd.nextInt(26);
            String rndChar = charAlphabeth.substring(idx,idx+1);
            idBatch += rndChar;
        }

        if(listIdBatch.size()==0){
            listIdBatch.add(idBatch);
        }else{
            while(listIdBatch.contains(idBatch)){
                idBatch = idBatch.substring(0,11);
                for (int i = 0; i < 2; i++) {
                    int idx = rnd.nextInt(26);
                    String rndChar = charAlphabeth.substring(idx,idx+1);
                    idBatch += rndChar;
                }
                listIdBatch.add(idBatch);
            }
        }
        return idBatch;
    }

    @Override
    public List<DokterPasienModel> getDokterPasienByIdFaskes(Long idFaskes) {
        return dokterPasienDB.findAllByIdFaskes(idFaskes);
    }

    @Override
    public List<DokterPasienModel> getDokterPasienByJenisVaksin(String jenisVaksin) {
        List<FaskesModel> fasVak = faskesDB.findAllByVaksin_JenisVaksin(jenisVaksin);
        List<DokterPasienModel> pilihPasien = new ArrayList<>();

        for(FaskesModel faskes : fasVak){
            for(DokterPasienModel dokPas : dokterPasienDB.findAllByIdFaskes(faskes.getIdFaskes())){
                pilihPasien.add(dokPas);
            }
        }
        return pilihPasien;
    }

    @Override
    public List<DokterPasienModel> getDokterPasienByIdFaskesJenisVaksin(String namaFaskes , String jenisVaksin) {
        List<FaskesModel> fasVak = faskesDB.findAllByVaksin_JenisVaksin(jenisVaksin);
        List<DokterPasienModel> res = new ArrayList<>();
        FaskesModel faskes = faskesDB.findByNamaFaskes(namaFaskes);
        for(FaskesModel fas : fasVak){
            if(fas.getNamaFaskes() == faskes.getNamaFaskes()){
                List<DokterPasienModel> listDokPas = dokterPasienDB.findAllByIdFaskes(fas.getIdFaskes());
                for(DokterPasienModel dokPas : listDokPas){
                    res.add(dokPas);
                }
            }
        }
        return res;
    }

}
