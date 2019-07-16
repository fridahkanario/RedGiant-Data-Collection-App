package com.keredgiantaio.techsavanna.redgiantaio.methods;

public class RoadShowResponse {

    private Integer success;
    private String message;

    public RoadShowResponse() {
    }

    public RoadShowResponse(Integer success, String message) {
        this.success = success;
        this.message = message;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}


