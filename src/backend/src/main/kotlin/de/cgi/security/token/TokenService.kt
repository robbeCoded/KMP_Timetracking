package de.cgi.security.token

interface TokenService {
    fun generates(
        config: TokenConfig,
        vararg claims: TokenClaim
    ): String
}