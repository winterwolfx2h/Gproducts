package com.Bcm.Exception;

public class ApiResponseDTO {
    private String status;
    private String message;
    private boolean success;

    public ApiResponseDTO(String status, String message, boolean success) {
        this.status = status;
        this.message = message;
        this.success = success;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
