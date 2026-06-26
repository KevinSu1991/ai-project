package com.sec.carbon.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sec.carbon.base.entity.SysUserManagement;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户管理 Mapper 接口
 * <p>继承 MyBatis-Plus BaseMapper，自动获得 CRUD 方法</p>
 *
 * @author Power-Station-Carbon-Team
 */
@Mapper
public interface SysUserManagementMapper extends BaseMapper<SysUserManagement> {
}
