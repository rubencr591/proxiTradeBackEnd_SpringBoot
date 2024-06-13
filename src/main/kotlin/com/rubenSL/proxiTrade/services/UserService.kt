package com.rubenSL.proxiTrade.services

import com.rubenSL.proxiTrade.exceptions.UserAlreadyExistsException
import com.rubenSL.proxiTrade.model.dtos.LocationDTO
import com.rubenSL.proxiTrade.model.dtos.UserDTO
import com.rubenSL.proxiTrade.model.dtos.UserResponseDTO
import com.rubenSL.proxiTrade.model.entities.ProfilePicture
import com.rubenSL.proxiTrade.model.entities.User
import com.rubenSL.proxiTrade.model.mappers.LocationMapper
import com.rubenSL.proxiTrade.model.mappers.UserMapper
import com.rubenSL.proxiTrade.repositories.LocationRepository
import com.rubenSL.proxiTrade.repositories.ProfilePictureRepository
import com.rubenSL.proxiTrade.repositories.UserRepository
import jakarta.persistence.EntityNotFoundException
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.sql.Blob
import java.util.*
import javax.sql.rowset.serial.SerialBlob

@Service
class UserService @Autowired constructor( private val userRepository: UserRepository,
                                          private val userMapper: UserMapper,
                                          private val locationMapper: LocationMapper,
                                            private val locationRepository: LocationRepository) {


    @Autowired
    private lateinit var profilePictureRepository: ProfilePictureRepository

    //Transactional es una anotación que se utiliza para indicar que un método debe ser ejecutado dentro de una transacción.
    @Transactional
    fun updateProfilePicture(userId: String, imageBase64: String?): User {
        val user = userRepository.findById(userId).orElseThrow { Exception("User not found") }

        val profilePicture = profilePictureRepository.findByUserUid(userId).orElse(ProfilePicture())

        if (imageBase64 != null) {
            val imageBytes = Base64.getDecoder().decode(imageBase64)
            val imageBlob: Blob = SerialBlob(imageBytes)
            profilePicture.base64 = imageBlob
        } else {
            profilePicture.base64 = null
        }

        profilePicture.user = user

        user.profilePicture = profilePicture
        profilePictureRepository.save(profilePicture)

        return userRepository.save(user)
    }


    fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByEmail(username) ?: throw UsernameNotFoundException("User with email $username not found")
        return org.springframework.security.core.userdetails.User(user.email, user.password, emptyList())
    }

    fun getUserByEmail(email: String): UserDTO {
        val user = userRepository.findByEmail(email) ?: throw EntityNotFoundException("User with email $email not found")
        return userMapper.toUserDTO(user)
    }

    fun getUserById(id: String): UserDTO {
        val user = userRepository.findById(id).orElseThrow { EntityNotFoundException("User with id $id not found") }
        val location = user.location
        return userMapper.toUserDTO(user)
    }

    fun getUserByUid(uid: String): User {
        return userRepository.findByUid(uid) ?: throw EntityNotFoundException("User with uid $uid not found")
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

    fun updateUserLocation(id: String, locationDTO: LocationDTO) {
        val user = userRepository.findById(id).orElseThrow { EntityNotFoundException("User with id $id not found") }

        val currentLocation = user.location


        if (currentLocation != null) {
            currentLocation.city = locationDTO.city
            currentLocation.community = locationDTO.community
            currentLocation.country = locationDTO.country
            currentLocation.latitude = locationDTO.latitude
            currentLocation.longitude = locationDTO.longitude
            currentLocation.numberLetter = locationDTO.numberLetter
            currentLocation.postalCode = locationDTO.postalCode
            currentLocation.province = locationDTO.province
            currentLocation.street = locationDTO.street

            locationRepository.save(currentLocation)


        }else{
            val location = locationMapper.toLocation(locationDTO)
            locationRepository.save(location)
        }



    }

    fun updateKmRatio(uid: String, kmRatio: Double) {
        val user = userRepository.findByUid(uid) ?: throw EntityNotFoundException("User with uid $uid not found")
        user.kmRatio = kmRatio
        userRepository.save(user)
    }
}

