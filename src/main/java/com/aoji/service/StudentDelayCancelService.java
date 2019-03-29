package com.aoji.service;

import com.aoji.model.StudentDelayCancel;
import java.util.List;

public interface StudentDelayCancelService {
    /**
     * 添加取消缓办申请
     * @param StudentDelayCancel
     * @return
     */
    boolean insert(StudentDelayCancel studentDelayCancel);

    /**
     * 修改取消缓办
     * @param StudentDelayCancel
     * @return
     */
    boolean update(StudentDelayCancel studentDelayCancel);

    /**
     * 根据学号查询取消缓办申请
     * @param studentNo
     * @return
     */
    List<StudentDelayCancel> getByStudentNo(String studentNo);

    /**
     * 根据缓办id查询取消缓办申请
     * @param delayId
     * @return
     */
    List<StudentDelayCancel> getByDelayId(Integer delayId);
}
