package com.rubenSL.proxiTrade.services

import com.rubenSL.proxiTrade.exceptions.UserAlreadyExistsException
import com.rubenSL.proxiTrade.model.dtos.LocationDTO
import com.rubenSL.proxiTrade.model.dtos.UserDTO
import com.rubenSL.proxiTrade.model.dtos.UserResponseDTO
import com.rubenSL.proxiTrade.model.mappers.UserMapper
import com.rubenSL.proxiTrade.repositories.UserRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserService : UserDetailsService {
    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var userMapper: UserMapper

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByName(username)
            ?: throw UsernameNotFoundException("User not found with username: $username")

        return org.springframework.security.core.userdetails.User
            .withUsername(username)
            .password(user.password)
            .authorities(emptyList())
            .accountExpired(false)
            .accountLocked(false)
            .credentialsExpired(false)
            .disabled(false)
            .build()
    }

    fun getUserById(id: String): UserDTO {
        val user = userRepository.findById(id).orElseThrow { EntityNotFoundException("User with id $id not found") }
        return userMapper.toUserDTO(user)
    }

    fun getAllUsers(): List<UserResponseDTO> {
        val users = userRepository.findAll()
        return users.map { userMapper.toUserResponseDTO(it) }
    }

    fun getAllUsersByName(name: String): List<UserResponseDTO> {
        val users = userRepository.findAllByName(name)

        val userDTOs = mutableListOf<UserResponseDTO>()

        for (user in users) {
            userDTOs.add(userMapper.toUserResponseDTO(user))
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

        val user = userMapper.toUser(userDTO)
        val savedUser = userRepository.save(user)
        val userResponse = userMapper.toUserDTO(savedUser)
        userResponse.uid = savedUser.uid
        return userResponse
    }

    fun updateUser(id: String, userDTO: UserDTO): UserDTO {
        if (!userRepository.existsById(id)) {
            throw EntityNotFoundException("User with id $id not found")
        }
        val user = userMapper.toUser(userDTO)
        user.uid = id
        val updatedUser = userRepository.save(user)
        return userMapper.toUserDTO(updatedUser)
    }

    fun deleteUser(id: String) {
        if (!userRepository.existsById(id)) {
            throw EntityNotFoundException("User with id $id not found")
        }
        userRepository.deleteById(id)
    }

    fun updateUserLocation(id: String, locationDTO: LocationDTO): UserDTO {
        val user = userRepository.findById(id).orElseThrow { EntityNotFoundException("User with id $id not found") }
        user.location = userMapper.toLocation(locationDTO)
        val updatedUser = userRepository.save(user)
        return userMapper.toUserDTO(updatedUser)
    }
}

