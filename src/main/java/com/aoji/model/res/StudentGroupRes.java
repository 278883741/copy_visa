package com.aoji.model.res;

import com.aoji.vo.IMStudentSearchVO;

import java.util.List;

/**
 * author: chenhaibo
 * description: 
 * date: 2018/12/8
 */
public class StudentGroupRes extends BaseRes{

    private List<IMStudentSearchVO> data;

    public List<IMStudentSearchVO> getData() {
        return data;
    }

    public void setData(List<IMStudentSearchVO> data) {
        this.data = data;
    }
}
