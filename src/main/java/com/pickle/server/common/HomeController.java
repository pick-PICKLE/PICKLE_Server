package com.pickle.server.common;


import com.pickle.server.config.PropertyUtil;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping
    public JSONObject home(){
        return PropertyUtil.responseMessage("Hello world");
    }
}
