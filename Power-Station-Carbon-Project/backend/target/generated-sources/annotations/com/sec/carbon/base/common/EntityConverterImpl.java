package com.sec.carbon.base.common;

import com.sec.carbon.base.entity.SysRole;
import com.sec.carbon.base.entity.SysUser;
import com.sec.carbon.base.model.vo.RoleVO;
import com.sec.carbon.base.model.vo.UserVO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-24T12:44:30+0800",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.46.0.v20260407-0427, environment: Java 21.0.10 (Eclipse Adoptium)"
)
@Component
public class EntityConverterImpl implements EntityConverter {

    @Override
    public UserVO toUserVO(SysUser user, String roleName) {
        if ( user == null && roleName == null ) {
            return null;
        }

        UserVO userVO = new UserVO();

        if ( user != null ) {
            userVO.setId( user.getId() );
            userVO.setUsername( user.getUsername() );
            userVO.setNickname( user.getNickname() );
            userVO.setRoleId( user.getRoleId() );
            userVO.setCreateTime( user.getCreateTime() );
            userVO.setUpdateTime( user.getUpdateTime() );
        }
        userVO.setRoleName( roleName );

        return userVO;
    }

    @Override
    public RoleVO toRoleVO(SysRole role) {
        if ( role == null ) {
            return null;
        }

        RoleVO roleVO = new RoleVO();

        roleVO.setCreateTime( role.getCreateTime() );
        roleVO.setDescription( role.getDescription() );
        roleVO.setId( role.getId() );
        roleVO.setRoleKey( role.getRoleKey() );
        roleVO.setRoleName( role.getRoleName() );

        return roleVO;
    }
}
