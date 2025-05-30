package com.chengliuxiang.xiaochengshu.user.biz.domain.mapper;

import com.chengliuxiang.xiaochengshu.user.biz.domain.dataobject.RolePermissionDO;

public interface RolePermissionDOMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RolePermissionDO record);

    int insertSelective(RolePermissionDO record);

    RolePermissionDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RolePermissionDO record);

    int updateByPrimaryKey(RolePermissionDO record);
}