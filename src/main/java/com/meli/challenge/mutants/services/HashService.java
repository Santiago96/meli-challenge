package com.meli.challenge.mutants.services;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meli.challenge.mutants.model.Request;
import lombok.extern.slf4j.Slf4j;
import net.jpountz.xxhash.XXHash64;
import net.jpountz.xxhash.XXHashFactory;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class HashService {

    private static XXHash64 hash64 = XXHashFactory.fastestInstance().hash64();
    private final int seed = 0x9735b28c;

    @Autowired
    private ObjectMapper mapper;

    public long getHash(Request request) {
        String str = serialize(request);
        return calculateHash(str.getBytes());
    }

    private String serialize(Request request) {
        try {
            return mapper.writeValueAsString(request);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Serialization issue", e);
        }
    }

    private long calculateHash(byte[] content) {
        if (ArrayUtils.isEmpty(content)) {
            return 0;
        }
        return hash64.hash(content, 0, content.length, seed);
    }
}
