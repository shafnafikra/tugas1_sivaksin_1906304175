package apap.tugas.sivaksin.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
@Entity
@Table(name = "faskes")
public class FaskesModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFaskes;

    @NotNull
    @Column(nullable = false)
    private String namaFaskes;

    @NotNull
    @Column(nullable = false)
    private String kabupaten;

    @NotNull
    @Column(nullable = false)
    private String provinsi;

    @NotNull
    @Column(nullable = false)
    private Integer kuota;

    @NotNull
    @Column(nullable = false)
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime jamMulai;

    @NotNull
    @Column(nullable = false)
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime jamTutup;

    //Relasi dengan VaksinModel
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "idVaksin", referencedColumnName = "idVaksin", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private VaksinModel vaksin;

    @ManyToMany
    @JoinTable(
            name = "pasien_faskes",
            joinColumns = @JoinColumn(name = "id_faskes"),
            inverseJoinColumns = @JoinColumn(name = "id_pasien")
    )
    List<PasienModel> listPasien;

}
