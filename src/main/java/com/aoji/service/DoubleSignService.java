package com.aoji.service;

import com.aoji.model.DoubleSignInfo;

import java.util.List;

public interface DoubleSignService {
    List<DoubleSignInfo> getList(DoubleSignInfo doubleSignInfo);
    DoubleSignInfo get(DoubleSignInfo doubleSignInfo);
}
