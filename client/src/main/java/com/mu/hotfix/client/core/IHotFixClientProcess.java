package com.mu.hotfix.client.core;

import com.mu.hotfix.common.DTO.RemoteClassDTO;

import java.util.List;


public interface IHotFixClientProcess {

    /**
     * 更新Class
     * @param updateClassDTO
     */
    void updateClass(RemoteClassDTO updateClassDTO);


    /**
     * 获取加载过的Class
     * @return
     */
    List<RemoteClassDTO> listLoadedClass();
}
