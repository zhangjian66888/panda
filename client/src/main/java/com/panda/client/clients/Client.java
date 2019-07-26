package com.panda.client.clients;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Feign;
import feign.Request;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**
 * com.panda.client.clients.Client
 * <p>
 * DATE 2019/7/25
 *
 * @author zhanglijian.
 */
public class Client {

    public static <T> T createDefalut(Class<T> clazz, String url) {
        HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter(new ObjectMapper());
        ObjectFactory<HttpMessageConverters> converter = () -> new HttpMessageConverters(jsonConverter);
        return Feign.builder()
                .encoder(new SpringEncoder(converter))
                .decoder(new SpringDecoder(converter))
                .options(new Request.Options(5 * 1000, 5 * 1000))
                .target(clazz, url);
    }

}
