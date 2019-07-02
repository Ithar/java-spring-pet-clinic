package com.ithar.malik.udmey.spring.petclinic.controllers;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.ithar.malik.udmey.spring.petclinic.model.Owner;
import com.ithar.malik.udmey.spring.petclinic.service.OwnerService;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    @Mock
    private OwnerService ownerService;

    @InjectMocks
    private OwnerController controller;

    private Set<Owner> owners = new HashSet<>();

    @BeforeEach
    void setUp() {
        Owner owner1 = new Owner();
        owner1.setId(1L);
        Owner owner2 = new Owner();
        owner2.setId(2L);

        owners.add(owner1);
        owners.add(owner2);

        when(ownerService.findAll()).thenReturn(owners);
    }

    @Test
    void list() throws Exception {

        // Given

        // When
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        // Then
        mockMvc.perform(get("/owners"))
            .andExpect(status().isOk())
            .andExpect(view().name("owners/list"))
            .andExpect(model().attribute("owners", hasSize(owners.size())));
    }

    @Test
    void listByIndex() throws Exception {

        // Given setup

        // When
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        // Then
        mockMvc.perform(get("/owners/index"))
            .andExpect(status().isOk())
            .andExpect(view().name("owners/list"))
            .andExpect(model().attribute("owners", hasSize(owners.size())));
    }

    @Test
    void find() throws Exception {

        // Given setup

        // When
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        // Then
        mockMvc.perform(get("/owners/find"))
            .andExpect(status().isOk())
            .andExpect(view().name("notimplemented"));
    }
}