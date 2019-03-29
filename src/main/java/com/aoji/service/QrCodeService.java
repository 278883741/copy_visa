package com.aoji.service;

import com.google.zxing.WriterException;

public interface QrCodeService {

    void test(String filePath,String studentNo) throws WriterException;
}
