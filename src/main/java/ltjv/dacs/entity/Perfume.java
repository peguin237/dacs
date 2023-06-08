package ltjv.dacs.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;
import ltjv.dacs.Validator.annotation.ValidCategoryId;
import org.hibernate.Hibernate;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "perfume")
public class Perfume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title", length = 50, nullable = false)
    @Size(min = 1, max = 50, message = "Title must be between 1 and 50 characters")
    @NotBlank(message = "Title must not be blank")
    private String title;
    @Column(name = "des", length = 500, nullable = false)
    @Size(min = 1,max = 500, message = "Des must be between 1 and 50 characters")
    @NotBlank(message = "Des must not be blank")
    private String des;
    @Column(name = "price")
    @Positive(message = "Price must be greater than 0")
    private Double price;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    @ValidCategoryId
    @ToString.Exclude
    private Category category;

    @OneToMany(mappedBy = "perfume", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<ItemInvoice> itemInvoices = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) !=
                Hibernate.getClass(o)) return false;
        Perfume perfume = (Perfume) o;
        return getId() != null && Objects.equals(getId(),
                perfume.getId());
    }
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
