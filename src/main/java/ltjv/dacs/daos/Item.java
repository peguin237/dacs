package ltjv.dacs.daos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    private Long perfumeId;
    private String perfumeName;
    private Double price;
    private int quantity;
}
