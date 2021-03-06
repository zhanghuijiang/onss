package work.onss.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Log4j2
@Data
@NoArgsConstructor
@Document
public class Address implements Serializable {

    @Id
    private String id;
    @NotBlank(message = "姓名不能为空")
    private String username;
    @Pattern(regexp = "^[1][34578][0-9]{9}$", message = "手机号格式错误")
    private String phone;
    @Length(min = 3, max = 50, message = "请尽可能填写详细地址")
    private String detail;
    @Range(min = 0, max = 3, message = "地址类型格式错误")
    private Integer tag;
    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2D)
    @Size(min = 2, max = 2, message = "请重新定位坐标")
    private double[] point;
    private String uid;
}
