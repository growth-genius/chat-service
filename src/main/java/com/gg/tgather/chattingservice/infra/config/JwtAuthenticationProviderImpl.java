package com.gg.tgather.chattingservice.infra.config;

import com.gg.tgather.chattingservice.modules.client.AccountServiceClient;
import com.gg.tgather.commonservice.dto.account.AccountDto;
import com.gg.tgather.commonservice.security.CredentialInfo;
import com.gg.tgather.commonservice.security.JwtAuthenticationProvider;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthenticationProviderImpl implements JwtAuthenticationProvider {

    private final AccountServiceClient accountServiceClient;

    @Override
    public AccountDto getAccountDto(String principal, CredentialInfo credential) {
        return accountServiceClient.getAccount(principal);
    }

}
