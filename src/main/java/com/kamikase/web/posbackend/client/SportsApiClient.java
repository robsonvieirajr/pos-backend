package com.kamikase.web.posbackend.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "SportsAPI", url = "https://www.thesportsdb.com/api/v1/json/3")
public interface SportsApiClient {

    @GetMapping("/all_sports.php")
    String obterEsportePorId();

}
