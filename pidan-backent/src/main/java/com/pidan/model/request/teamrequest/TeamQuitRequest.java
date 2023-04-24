package com.pidan.model.request.teamrequest;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 黄大头
 * @date 2023年04月24日 18:43
 */
@Data
public class TeamQuitRequest implements Serializable {
    private static final long serialVersionUID = -7227872356907731125L;

    /**
     * id
     */
    private Long teamId;
}
