package com.aoji.mapper;

import com.aoji.model.SysUser;
import com.aoji.model.dto.UserInfoDTO;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SysUserMapper extends Mapper<SysUser> {

    /**
     * 根据所属国家和角色查询用户
     * @return
     */
    List<SysUser> selectByCountryAndRole(@Param("countryBussId") Integer country,
                                            @Param("roleName") String role,
                                            @Param("leaderStatus") Boolean leaderStatus);

    /**
     * 根据工号查询用户角色、是否为组领导人
     */
    List<UserInfoDTO> selectByOaid(String oaid);

    /**
     * 根据角色名查询用户
     * @param roleName
     * @return
     */
    List<SysUser> getByRoleName(String roleName);

    /**
     * 根据组长oaid查询组员
     * @param oaid
     * @return
     */
    List<SysUser> getByLeaderOaid(String oaid);

    /**
     * 根据oaid查询同国家线其他组长id
     * @param oaid
     * @return
     */
    List<SysUser> getOtherLeaders(String oaid);

    /**
     * 根据国家和用户组查询用户  （角色为文案经理或文案顾问）
     * @param userGroup
     * @param countryGroup
     * @return
     */
    List<SysUser> getByUserGroupAndCountry(@Param("userGroup") Integer userGroup,
                                           @Param("countryGroup") Integer countryGroup,
                                           @Param("leaderStatus") Boolean leaderStatus);

    /**
     * 查询转案分配人
     * @param countryBussId
     * @return
     */
    List<SysUser> getTransferUser(Integer countryBussId);

    /**
     * 根据国家组查询转案分配人
     * @param countryGroup
     * @return
     */
    List<SysUser> getTransferUserByCountryGroup(Integer countryGroup);

    List<String> getOaidsByRoleName(String roleName);

    List<SysUser> getSysUsersByRoleName(String roleName);

    /**
     * 查询文签经理和文签总监
     * @param studentNo 学号
     * @param oaid 学生当前文签顾问工号
     * @return
     */
    List<String> getCopyManager(@Param("studentNo") String studentNo,
                                @Param("oaid") String oaid);
}