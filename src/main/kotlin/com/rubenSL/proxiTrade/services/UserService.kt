package com.rubenSL.proxiTrade.services

import com.rubenSL.proxiTrade.exceptions.UserAlreadyExistsException
import com.rubenSL.proxiTrade.model.dtos.LocationDTO
import com.rubenSL.proxiTrade.model.dtos.UserDTO
import com.rubenSL.proxiTrade.model.dtos.UserResponseDTO
import com.rubenSL.proxiTrade.model.entities.ProfilePicture
import com.rubenSL.proxiTrade.model.entities.User
import com.rubenSL.proxiTrade.model.mappers.LocationMapper
import com.rubenSL.proxiTrade.model.mappers.UserMapper
import com.rubenSL.proxiTrade.repositories.UserRepository
import jakarta.persistence.EntityNotFoundException
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.sql.Blob
import java.util.*
import javax.sql.rowset.serial.SerialBlob

@Service
class UserService  {
    @Autowired
    private lateinit var userRepository: UserRepository

    //Transactional es una anotación que se utiliza para indicar que un método debe ser ejecutado dentro de una transacción.
    @Transactional
    fun updateProfilePicture(userId: String, profilePicture: ProfilePicture, imageBase64: String?): User {
        val user = userRepository.findById(userId).orElseThrow { Exception("User not found") }

        if(imageBase64 != null){
            // Convertir la cadena base64 a Blob
            val imageBytes = Base64.getDecoder().decode(imageBase64)
            val imageBlob: Blob = SerialBlob(imageBytes)
            // Asignar el Blob a profilePicture
            profilePicture.base64 = imageBlob
            profilePicture.user = user
            user.profilePicture = profilePicture
        }else{
            profilePicture.base64 = null
            profilePicture.user = user
            user.profilePicture = profilePicture
        }

        return userRepository.save(user) //Update user with new profile picture
    }

    fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByEmail(username) ?: throw UsernameNotFoundException("User with email $username not found")
        return org.springframework.security.core.userdetails.User(user.email, user.password, emptyList())
    }

    fun getUserByEmail(email: String): UserDTO {
        val user = userRepository.findByEmail(email) ?: throw EntityNotFoundException("User with email $email not found")
        return UserMapper.toUserDTO(user)
    }

    fun getUserById(id: String): UserDTO {
        val user = userRepository.findById(id).orElseThrow { EntityNotFoundException("User with id $id not found") }
        return UserMapper.toUserDTO(user)
    }

    fun getAllUsers(): List<UserResponseDTO> {
        val users = userRepository.findAll()
        return users.map { UserMapper.toUserResponseDTO(it) }
    }

    fun getAllUsersByName(name: String): List<UserResponseDTO> {
        val users = userRepository.findAllByName(name)

        val userDTOs = mutableListOf<UserResponseDTO>()

        for (user in users) {
            userDTOs.add(UserMapper.toUserResponseDTO(user))
        }

        return userDTOs
    }

    fun createUser(userDTO: UserDTO): UserDTO {
        userRepository.findByEmail(userDTO.email!!)?.let {
            throw UserAlreadyExistsException("User with email ${userDTO.email} already exists")
        }
        userRepository.findByName(userDTO.name!!)?.let {
            throw UserAlreadyExistsException("User with username ${userDTO.name} already exists")
        }

        userRepository.findByPhone(userDTO.phone!!)?.let {
            throw UserAlreadyExistsException("User with phone ${userDTO.phone} already exists")
        }

        val user = UserMapper.toUser(userDTO)
        val savedUser = userRepository.save(user)
        val userResponse = UserMapper.toUserDTO(savedUser)
        userResponse.uid = savedUser.uid
        return userResponse
    }

    fun updateUser(id: String, userDTO: UserDTO): UserDTO {
        if (!userRepository.existsById(id)) {
            throw EntityNotFoundException("User with id $id not found")
        }
        val user = UserMapper.toUser(userDTO)
        user.uid = id
        val updatedUser = userRepository.save(user)
        return UserMapper.toUserDTO(updatedUser)
    }

    fun deleteUser(id: String) {
        if (!userRepository.existsById(id)) {
            throw EntityNotFoundException("User with id $id not found")
        }
        userRepository.deleteById(id)
    }

    fun updateUserLocation(id: String, locationDTO: LocationDTO): UserDTO {
        val user = userRepository.findById(id).orElseThrow { EntityNotFoundException("User with id $id not found") }
        user.location = LocationMapper.toLocation(locationDTO)
        val updatedUser = userRepository.save(user)
        return UserMapper.toUserDTO(updatedUser)
    }
}

