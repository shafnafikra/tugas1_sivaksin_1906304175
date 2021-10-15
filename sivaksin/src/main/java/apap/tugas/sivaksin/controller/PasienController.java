package apap.tugas.sivaksin.controller;

import apap.tugas.sivaksin.model.DokterPasienModel;
import apap.tugas.sivaksin.model.FaskesModel;
import apap.tugas.sivaksin.model.PasienModel;
import apap.tugas.sivaksin.model.VaksinModel;
import apap.tugas.sivaksin.service.DokterPasienService;
import apap.tugas.sivaksin.service.FaskesService;
import apap.tugas.sivaksin.service.PasienService;
import apap.tugas.sivaksin.service.VaksinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PasienController {
    @Qualifier("pasienServiceImpl")
    @Autowired
    PasienService pasienService;

    @Qualifier("faskesServiceImpl")
    @Autowired
    FaskesService faskesService;

    @Qualifier("vaksinServiceImpl")
    @Autowired
    VaksinService vaksinService;

    @Qualifier("dokterPasienServiceImpl")
    @Autowired
    DokterPasienService dokterPasienService;

    @GetMapping("/pasien")
    public String viewAllPasien(
            Model model
    ) {
        model.addAttribute("listPasien", pasienService.getPasienList());
        return "viewall-pasien";
    }

    @GetMapping("/pasien/tambah")
    public String addPasienForm(Model model) {
        PasienModel pasien = new PasienModel();

        model.addAttribute("pasien", pasien);
        return "form-add-pasien";
    }

    @PostMapping(value = "/pasien/tambah", params = {"submitPasien"})
    private String submitPasien(
            @ModelAttribute PasienModel pasien,
            Model model
    ) {
        pasienService.addPasien(pasien);
        model.addAttribute("namaPasien", pasien.getNamaPasien());
        model.addAttribute("idPasien", pasien.getIdPasien());
        return "add-pasien";
    }

    @GetMapping("/pasien/{idPasien}")
    public String viewDetailPasien(
            @PathVariable Long idPasien,
            Model model
    ) {
        PasienModel pasien = pasienService.getPasienByIdPasien(idPasien);
        //FaskesModel faskes = faskesService.getFaskesList();
        if (pasienService.getPasienList().contains(pasien)) {
            model.addAttribute("pasien", pasien);
            model.addAttribute("faskes", faskesService);
            model.addAttribute("listFaskes", pasien.getListFaskes());
            return "view-pasien";
        } else {
            return "null-pasien";
        }
    }

    @GetMapping("/pasien/ubah/{idPasien}")
    public String updatePasienForm(
            @PathVariable Long idPasien,
            Model model
    ) {
        PasienModel pasien = pasienService.getPasienByIdPasien(idPasien);

        model.addAttribute("pasien", pasien);
        return "form-ubah-pasien";
    }

    @PostMapping(value = "/pasien/ubah")
    private String ubahPasien(
            @ModelAttribute PasienModel pasien,
            Model model
    ) {
        pasienService.updatePasien(pasien);
        model.addAttribute("namaPasien", pasien.getNamaPasien());
        model.addAttribute("idPasien", pasien.getIdPasien());
        return "update-pasien";
    }

    @RequestMapping(value = "/cari/pasien", method = RequestMethod.GET)
    public String cariPasienByVaksinFaskes(Model model) {

        List<FaskesModel> listAllFaskes = faskesService.getFaskesList();
        List<VaksinModel> listAllVaksin = vaksinService.getVaksinList();

        model.addAttribute("listFaskes", listAllFaskes);
        model.addAttribute("listVaksin", listAllVaksin);

        return "find-pasien-vaksin-faskes";
    }

    @RequestMapping(value = "/cari/pasien", method = RequestMethod.GET, params = {"jenisVaksin", "namaFaskes"})
    public String cariPasienByVaksinFaskes(
            @RequestParam(required = false, value = "jenisVaksin") String jenisVaksin,
            @RequestParam(required = false, value = "namaFaskes") String namaFaskes,
            Model model) {
        FaskesModel faskes = faskesService.cariFaskesByNama(namaFaskes);
        VaksinModel vaksin = vaksinService.cariVaksinByJenisVaksin(jenisVaksin);

        List<FaskesModel> listAllFaskes = faskesService.getFaskesList();
        List<VaksinModel> listAllVaksin = vaksinService.getVaksinList();

        model.addAttribute("listFaskes", listAllFaskes);
        model.addAttribute("listVaksin", listAllVaksin);
        System.out.println(listAllFaskes);
        System.out.println(listAllVaksin);

        if (faskes!=null && vaksin!=null) {
            List<DokterPasienModel> listDokPas = dokterPasienService.getDokterPasienByIdFaskesJenisVaksin(faskes.getNamaFaskes(), vaksin.getJenisVaksin());
            model.addAttribute("listDokPas", listDokPas);
            return "find-pasien-vaksin-faskes";
        }if (faskes==null && vaksin!=null){
            List<DokterPasienModel> listDokPas = dokterPasienService.getDokterPasienByJenisVaksin(vaksin.getJenisVaksin());
            model.addAttribute("listDokPas", listDokPas);
            return "find-pasien-vaksin-faskes";
        }if (faskes!=null && vaksin==null){
            List<DokterPasienModel> listDokPas = dokterPasienService.getDokterPasienByIdFaskes(faskes.getIdFaskes());
            model.addAttribute("listDokPas", listDokPas);
            return "find-pasien-vaksin-faskes";
        }else{
            return "find-pasien-vaksin-faskes";
        }

    }

}
