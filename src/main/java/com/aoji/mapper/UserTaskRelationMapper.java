package com.aoji.mapper;

import com.aoji.model.UserTaskRelation;
import com.aoji.vo.MessageVO;
import com.aoji.vo.UserTaskRelationVO;
import com.aoji.vo.WorkCountVO;
import com.aoji.vo.WorkTableCountVo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import java.util.List;

public interface UserTaskRelationMapper extends Mapper<UserTaskRelation> {
    List<MessageVO> queryList(UserTaskRelation userTaskRelation);

    List<UserTaskRelation> queryTaskList(String oaid);

    /**
     * 根据oaid,消息类型,获取分页消息
     * @param oaid
     * @param taskType
     * @param pageIndex
     * @return
     */
    List<UserTaskRelationVO> getTypeList(@Param("oaid") String oaid, @Param("taskType")String taskType, @Param("pageIndex")Integer pageIndex);

    /**
     * 获取各消息的总数及未读总数
     */
    WorkTableCountVo getworkTableCount(String oaid);

    /**
     * 获取各未办事项的总数
     */
    WorkCountVO getWorkCount(String operatorNo);

    /**
     * 获取未回访的总数
     */
    Integer getVisitCount(@Param("operator") String operator,@Param("roleName") String roleName);


}
