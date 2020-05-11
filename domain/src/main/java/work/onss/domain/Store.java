package work.onss.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.StringUtils;
import work.onss.enums.BankEnum;
import work.onss.enums.LicenseEnum;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.*;

/**
 * @author wangchanghao
 */
@Log4j2
@Data
@NoArgsConstructor
@ToString
@Document
public class Store implements Serializable {

    @Id
    private String id;

    private String name;
    private String description;
    private String address;
    private String trademark;
    private String username;
    private String phone;
    private Boolean status = false;
    @Range(min = 0, max = 5, message = "服装 美食 果蔬 饮品 超市 书店")
    private Integer type;
    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2D)
    private Double[] point = new Double[0];
    @Size(min = 1, max = 5, message = "请填写宣传画册")
    private Collection<String> pictures = new ArrayList<>();
    @Size(min = 1, max = 5, message = "请填写宣传视频")
    private Collection<String> videos = new ArrayList<>();

    @Indexed(unique = true)
    private String subMchId;
    @Valid
    private License license;
    @Valid
    private List<Contact> contacts;
    @Valid
    private Legal legal;
    @Valid
    private Bank bank;
    @NotEmpty(message = "请上传小程序效果图")
    @Size(min = 1, max = 5, message = "最多只能上传5张小程序效果图")
    private Collection<String> xcxPictures = new TreeSet<>();
    @Size(min = 1, max = 5, message = "最多只能上传5张特殊资质图片")
    private Collection<String> specialPictures = new TreeSet<>();

    @Data
    @NoArgsConstructor
    public static class Legal {
        @NotBlank(message = "请填写法人姓名")
        private String name;
        @NotBlank(message = "请填写法人18位身份证号")
        @Pattern(regexp = "^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$", message = "身份证格式错误")
        private String idCard;
        @NotBlank(message = "请上传法人身份证正面")
        private String idCardA;
        @NotBlank(message = "请上传法人身份证反面")
        private String idCardB;
        @NotBlank(message = "请填写法人身份证注册日期")
        private String start;
        @NotBlank(message = "请填写法人身份证结束日期")
        private String end;
    }

    @Data
    @NoArgsConstructor
    public static class License {
        @NotBlank(message = "请填写主体简称")
        private String title;
        @NotBlank(message = "请填写主体全称")
        private String name;
        @NotBlank(message = "请上传营业执照副本")
        private String picture;
        @NotNull(message = "请选择主体类型 个体、企业、单位、组织")
        private LicenseEnum type;
        @NotNull(message = "请选择主体行业 如 '餐饮、服装、出行等'")
        private Integer industry;
        @NotBlank(message = "请填写营业执照编号")
        @Pattern(regexp = "[^_IOZSVa-z\\W]{2}\\d{6}[^_IOZSVa-z\\W]{10}", message = "营业执照编号格式错误")
        @Indexed(unique = true)
        private String number;
        @NotBlank(message = "请填写客服手机号")
        @Pattern(regexp = "^[1][34578][0-9]{9}$", message = "客服手机号格式错误")
        private String phone;
        @NotEmpty(message = "请填写经营场所")
        private String address;
    }

    @Data
    @NoArgsConstructor
    public static class Contact {
        private String openid;
        private String role;
        @NotBlank(message = "请填写管理员手机号")
        @Pattern(regexp = "^[1][34578][0-9]{9}$", message = "管理员手机号格式错误")
        private String phone;
        @NotBlank(message = "请填写管理员姓名")
        private String name;
        @NotBlank(message = "请填写管理员18位身份证号")
        @Pattern(regexp = "^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$", message = "管理员身份证格式错误")
        private String idCard;
        @NotBlank(message = "请填写常用邮箱")
        @Email(message = "常用邮箱格式错误")
        private String email;
    }

    @Data
    @NoArgsConstructor
    public static class Bank {
        @NotBlank(message = "请填写开户银行")
        private Integer title;
        @NotBlank(message = "请填写银行账号")
        private String number;
        private BankEnum type;
        private String name;
        @Valid
        private Address address;
    }

    @Data
    @NoArgsConstructor
    public static class Address {
        @NotEmpty(message = "缺少银行地址")
        @Size(min = 3, max = 3, message = "银行地址错误")
        private String[] value;
        @NotBlank(message = "缺少邮编")
        private String postcode;
        @NotEmpty(message = "缺少银行地址编号")
        @Size(min = 3, max = 3, message = "银行地址编号错误")
        private String[] code;
    }
}
