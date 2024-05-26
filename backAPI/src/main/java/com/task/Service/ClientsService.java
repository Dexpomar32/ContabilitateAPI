package com.task.Service;

import com.task.Repository.ClientsRepository;
import com.task.DTO.Mapper.ClientsMapper;
import com.task.DTO.Records.ClientsRecord;
import com.task.Model.Clients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class ClientsService {
    private final ClientsRepository clientsRepository;
    private final ClientsMapper clientsMapper;

    @Autowired
    public ClientsService(ClientsRepository clientsRepository, ClientsMapper clientsMapper) {
        this.clientsRepository = clientsRepository;
        this.clientsMapper = clientsMapper;
    }

    public Optional<List<ClientsRecord>> getAll() {
        List<ClientsRecord> clientsRecordList = clientsRepository
                .findAll()
                .stream()
                .map(clientsMapper)
                .toList();

        return Optional.of(clientsRecordList);
    }

    public Optional<ClientsRecord> getOne(String code) {
        return Optional.ofNullable(clientsRepository.findByCode(code))
                .map(clientsMapper);
    }

    public Optional<ClientsRecord> create(Clients client) {
        if (check(client)) {
            return Optional.empty();
        }

        clientsRepository.save(client);
        return Optional.ofNullable(clientsMapper.apply(client));
    }

    public Optional<ClientsRecord> update(Clients client) {
        if (check(client)) {
            return Optional.empty();
        }

        return Optional.ofNullable(clientsRepository.findByCode(client.getCode()))
                .map(existingClient -> {
                    existingClient.setName(client.getName());
                    existingClient.setSurname(client.getSurname());
                    existingClient.setEmail(client.getEmail());
                    existingClient.setNumber(client.getNumber());
                    clientsRepository.save(existingClient);
                    return clientsMapper.apply(existingClient);
                });
    }

    public Optional<ClientsRecord> delete(String code) {
        Optional<Clients> optionalClient = Optional.ofNullable(clientsRepository.findByCode(code));

        return optionalClient.map(client -> {
            optionalClient.ifPresent(clientsRepository::delete);
            return clientsMapper.apply(client);
        });
    }

    public boolean check(Clients client) {
        return Stream.of(client.getCode(), client.getName(), client.getSurname(), client.getEmail(), client.getNumber())
                .anyMatch(field -> Objects.isNull(field) || (field instanceof String && ((String) field).isEmpty()));
    }
}
