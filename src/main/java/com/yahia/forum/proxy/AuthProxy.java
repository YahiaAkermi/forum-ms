package com.yahia.forum.proxy;


import com.yahia.forum.model.UserAuth;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "ms-auth")
    @LoadBalancerClient(name = "ms-auth")
    public interface AuthProxy {

        @GetMapping("auth/search/findByEmail")
        UserAuth getAuth(@RequestParam("email") String email, @RequestParam("projection") String projection);

}



