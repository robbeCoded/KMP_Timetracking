package de.cgi

import de.cgi.data.requests.AuthRequest
import de.cgi.data.responses.AuthResponse
import de.cgi.data.models.User
import de.cgi.data.datasource.UserDataSource
import de.cgi.data.requests.SignUpRequest
import de.cgi.data.responses.GetUserIdResponse
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
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        //implent more validation here
        val areFieldsBlank = request.email.isBlank() || request.password.isBlank()
        val isPwToShort = request.password.length < 8
        if (areFieldsBlank || isPwToShort) {
            //give more information than just the StatusCode
            call.respond(HttpStatusCode.Conflict, "Password to short!")
            return@post
        }

        val userFound = userDataSource.getUserByEmail(request.email)
        if (userFound == null) {
            val saltedHash = hashingService.generateSaltedHash(request.password)
            val user = User(
                name = request.name,
                email = request.email,
                hashedPassword = saltedHash.hash,
                salt = saltedHash.salt
            )
            val wasAcknowledged = userDataSource.insertUser(user)
            if (!wasAcknowledged) {
                call.respond(HttpStatusCode.Conflict)
                return@post
            }

            call.respond(HttpStatusCode.OK)

        } else {
            call.respond(HttpStatusCode.Conflict, "E-Mail address already taken")
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
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        //implent more validation here
        val areFieldsBlank = request.email.isBlank() || request.password.isBlank()
        val isPwToShort = request.password.length < 8
        if (areFieldsBlank || isPwToShort) {
            //give more information than just the StatusCode
            call.respond(HttpStatusCode.Conflict, "Fields are blank or password less than 8 characters")
            return@post
        }

        val user = userDataSource.getUserByEmail(request.email)
        if (user == null) {
            call.respond(HttpStatusCode.Conflict, "User not found")
            return@post
        } else {
            val saltedHash = SaltedHash(user.hashedPassword, user.salt)
            val validPassword = hashingService.verify(
                value = request.password,
                saltedHash = saltedHash
            )


            if (!validPassword) {
                call.respond(HttpStatusCode.Conflict, "Incorrect email or password")
                return@post
            }

            val token = tokenService.generates(
                config = tokenConfig,
                TokenClaim(
                    name = "userId",
                    value = user.id.toString()
                )
            )

            call.respond(
                status = HttpStatusCode.OK,
                message = AuthResponse(token, user.id.toString())
            )
        }
    }
}

fun Route.authenticate() {
    //function takes care of token authentication and respond with Unauthorized Status code if not authorized.
    authenticate {
        get("authenticate") {
            call.respond(HttpStatusCode.OK)
        }
    }
}

fun Route.getUserId() {
    authenticate {
        get("userId") {
            val principal = call.principal<JWTPrincipal>()
            val userId = principal?.getClaim("userId", String::class)
            if(userId != null) {
                call.respond(status = HttpStatusCode.OK, message = GetUserIdResponse(userId))
            } else {
                call.respond(HttpStatusCode.Conflict, "No user found")
            }

        }
    }
}