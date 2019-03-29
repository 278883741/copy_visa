package com.aoji.service;

/**
 * author: chenhaibo
 * description: 转案接收接口
 * date: 2018/1/4
 */
public interface TransferReceiveService {

    /**
     * 添加一条记录
     * @param id
     * @param confirmStatus
     * @param comment
     * @return
     */
    boolean insert(Integer id, Integer confirmStatus, String comment);


    /**
     * 自动接受：适用只有一个接收人的文签转案
     * @param id 转案id
     * @param receiveNo 接收人工号
     * @param receiver 接收人
     * @return
     */
    boolean autoReceive(Integer id, String receiveNo, String receiver, String studentNo);
}
