package com.rubenSL.proxiTrade.controllers
import com.rubenSL.proxiTrade.exceptions.UserAlreadyExistsException
import com.rubenSL.proxiTrade.model.dtos.LocationDTO
import com.rubenSL.proxiTrade.model.dtos.LoginDTO
import com.rubenSL.proxiTrade.model.dtos.UserDTO
import com.rubenSL.proxiTrade.model.dtos.UserResponseDTO
import com.rubenSL.proxiTrade.security.FirebaseService
import com.rubenSL.proxiTrade.services.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController(private val userService: UserService, private val firebaseService: FirebaseService) {


    @GetMapping
    fun getCurrentUser(@RequestHeader("Authorization") idToken:String): ResponseEntity<UserDTO> {
        val token = idToken.substring(7)
        firebaseService.verifyToken(token)
        val uid = firebaseService.getUidFromToken(token)

        val user = userService.getUserById(uid)

        return ResponseEntity.ok(user)
    }
    @GetMapping("/{id}")
    fun getUserById(@RequestHeader("Authorization") idToken:String, @PathVariable id:String): ResponseEntity<UserDTO> {
        val token = idToken.substring(7)
        firebaseService.getUidFromToken(token)
        firebaseService.verifyToken(token)
        val user = userService.getUserById(id)

        return ResponseEntity.ok(user)
    }

    @GetMapping("/all")
    fun getAllUsers(@RequestHeader("Authorization") idToken: String): ResponseEntity<List<UserResponseDTO>> {
        val users = userService.getAllUsers()
        return ResponseEntity.ok(users)
    }

    @GetMapping("/all/{name}")
    fun getAllUsersByName(@RequestHeader("Authorization") idToken: String, @PathVariable name: String): ResponseEntity<List<UserResponseDTO>> {
        val uid = firebaseService.getUidFromToken(idToken.substring(7))
        val users = userService.getAllUsersByName(name).filter { it.uid != uid }
        return ResponseEntity.ok(users)
    }

    @PostMapping("/register")
    fun register(@RequestBody userDTO: UserDTO): ResponseEntity<UserDTO> {
        val passwordEncoder = BCryptPasswordEncoder()
        userDTO.password = passwordEncoder.encode(userDTO.password)
        val createdUser = userService.createUser(userDTO)
        return ResponseEntity.ok(createdUser)
    }

    @PostMapping("/login")
    fun login(@RequestBody loginDTO: LoginDTO): ResponseEntity<UserDTO> {
        val userLogged = userService.getUserByEmail(loginDTO.email)
        val passwordEncoder = BCryptPasswordEncoder()
        if (!passwordEncoder.matches(loginDTO.password, userLogged.password)) {
            throw IllegalArgumentException("Invalid password")
        }
        return ResponseEntity.ok(userLogged)
    }
    @PutMapping
    fun updateUser(@RequestHeader("Authorization") idToken: String, @RequestBody userDTO: UserDTO): ResponseEntity<UserDTO> {
        val id = firebaseService.getUidFromToken(idToken.substring(7))
        val updatedUser = userService.updateUser(id, userDTO)
        return ResponseEntity.ok(updatedUser)
    }

    @PutMapping("/profilePicture")
    fun updateProfilePicture(@RequestHeader("Authorization") idToken: String, @RequestBody base64:String?): ResponseEntity<Boolean>{
        val uid = firebaseService.getUidFromToken(idToken.substring(7))
            userService.updateProfilePicture(uid,base64)

           return ResponseEntity.ok(true)

    }

    @PutMapping("/location")
    fun updateLocation(@RequestHeader("Authorization") idToken: String, @RequestBody location: LocationDTO): ResponseEntity<Boolean> {
        val uid = firebaseService.getUidFromToken(idToken.substring(7))
        userService.updateUserLocation(uid, location)
        return ResponseEntity.ok(true)
    }

    @PutMapping("/kmRatio")
    fun updateKmRatio(@RequestHeader("Authorization") idToken: String, @RequestBody kmRatio: Double): ResponseEntity<Boolean> {
        val uid = firebaseService.getUidFromToken(idToken.substring(7))
        userService.updateKmRatio(uid, kmRatio)
        return ResponseEntity.ok(true)
    }

    @DeleteMapping
    fun deleteUser(@RequestHeader("Authorization") idToken: String): ResponseEntity<Any> {
        val id = firebaseService.getUidFromToken(idToken.substring(7))
        userService.deleteUser(id)
        return ResponseEntity.noContent().build()
    }

    @ExceptionHandler(UserAlreadyExistsException::class)
    fun handleUserAlreadyExistsException(ex: UserAlreadyExistsException): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.message)
    }
}
