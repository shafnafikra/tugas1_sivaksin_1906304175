package apap.tugas.sivaksin.service;

import apap.tugas.sivaksin.model.FaskesModel;
import apap.tugas.sivaksin.repository.FaskesDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FaskesServiceImpl implements FaskesService{
    @Autowired
    FaskesDB faskesDB;

    @Override
    public void addFaskes(FaskesModel faskes) {
        faskesDB.save(faskes);
    }

//    @Override
//    public void updateFaskes(FaskesModel faskes) {
//        faskesDB.save(faskes);
//    }

    @Override
    public FaskesModel updateFaskes(FaskesModel faskes){
        LocalTime time = LocalTime.now();
        LocalTime mulai = faskes.getJamMulai();
        LocalTime tutup = faskes.getJamTutup();
        if(time.compareTo(mulai) <= 0 || time.compareTo(tutup) >= 0){
            faskesDB.save(faskes);
            return faskes;
        }
        else{
            return null;
        }
    }

    @Override
    public List<FaskesModel> getFaskesList() {
        return faskesDB.findAll();
    }

    @Override
    public FaskesModel getFaskesByIdFaskes(Long idFaskes) {
        Optional<FaskesModel> faskes = faskesDB.findByIdFaskes(idFaskes);
        if (faskes.isPresent()) {
            return faskes.get();
        }
        return null;
    }

    @Override
    public FaskesModel deleteFaskes(FaskesModel faskes) {
        //faskesDB.delete(faskes);
        LocalTime time = LocalTime.now();
        LocalTime mulai = faskes.getJamMulai();
        LocalTime tutup = faskes.getJamTutup();
        if(time.compareTo(mulai) <= 0 || time.compareTo(tutup) >= 0){
            faskesDB.delete(faskes);
            return faskes;
        }
        else{
            return null;
        }
    }

    @Override
    public List<FaskesModel> cariFaskesByVaksin(String jenisVaksin) {
        List<FaskesModel> res = faskesDB.findAllByVaksin_JenisVaksin(jenisVaksin);
        return res;
    }

    @Override
    public FaskesModel cariFaskesByNama(String namaFaskes) {
        FaskesModel result = faskesDB.findByNamaFaskes(namaFaskes);
        return result;
    }


}
