package com.gg.chattingservice.modules.client;


import com.gg.commonservice.dto.account.AccountDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="account-service")
public interface AccountServiceClient {

    @GetMapping("/account/{accountId}")
    AccountDto getAccount(@PathVariable String accountId);

}
