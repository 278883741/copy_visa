package com.aoji.service;

import com.aoji.model.CoeAttachmentInfo;

import java.util.List;

public interface CoeAttachmentService {
    Integer add(CoeAttachmentInfo coeAttachmentInfo);

    CoeAttachmentInfo select(Integer id);

    List<CoeAttachmentInfo> getList(Integer businessId);

    Integer update(CoeAttachmentInfo coeAttachmentInfo);

    Integer delete(Integer id);
}
