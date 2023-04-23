package com.pidan.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 黄大头
 * @date 2023年04月20日 15:03
 */
@Data
public class PageRequest  implements Serializable {
    private static final long serialVersionUID = 350096246262617326L;

    /**
     * 每页显示多少条
     */
    private int pageSize = 10;

    /**
     * 当前第几页
     */
    private int pageNum = 1;
}
