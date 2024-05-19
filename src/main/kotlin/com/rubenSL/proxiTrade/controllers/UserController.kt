package com.rubenSL.proxiTrade.controllers
import com.rubenSL.proxiTrade.exceptions.UserAlreadyExistsException
import com.rubenSL.proxiTrade.model.dtos.LoginDTO
import com.rubenSL.proxiTrade.model.dtos.UserDTO
import com.rubenSL.proxiTrade.model.dtos.UserResponseDTO
import com.rubenSL.proxiTrade.model.entities.ProfilePicture
import com.rubenSL.proxiTrade.services.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController(private val userService: UserService) {

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: String): ResponseEntity<UserDTO> {
        val user = userService.getUserById(id)
        return ResponseEntity.ok(user)
    }

    @GetMapping("/all")
    fun getAllUsers(): ResponseEntity<List<UserResponseDTO>> {
        val users = userService.getAllUsers()
        return ResponseEntity.ok(users)
    }

    @GetMapping("/all/{name}")
    fun getAllUsersByName(@PathVariable name: String): ResponseEntity<List<UserResponseDTO>> {
        val users = userService.getAllUsersByName(name)
        return ResponseEntity.ok(users)
    }

    @PostMapping("/register")
    fun register(@RequestBody userDTO: UserDTO): ResponseEntity<UserDTO> {
        val createdUser = userService.createUser(userDTO)
        return ResponseEntity.ok(createdUser)
    }

    @PostMapping("/login")
    fun login(@RequestBody loginDTO: LoginDTO): ResponseEntity<UserDTO> {
        val userLogged = userService.getUserByEmail(loginDTO.email)
        if (userLogged.password != loginDTO.password) {
            throw IllegalArgumentException("Invalid password")
        }
        return ResponseEntity.ok(userLogged)
    }

    @PutMapping("/{id}")
    fun updateUser(@PathVariable id: String, @RequestBody userDTO: UserDTO): ResponseEntity<UserDTO> {
        val updatedUser = userService.updateUser(id, userDTO)
        return ResponseEntity.ok(updatedUser)
    }

    @PutMapping("/{uid}/profilePicture")
    fun updateProfilePicture(@PathVariable uid: String, @RequestBody base64:String): ResponseEntity<String> {
        return try {

            val profilePicture = ProfilePicture()

            userService.updateProfilePicture(uid, profilePicture, base64)

            ResponseEntity.ok("Profile picture updated successfully")
        } catch (e: Exception) {
            ResponseEntity.status(500).body("Error updating profile picture: ${e.message}")
        }
    }

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: String): ResponseEntity<Unit> {
        userService.deleteUser(id)
        return ResponseEntity.noContent().build()
    }

    @ExceptionHandler(UserAlreadyExistsException::class)
    fun handleUserAlreadyExistsException(ex: UserAlreadyExistsException): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.message)
    }
}
