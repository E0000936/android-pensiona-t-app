package com.mx.profuturo.grupo.launchermau.core.token_refresh;

import androidx.annotation.Nullable;

public interface AccessTokenProvider {
    /**
     * Refreshes the token and returns it. This call should be made synchronously.
     * In the event that the token could not be refreshed return null.
     */
    @Nullable
    String refreshToken();
}
