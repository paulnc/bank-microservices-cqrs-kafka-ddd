package com.tecbank.account.cmd.api.dto;

import com.tecbank.account.common.dto.BaseResponse;

public class OpenAccountResponse extends BaseResponse {
    private String id;

    public OpenAccountResponse(String message, String id) {
        super(message);
        this.id = id;
    }
}
