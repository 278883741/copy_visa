package com.aoji.service;

import com.aoji.model.ConfirmRecord;

public interface ConfirmRecordService {
    Integer addOne( ConfirmRecord confirmRecord);

    ConfirmRecord get(String studentNo,Integer caseId);
}
