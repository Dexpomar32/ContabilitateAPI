package com.task.crud.DTO.Mapper;

import com.task.crud.DTO.Records.ClientsRecord;
import com.task.crud.Model.Clients;
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
