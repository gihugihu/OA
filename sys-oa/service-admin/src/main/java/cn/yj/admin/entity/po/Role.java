package cn.yj.admin.entity.po;

import cn.yj.common.BaseEntity;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author 永健
 * @since 2020-04-04 03:15
 */
@Data
public class Role extends BaseEntity implements Serializable
{
    private static final long serialVersionUID = 1L;


    @NotBlank(message = "名称不允许为空")
    private String roleName;

    @NotBlank(message = "权限字符不能为空")
    private String code;

    private String remark;

}
