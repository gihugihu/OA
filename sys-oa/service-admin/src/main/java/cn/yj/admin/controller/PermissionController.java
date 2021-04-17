package cn.yj.admin.controller;


import cn.yj.admin.ConsVal;
import cn.yj.admin.entity.po.Permission;
import cn.yj.admin.service.IPermissionService;
import cn.yj.common.AbstractController;
import cn.yj.common.OperateLog;
import cn.yj.entity.R;
import cn.yj.tools.exception.ServiceException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.Serializable;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 永健
 * @since 2020-04-04 03:15
 */

@RestController
@RequestMapping("/permission")
public class PermissionController extends AbstractController<Permission>{

    @Autowired
    IPermissionService thisService;

    @GetMapping("/userMenuTree")
    public R userMenuTree() {
        return success(thisService.selectUserMenuTree(getCurrentUserId()));
    }


    @GetMapping("/userMenuTreeIds")
    public R userMenuTreeIds() {
        return success(thisService.selectUserMenuTreeIds(getCurrentUserId()));
    }

    @OperateLog(describe = "菜单列表")
    @GetMapping("/tree/all")
    @RequiresPermissions(value = {"menu:list"})
    public R menuTreeAll(String name) {
        return success(thisService.selectAllMenuTree(name));
    }

    /**
     * <p>
     * 根据当前节点的父id 获取 上级所有节点的id
     * </p>
     *
     * @param parentId
     */
    @GetMapping("/getMenIdsByParentId/{parentId}")
    public R getMenIdsByParentId(@PathVariable String parentId) {
        return success(thisService.selectMenIdsByParentId(parentId));
    }


    /**
     * 根据当前菜单id 获取所有子节点id
     *
     * @param menuId
     * @return
     */
    @GetMapping("/getMenuSonIdsByMenId/{menuId}")
    public R getMenuSonIdsByMenId(@PathVariable("menuId") String menuId) {
        return success(thisService.selectMenuSonIdsByMenId(menuId));
    }

    /**
     * 获取角色 拥有的菜单 id
     * 做角色权限修改回显
     *
     * @param roleId
     */
    @GetMapping("/getMenuIdsByRoleId/{roleId}")
    public R getMenuIdByRoleId(@PathVariable("roleId") String roleId) {
        return success(thisService.selectMenuIdByRoleId(roleId));
    }


    /**
     * 获取父Id为0目录，并且排除掉首页，没有子级并且有有路由的
     *
     * @return
     */
    @GetMapping("/getMenuParentIdIsZero")
    public R getMenuIdByRoleId() {
        return success(thisService.getIdAndNameByMenuParentIdIsZeroAndUrlIsNull());
    }


    /**
     * 只允许前端添加目录和外链
     * <p>
     * 添加路由没有意义，添加了路由需要开发对应的页面；
     * 单纯添加没有意义
     *
     * @param entity
     * @return
     */
    @PostMapping("/save")
    @OperateLog(describe = "新增菜单")
    @RequiresPermissions(value = {"menu:add"})
    public R insertSave(@Valid @RequestBody Permission entity) {
        return result(thisService.insert(entity));
    }


    @PutMapping("/update")
    @OperateLog(describe = "修改菜单")
    @RequiresPermissions(value = {"menu:update"})
    public R editSave(@Valid @RequestBody Permission entity) {
        return result(thisService.updateChange(entity));
    }


    @GetMapping("/selectById/{id}")
    public R selectById(@PathVariable("id") String id) {
        return success(thisService.selectById(id));
    }

    @OperateLog(describe = "删除菜单")
    @RequiresPermissions(value = ConsVal.SUPER_ADMIN_CODE)
    @DeleteMapping("/remove/{ids}")
    public R delete(@PathVariable("ids") Serializable[] ids) {
        if (true) {
            throw new ServiceException("不允许删除");
        }
        return result(thisService.removeByIds(ids));
    }

    @GetMapping("/auth/check")
    public R authCheck(@RequestParam String code) {
        boolean isFlag = true;
        try {
            SecurityUtils.getSubject().checkPermission(code);
        } catch (AuthorizationException e) {
            //e.printStackTrace();
            isFlag = false;
        }
        return success(isFlag);
    }
}

