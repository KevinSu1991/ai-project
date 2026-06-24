package com.sec.carbon.base.common;

import com.sec.carbon.base.entity.SysRole;
import com.sec.carbon.base.entity.SysUser;
import com.sec.carbon.base.model.vo.RoleVO;
import com.sec.carbon.base.model.vo.UserVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * 对象转换器（MapStruct）
 * <p>用于 Entity → VO 的自动映射，编译期生成实现类，零性能损耗</p>
 *
 * @author Power-Station-Carbon-Team
 */
@Mapper(componentModel = "spring")
public interface EntityConverter {

    EntityConverter INSTANCE = Mappers.getMapper(EntityConverter.class);

    /**
     * SysUser + SysRole → UserVO
     * <p>将用户实体和关联的角色名称合并为前端展示对象</p>
     *
     * @param user     用户实体
     * @param roleName 角色名称（从 SysRole 查询得到）
     * @return UserVO
     */
    @Mapping(source = "user.id", target = "id")
    @Mapping(source = "user.username", target = "username")
    @Mapping(source = "user.nickname", target = "nickname")
    @Mapping(source = "user.roleId", target = "roleId")
    @Mapping(source = "roleName", target = "roleName")
    @Mapping(source = "user.createTime", target = "createTime")
    @Mapping(source = "user.updateTime", target = "updateTime")
    UserVO toUserVO(SysUser user, String roleName);

    /**
     * SysRole → RoleVO
     */
    RoleVO toRoleVO(SysRole role);
}
