package hackathon.fluttershy.controller;

import lombok.Data;

import java.util.Map;

@Data
public class Request {
    Map<String, String> queryStringParameters;
    Map<String, Object> request;
    Object version;
    Map<String, Object> session;
}