package com.panda.client.clients;

import feign.Feign;
import feign.Request;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;

/**
 * com.panda.client.clients.Client
 * <p>
 * DATE 2019/7/25
 *
 * @author zhanglijian.
 */
public class Client {

    public static <T> T createDefalut(Class<T> clazz, String url) {
        return Feign.builder()
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .options(new Request.Options(5 * 1000, 5 * 1000))
                .target(clazz, url);
    }

}
