package com.panda.core.config;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Objects;

/**
 * com.panda.core.config.ACLProperties
 * <p>
 * DATE 2019/7/17
 *
 * @author zhanglijian.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "panda.acl")
@Configuration
public class ACLProperties {
    private List<String> openUrls;
    private List<String> openPaths;
    private List<String> blankUrls;
    private List<String> blankPaths;

    public boolean checkOpenUrls(String url) {
        return Objects.nonNull(openUrls) && !openUrls.isEmpty() && openUrls.indexOf(url) > -1;
    }

    public boolean checkOpenPaths(String url) {
        return Objects.nonNull(openPaths) && !openPaths.isEmpty() && openPaths.stream().anyMatch(t -> url.startsWith(t));
    }

    public boolean checkBlackUrls(String url) {
        return Objects.nonNull(blankUrls) && !blankUrls.isEmpty() && blankUrls.indexOf(url) > -1;
    }

    public boolean checkBlackPaths(String url) {
        return Objects.nonNull(blankPaths) && !blankPaths.isEmpty() && blankPaths.stream().anyMatch(t -> url.startsWith(t));
    }

    public boolean checkACL(String url) {
        if (checkOpenUrls(url)) return true;
        if (checkOpenPaths(url)) return true;
        if (checkBlackUrls(url)) return false;
        if (checkBlackPaths(url)) return false;
        return false;
    }

    public static void main(String[] args) {
        ACLProperties aclProperties = new ACLProperties();
        aclProperties.setOpenUrls(Lists.newArrayList("/actuator/health", "/actuator/mappings"));
//        aclProperties.setBlankUrls(Lists.newArrayList("/actuator/health", "/actuator/mappings"));
        System.out.println(aclProperties.checkACL("/actuator/health"));
        System.out.println(aclProperties.checkACL("/permissions"));
    }
}
