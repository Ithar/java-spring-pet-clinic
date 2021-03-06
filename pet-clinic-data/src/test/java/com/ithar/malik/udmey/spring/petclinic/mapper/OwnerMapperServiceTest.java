package com.ithar.malik.udmey.spring.petclinic.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.ithar.malik.udmey.spring.petclinic.dto.OwnerDTO;
import com.ithar.malik.udmey.spring.petclinic.model.Address;
import com.ithar.malik.udmey.spring.petclinic.model.Owner;
import com.ithar.malik.udmey.spring.petclinic.model.Pet;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;


class OwnerMapperServiceTest {

    private static OwnerMapperService service = new OwnerMapperService();

    @Test
    void mapToOwner() {

        // Given
        Long id = 10L;
        String firstName = "Jane";
        String lastName = "Doe";
        String line1 = "10 Brick lane";
        String city = "London";
        String phone = "07787778787";

        Pet pet = new Pet();
        pet.setId(1L);
        pet.setName("Bob");
        Set<Pet> pets = new HashSet<>();
        pets.add(pet);

        OwnerDTO dto = new OwnerDTO();
        dto.setId(id);
        dto.setFirstName(firstName);
        dto.setLastName(lastName);
        dto.setLine1(line1);
        dto.setCity(city);
        dto.setTelephone(phone);
        dto.setPets(pets);


        // When
        Owner owner = service.mapToOwner(dto);

        // Then
        assertNotNull(owner);
        assertEquals(id, owner.getId());
        assertEquals(firstName, owner.getFirstName());
        assertEquals(lastName, owner.getLastName());
        assertEquals(line1, owner.getAddress().getLine1());
        assertEquals(city, owner.getAddress().getCity());
        assertEquals(phone, owner.getAddress().getTelephone());
        assertEquals(pets.size(), owner.getPets().size());
    }

    @Test
    void mapToOwnerDTO() {

        // Given
        Long id = 19L;
        String firstName = "Jane";
        String lastName = "Doe";
        String line1 = "10 Brick lane";
        String city = "London";
        String phone = "07787778787";

        Address address = new Address();
        address.setLine1(line1);
        address.setCity(city);
        address.setTelephone(phone);

        Pet pet = new Pet();
        pet.setId(1L);
        pet.setName("Bob");
        Set<Pet> pets = new HashSet<>();
        pets.add(pet);

        Owner owner = new Owner();
        owner.setId(id);
        owner.setFirstName(firstName);
        owner.setLastName(lastName);
        owner.setAddress(address);
        owner.setPets(pets);

        // When
        OwnerDTO ownerDTO = service.mapToOwnerDTO(owner);

        // Then
        assertNotNull(ownerDTO);
        assertEquals(id, ownerDTO.getId());
        assertEquals(firstName, ownerDTO.getFirstName());
        assertEquals(lastName, ownerDTO.getLastName());
        assertEquals(line1, ownerDTO.getLine1());
        assertEquals(city, ownerDTO.getCity());
        assertEquals(phone, ownerDTO.getTelephone());
        assertEquals(pets.size(), ownerDTO.getPets().size());
    }
}