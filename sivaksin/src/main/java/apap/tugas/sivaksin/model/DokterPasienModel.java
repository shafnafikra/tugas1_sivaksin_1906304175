package apap.tugas.sivaksin.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
@Entity
@Table(name = "dokter_pasien")
public class DokterPasienModel implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDokterPasien;

    @NotNull
    @Column(nullable = false)
    private String idBatch;

    @NotNull
    @Column(nullable = false)
    private Long idFaskes;

    @NotNull
    @Column(name = "waktu_suntik", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime waktuSuntik;

    @ManyToOne
    @JoinColumn(name = "id_dokter", referencedColumnName = "idDokter")
    DokterModel dokter;

    @ManyToOne
    @JoinColumn(name = "id_pasien", referencedColumnName = "idPasien")
    PasienModel pasien;

}
