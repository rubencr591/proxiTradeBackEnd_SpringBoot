package com.rubenSL.proxiTrade.controllers

import com.rubenSL.proxiTrade.model.dtos.LocationDTO
import com.rubenSL.proxiTrade.model.dtos.LoginDTO
import com.rubenSL.proxiTrade.model.dtos.UserDTO
import com.rubenSL.proxiTrade.model.dtos.UserResponseDTO
import com.rubenSL.proxiTrade.model.entities.User
import com.rubenSL.proxiTrade.security.FirebaseService
import com.rubenSL.proxiTrade.services.UserService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class UserControllerTest {
    private val userService: UserService = Mockito.mock(UserService::class.java)
    private val firebaseService: FirebaseService = Mockito.mock(FirebaseService::class.java)
    private val userController = UserController(userService, firebaseService)
    private val passwordEncoder: BCryptPasswordEncoder = Mockito.mock(BCryptPasswordEncoder::class.java)

    @Test
    fun getCurrentUser() {
        val idToken = "Bearer testToken"
        val uid = "testUid"
        val expectedUser = UserDTO(uid, "name", "email", "password", 1234567890L, null, "profilePicture", 50.0)
        Mockito.`when`(firebaseService.getUidFromToken(idToken.substring(7))).thenReturn(uid)
        Mockito.`when`(userService.getUserById(uid)).thenReturn(expectedUser)

        val responseEntity = userController.getCurrentUser(idToken)
        val actualUser = responseEntity.body

        assertEquals(expectedUser, actualUser)
    }

    @Test
    fun getUserById() {
        val idToken = "Bearer testToken"
        val uid = "testUid"
        val expectedUser = UserDTO(uid, "name", "email", "password", 1234567890L, null, "profilePicture", 50.0)
        Mockito.`when`(firebaseService.getUidFromToken(idToken.substring(7))).thenReturn(uid)
        Mockito.`when`(userService.getUserById(uid)).thenReturn(expectedUser)

        val responseEntity = userController.getUserById(idToken, uid)
        val actualUser = responseEntity.body

        assertEquals(expectedUser, actualUser)
    }

    @Test
    fun getAllUsers() {
        val idToken = "Bearer testToken"
        val expectedUsers = listOf(UserResponseDTO("uid", "name", "email", null, "profilePicture", 50.0))
        Mockito.`when`(userService.getAllUsers()).thenReturn(expectedUsers)

        val responseEntity = userController.getAllUsers(idToken)
        val actualUsers = responseEntity.body

        assertEquals(expectedUsers, actualUsers)
    }

    @Test
    fun getAllUsersByName() {
        val idToken = "Bearer testToken"
        val name = "name"
        val expectedUsers = listOf(UserResponseDTO("uid", "name", "email", null, "profilePicture", 50.0))
        Mockito.`when`(userService.getAllUsersByName(name)).thenReturn(expectedUsers)

        val responseEntity = userController.getAllUsersByName(idToken, name)
        val actualUsers = responseEntity.body

        assertEquals(expectedUsers, actualUsers)
    }

    @Test
    fun register() {
        val userDTO = UserDTO("uid", "name", "email", "password", 1234567890L, null, "profilePicture", 50.0)
        Mockito.`when`(userService.createUser(userDTO)).thenReturn(userDTO)

        val responseEntity = userController.register(userDTO)
        val actualUser = responseEntity.body

        assertEquals(userDTO, actualUser)
    }

    @Test
    fun login_SuccessfulLogin_ReturnsUserDTO() {
        // Arrange
        val loginDTO = LoginDTO("email@example.com", "password")
        val plainPassword = "password"
        val hashedPassword = BCryptPasswordEncoder().encode(plainPassword) // Codificar la contraseña

        val expectedUser = UserDTO("uid", "name", "email@example.com", hashedPassword, 1234567890L, null, "profilePicture", 50.0)

        `when`(userService.getUserByEmail(loginDTO.email)).thenReturn(expectedUser)
        `when`(passwordEncoder.matches(plainPassword, expectedUser.password)).thenReturn(true) // Comparar con la contraseña sin codificar

        // Act
        val responseEntity: ResponseEntity<UserDTO> = userController.login(loginDTO)

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.statusCode)
        assertEquals(expectedUser, responseEntity.body)
    }

    @Test
    fun login_InvalidPassword_ThrowsIllegalArgumentException() {
        // Arrange
        val loginDTO = LoginDTO("email@example.com", "password")
        val expectedUser = UserDTO("uid", "name", "email@example.com", "passwordHash", 1234567890L, null, "profilePicture", 50.0)

        `when`(userService.getUserByEmail(loginDTO.email)).thenReturn(expectedUser)
        `when`(passwordEncoder.matches(loginDTO.password, expectedUser.password)).thenReturn(false)

        // Act & Assert
        try {
            userController.login(loginDTO)
            // If login() does not throw IllegalArgumentException, fail the test
            throw AssertionError("Expected IllegalArgumentException was not thrown")
        } catch (ex: IllegalArgumentException) {
            assertEquals("Invalid password", ex.message)
        }
    }
    @Test
    fun updateUser() {
        val idToken = "Bearer testToken"
        val uid = "testUid"
        val userDTO = UserDTO(uid, "name", "email", "password", 1234567890L, null, "profilePicture", 50.0)
        Mockito.`when`(firebaseService.getUidFromToken(idToken.substring(7))).thenReturn(uid)
        Mockito.`when`(userService.updateUser(uid, userDTO)).thenReturn(userDTO)

        val actualUser = userController.updateUser(idToken, userDTO)

        assertEquals(userDTO, actualUser.body)

    }

    @Test
    fun updateProfilePicture() {
        val idToken = "Bearer testToken"
        val uid = "testUid"
        val base64 = "base64"
        Mockito.`when`(firebaseService.getUidFromToken(idToken.substring(7))).thenReturn(uid)

        val response = userController.updateProfilePicture(idToken, base64)

        assertEquals(ResponseEntity.ok(true), response)
    }

    @Test
    fun updateLocation() {
        val idToken = "Bearer testToken"
        val uid = "testUid"
        val locationDTO = LocationDTO(1L, "street", "1241", "5", "spain", "city", "country", "province", 1.0f, 1.0f)
        Mockito.`when`(firebaseService.getUidFromToken(idToken.substring(7))).thenReturn(uid)

        val response = userController.updateLocation(idToken, locationDTO)

        assertEquals(ResponseEntity.ok(true), response)
    }

    @Test
    fun deleteUser() {
        // Arrange
        val idToken = "Bearer testToken"
        val uid = "testUid"
        `when`(firebaseService.getUidFromToken(idToken.substring(7))).thenReturn(uid)

        // Act
        val responseEntity: ResponseEntity<Any> = userController.deleteUser(idToken)

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.statusCode)
    }

}