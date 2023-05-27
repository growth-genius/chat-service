package com.gg.chattingservice.infra.config;

import com.gg.chattingservice.modules.client.AccountServiceClient;
import com.gg.commonservice.dto.account.AccountDto;
import com.gg.commonservice.security.CredentialInfo;
import com.gg.commonservice.security.JwtAuthenticationProvider;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthenticationProviderImpl implements JwtAuthenticationProvider {

    private final AccountServiceClient accountServiceClient;

    @Override
    public AccountDto getAccountDto(String principal, CredentialInfo credential) {
        return accountServiceClient.getAccount(principal);
    }

}
