package de.cgi

import de.cgi.data.requests.AuthRequest
import de.cgi.data.responses.AuthResponse
import de.cgi.data.models.User
import de.cgi.data.datasource.UserDataSource
import de.cgi.data.requests.SignUpRequest
import de.cgi.data.responses.GetUserRoleResponse
import de.cgi.security.hashing.HashingService
import de.cgi.security.hashing.SaltedHash
import de.cgi.security.token.TokenClaim
import de.cgi.security.token.TokenConfig
import de.cgi.security.token.TokenService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Route.signUp(
    hashingService: HashingService,
    userDataSource: UserDataSource
) {
    post("signup") {

        val request = call.receiveNullable<SignUpRequest>() ?: kotlin.run {
            call.respond(
                HttpStatusCode.BadRequest,
                AuthResponse(
                    message = "Something went wrong",
                    token = null,
                    userId = null,
                    username = null
                )
            )
            return@post
        }

        val areFieldsBlank = request.email.isBlank() || request.password.isBlank()
        val isPwToShort = request.password.length < 8
        if (areFieldsBlank) {
            call.respond(
                HttpStatusCode.Conflict,
                AuthResponse(
                    message = "Required fields were blank",
                    token = null,
                    userId = null,
                    username = null
                )
            )
            return@post
        }
        if (isPwToShort) {
            call.respond(
                HttpStatusCode.Conflict,
                AuthResponse(
                    message = "Password to short",
                    token = null,
                    userId = null,
                    username = null
                )
            )
            return@post
        }

        val userFound = userDataSource.getUserByEmail(request.email)

        //TODO: remove, only for testing purposes
        val role = if (request.email.contains("@manager")) {
            "team_manager"
        } else {
            "team_member"
        }

        if (userFound == null) {
            val saltedHash = hashingService.generateSaltedHash(request.password)
            val user = User(
                name = request.name,
                email = request.email,
                hashedPassword = saltedHash.hash,
                salt = saltedHash.salt,
                role = role,
                teamId = null
            )
            val wasAcknowledged = userDataSource.insertUser(user)
            if (!wasAcknowledged) {
                call.respond(
                    HttpStatusCode.Conflict,
                    AuthResponse(
                        message = "Could not insert User into DB",
                        token = null,
                        userId = null,
                        username = null
                    )
                )
                return@post
            }
            call.respond(
                HttpStatusCode.OK,
                AuthResponse(message = null, token = null, userId = null, username = null)
            )

        } else {
            call.respond(
                HttpStatusCode.Conflict,
                AuthResponse(
                    message = "User already exists",
                    token = null,
                    userId = null,
                    username = null
                )
            )
        }


    }
}

fun Route.signIn(
    userDataSource: UserDataSource,
    hashingService: HashingService,
    tokenService: TokenService,
    tokenConfig: TokenConfig
) {
    post("signin") {
        val request = call.receiveNullable<AuthRequest>() ?: kotlin.run {
            call.respond(
                status = HttpStatusCode.BadRequest,
                message = AuthResponse(message = null, token = null, userId = null, username = null)
            )
            return@post
        }

        val areFieldsBlank = request.email.isBlank() || request.password.isBlank()
        if (areFieldsBlank) {
            call.respond(
                status = HttpStatusCode.Conflict,
                message = AuthResponse(
                    message = "Password or E-Mail was blank",
                    token = null,
                    userId = null,
                    username = null
                )
            )
            return@post
        }

        val user = userDataSource.getUserByEmail(request.email)
        if (user == null) {
            call.respond(
                status = HttpStatusCode.Conflict,
                message = AuthResponse(
                    message = "User not found",
                    token = null,
                    userId = null,
                    username = null
                )
            )
            return@post
        } else {
            val saltedHash = SaltedHash(user.hashedPassword, user.salt)
            val validPassword = hashingService.verify(
                value = request.password,
                saltedHash = saltedHash
            )


            if (!validPassword) {
                call.respond(
                    status = HttpStatusCode.Conflict,
                    message = AuthResponse(
                        message = "Incorrect E-Mail or Password",
                        token = null,
                        userId = null,
                        username = null
                    )
                )
                return@post
            }

            val token = tokenService.generates(
                config = tokenConfig,
                TokenClaim(
                    name = "userId",
                    value = user.id.toString()
                ),
                TokenClaim(
                    name = "role",
                    value = user.role.toString()
                )
            )



            call.respond(
                status = HttpStatusCode.OK,
                message = AuthResponse(message = null, token, user.id.toString(), user.name)
            )
        }
    }
}

fun Route.authenticate() {
    //function takes care of token authentication and respond with Unauthorized Status code if not authorized.
    authenticate {
        get("authenticate") {
            call.respond(
                status = HttpStatusCode.OK,
                message = AuthResponse(message = null, token = null, userId = null, username = null)
            )
        }
    }
}

fun Route.getUserRole() {
    authenticate {
        get("role") {
            val principal = call.principal<JWTPrincipal>()
            val role = principal?.getClaim("role", String::class)
            if (role != null) {
                call.respond(status = HttpStatusCode.OK, message = GetUserRoleResponse(role))
            } else {
                call.respond(HttpStatusCode.Conflict, "No user found")
            }

        }
    }
}