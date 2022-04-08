package se.iths.entity;

public class JSONResponse {

    private String ResponseStatus;
    private String ResponseCode;
    private String ResponseMessage;

    public JSONResponse(String responseStatus, String responseCode, String responseMessage) {
        ResponseStatus = responseStatus;
        ResponseCode = responseCode;
        ResponseMessage = responseMessage;
    }

    public JSONResponse() {
    }


    public String getResponseCode() {
        return ResponseCode;
    }

    public void setResponseCode(String responseCode) {
        ResponseCode = responseCode;
    }

    public String getResponseMessage() {
        return ResponseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        ResponseMessage = responseMessage;
    }

    public String getResponseStatus() {
        return ResponseStatus;
    }

    public void setResponseStatus(String responseStatus) {
        ResponseStatus = responseStatus;
    }
}