package com.task.DTO.Mapper;

import com.task.DTO.Records.ClientsRecord;
import com.task.Model.Clients;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ClientsMapper implements Function<Clients, ClientsRecord> {
    @Override
    public ClientsRecord apply(Clients clients) {
        return new ClientsRecord(
                clients.getCode(),
                clients.getName(),
                clients.getSurname(),
                clients.getEmail(),
                clients.getNumber()
        );
    }
}