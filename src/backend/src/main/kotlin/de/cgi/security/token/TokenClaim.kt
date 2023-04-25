package de.cgi.security.token

//Key Value Pairs that are stored in the token. One Token can have multiple claims
data class TokenClaim(
    val name: String,
    val value: String
)
