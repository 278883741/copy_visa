package com.aoji.service;

import com.aoji.model.FailureJob;

import java.util.List;

public interface FailureJobService {
    List<FailureJob> getList(FailureJob failureJob);
    Integer add(FailureJob failureJob);
    Integer update(FailureJob failureJob);
}
