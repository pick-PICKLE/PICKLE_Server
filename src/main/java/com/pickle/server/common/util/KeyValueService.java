package com.pickle.server.common.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class KeyValueService {

    @Value("${security.base-url}")
    private String baseUrl;

    public String makeUrlHead(String kind){
        return baseUrl+"/"+kind+"/";
    }
}
