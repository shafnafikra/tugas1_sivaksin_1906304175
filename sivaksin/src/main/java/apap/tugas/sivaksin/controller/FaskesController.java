package apap.tugas.sivaksin.controller;

import apap.tugas.sivaksin.model.*;
import apap.tugas.sivaksin.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class FaskesController {
    @Qualifier("faskesServiceImpl")
    @Autowired
    FaskesService faskesService;

    @Autowired
    VaksinService vaksinService;

    @Autowired
    PasienService pasienService;

    @Autowired
    DokterService dokterService;

    @Autowired
    DokterPasienService dokterPasienService;

    @GetMapping("/faskes")
    public String viewAllFaskes(
            Model model
    ){
        model.addAttribute("listFaskes", faskesService.getFaskesList());
        return "viewall-faskes";
    }

    @GetMapping("/faskes/tambah")
    public String addFaskesForm(Model model) {
        FaskesModel faskes = new FaskesModel();
//        VaksinModel vaksin = new VaksinModel();
//        faskes.setVaksin(vaksin);

        model.addAttribute("faskes", faskes);
        model.addAttribute( "listAllVaksin", vaksinService.getVaksinList());
        return "form-add-faskes";
    }

    @PostMapping(value="/faskes/tambah", params = {"submitFaskes"})
    private String submitFaskes(
            @ModelAttribute FaskesModel faskes,
            Model model
    ){
        faskesService.addFaskes(faskes);
        model.addAttribute( "namaFaskes", faskes.getNamaFaskes());
        model.addAttribute( "idFaskes", faskes.getIdFaskes());
        return "add-faskes";
    }

    @GetMapping("/faskes/{idFaskes}")
    public String viewDetailFaskes(
            @PathVariable Long idFaskes,
            Model model
    ) {
        FaskesModel faskes = faskesService.getFaskesByIdFaskes(idFaskes);
        if(faskesService.getFaskesList().contains(faskes)) {
            model.addAttribute("listPasien", faskes.getListPasien());
            model.addAttribute( "faskes", faskes);
            return "view-faskes";
        }else {
            return "null-faskes";
        }
    }

    @GetMapping("/faskes/ubah/{idFaskes}")
    public String updateFaskesForm(
            @PathVariable Long idFaskes,
            Model model
    ){
        FaskesModel faskes = faskesService.getFaskesByIdFaskes(idFaskes);
        if(faskesService.getFaskesList().contains(faskes)) {
            if (faskesService.updateFaskes(faskes) == null) {
                return "faskes-buka";
            } else {
                boolean punyaPasien;
                if (faskes.getListPasien().size()==0){
                    punyaPasien = false;
                }else{
                    punyaPasien = true;
                }
                model.addAttribute("faskes", faskes);
                model.addAttribute( "listAllVaksin", vaksinService.getVaksinList());
                model.addAttribute("punyaPasien", punyaPasien);
                model.addAttribute("listPasien", pasienService.getPasienList());
                return "form-update-faskes";
            }
        }
        return "null-faskes";
    }

    @PostMapping("/faskes/ubah")
    public String updateFaskesSubmit(
            @ModelAttribute FaskesModel faskes,
            Model model
    ){
        faskesService.updateFaskes(faskes);
        model.addAttribute( "namaFaskes", faskes.getNamaFaskes());
        model.addAttribute( "idFaskes", faskes.getIdFaskes());
        return "update-faskes";
    }

    @GetMapping("/faskes/hapus/{idFaskes}")
    public String deleteFaskes(
            @PathVariable Long idFaskes,
            Model model
    ) {
        FaskesModel faskes = faskesService.getFaskesByIdFaskes(idFaskes);
        model.addAttribute("faskes", faskes);

        if (faskesService.getFaskesList().contains(faskes)) {
            if (faskesService.deleteFaskes(faskes) == null) {
                return "faskes-buka";
            } else {
                //if(faskes.getListPasien().size()<1){
                    model.addAttribute("namaFaskes", faskes.getNamaFaskes());
                    return "success-delete";
                //}
                //return "gabisa-delete-faskes";
            }
        }
        return "null-faskes";
    }

    @GetMapping("/faskes/{idFaskes}/tambah/pasien")
    public String addPasienFaskesForm(
            @PathVariable Long idFaskes,
            Model model) {

        DokterPasienModel dokterPasien = new DokterPasienModel();

        model.addAttribute("listAllPasien", pasienService.getPasienList());
        model.addAttribute("idFaskes", idFaskes);
        model.addAttribute("listAllDokter", dokterService.getDokterList());
        model.addAttribute("dokterPasien", dokterPasien);

        return "form-add-pasien-faskes";
    }

    @PostMapping(value="/faskes/{idFaskes}/tambah/pasien", params = {"submitFaskesPasien"})
    private String submitPasienFaskes(
            @PathVariable Long idFaskes,
            @ModelAttribute DokterPasienModel dokterPasien,
            Model model
    ){
        DokterModel dokter = dokterPasien.getDokter();
        PasienModel pasien = dokterPasien.getPasien();
        FaskesModel faskes = faskesService.getFaskesByIdFaskes(idFaskes);

        LocalTime time = dokterPasien.getWaktuSuntik().toLocalTime();
        LocalTime mulai = faskes.getJamMulai();
        LocalTime tutup = faskes.getJamTutup();

        if(time.compareTo(mulai) >= 0 || time.compareTo(tutup) <= 0){
            dokterPasien.setIdBatch(dokterPasienService.getDokterPasienIdBatch(dokterPasien));
            faskes.getListPasien().add(pasien);
            pasien.getListFaskes().add(faskes);
            model.addAttribute( "idDokterPasien", dokterPasien.getIdDokterPasien());
            model.addAttribute("idBatch", dokterPasien.getIdBatch());
            dokterPasienService.addDokterPasien(dokterPasien, dokter, pasien, faskes);
            return "add-pasien-faskes";
        }
        return "gagal-tambah-pasien-faskes";
    }

    @RequestMapping(value="/cari/faskes", method=RequestMethod.GET)
    public String findFaskesByVaksin(Model model){
        model.addAttribute("listAllVaksin", vaksinService.getVaksinList());
        model.addAttribute("listAllFaskes", faskesService.getFaskesList());
        return "find-faskes-vaksin";
    }

    @RequestMapping(value="/cari/faskes", method = RequestMethod.GET, params = {"jenisVaksin"})
    public String hasilFaskesByVaksin (
            @RequestParam(required = false, value = "jenisVaksin") String jenisVaksin,
            Model model
    ){
        if(vaksinService.getVaksinList()!=null){
            model.addAttribute("pilihFaskes", faskesService.cariFaskesByVaksin(jenisVaksin));
        }
        model.addAttribute("listAllVaksin", vaksinService.getVaksinList());
        return "find-faskes-vaksin";
    }
}
