package cn.yj.admin.controller;


import cn.yj.admin.entity.po.Role;
import cn.yj.admin.service.IRoleService;
import cn.yj.annotation.pagehelper.page.OrderBy;
import cn.yj.common.AbstractController;
import cn.yj.common.OperateLog;
import cn.yj.entity.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 永健
 * @since 2020-04-04 03:15
 */

@RestController
@RequestMapping("/role")
public class RoleController extends AbstractController<Role>
{

    @Autowired
    IRoleService thisService;

    @OperateLog(describe = "角色列表")
    @RequiresPermissions(value = {"role:list"})
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> param)
    {
        return success(thisService.findList(param, page(new OrderBy(OrderBy.Direction.ASC, "create_time"))));
    }

    @GetMapping("/listIdNameAll")
    public R listAll()
    {
        return success(thisService.listIdNameAll());
    }

    @OperateLog(describe = "新增角色")
    @RequiresPermissions(value = {"role:add"})
    @PostMapping("/save")
    public R insertSave(@Valid @RequestBody Role entity)
    {
        return result(thisService.insert(entity));
    }


    @OperateLog(describe = "修改角色")
    @RequiresPermissions(value = {"role:update"})
    @PutMapping("/update")
    public R editSave(@Valid @RequestBody Role entity)
    {
        return result(thisService.updateById(entity));
    }

    /**
     * 给角色授权
     *
     * @param roleId  角色id
     * @param menuIds 菜单Id
     * @return
     */
    @PutMapping("/{roleId}/toRoleAuth/{menuIds}")
    @RequiresPermissions(value = {"role:toAuth"})
    public R toRoleAuth(@PathVariable("roleId") String roleId, @PathVariable("menuIds") String[] menuIds)
    {
        return result(thisService.toRoleAuth(roleId, menuIds));
    }
    @OperateLog(describe = "删除角色")
    @DeleteMapping("/remove/{ids}")
    @RequiresPermissions(value = {"role:del"})
    public R removeByIds(@PathVariable("ids") Serializable[] ids)
    {
        return success(thisService.removeByIds(ids));
    }


}

