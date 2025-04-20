package hackathon.fluttershy.controller;

import lombok.Data;

import java.util.Map;

@Data
public class Response{
    private Object version;
    private Map<String, Object> session;
    private Map<String, Object> response;

    public Response(Object version, Map<String, Object> session, Map<String, Object> response) {
        this.version = version;
        this.session = session;
        this.response = response;
    }
}