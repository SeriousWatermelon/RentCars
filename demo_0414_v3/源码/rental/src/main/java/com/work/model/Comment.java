package com.work.model;

import lombok.Data;

/**
 * Created by 稻草人 on 2019/4/14.
 */

@Data
public class Comment {

    private String orderId;

    private Integer commentScore;

    private String commentDesc;

}
