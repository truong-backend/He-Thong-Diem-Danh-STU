package vn.diemdanh.hethong.dto.tkb;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TkbRequestDto {
    private Long maGd;
    private List<Integer> thu;
    private String maMh;
}
