package com.meli.challenge.mutants.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.meli.challenge.mutants.model.Request;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class HashServiceTest {


    @InjectMocks
    private HashService hashService;

    @Mock
    private ObjectMapper objectMapper;

    private static final String[] dna = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};

    @Test
    public void itShouldReturnHash() throws JsonProcessingException {
        when(objectMapper.writeValueAsString(any())).thenReturn("ObjectAsString");

        long hash = hashService.getHash(getMockRequest());

        assertEquals(5533893429582186140L,hash);
    }

    @Test
    public void itShouldReturnHashWithValueZero() throws JsonProcessingException {
        when(objectMapper.writeValueAsString(any())).thenReturn("");

        long hash = hashService.getHash(getMockRequest());

        assertEquals(0,hash);
    }

    @Test(expected = RuntimeException.class)
    public void itShouldThrowJsonProcessingException() throws JsonProcessingException {
        when(objectMapper.writeValueAsString(any())).thenThrow(JsonProcessingException.class);

        hashService.getHash(getMockRequest());
    }

    private Request getMockRequest() {
        Request request = new Request();
        request.setDna(dna);
        return request;
    }
}