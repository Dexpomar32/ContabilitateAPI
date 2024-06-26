package com.task.Service;

import com.task.Model.Contracts;
import com.task.Model.CountResponse;
import com.task.Repository.ClientsRepository;
import com.task.DTO.Mapper.ClientsMapper;
import com.task.DTO.Records.ClientsRecord;
import com.task.Model.Clients;
import com.task.Utils.CodeGenerator;
import com.task.Utils.NullAwareBeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class ClientsService {
    private final ClientsRepository clientsRepository;
    private final ClientsMapper clientsMapper;
    private final ContractsService contractsService;

    @Autowired
    public ClientsService(ClientsRepository clientsRepository, ClientsMapper clientsMapper, ContractsService contractsService) {
        this.clientsRepository = clientsRepository;
        this.clientsMapper = clientsMapper;
        this.contractsService = contractsService;
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

        if (client.getCode() == null || client.getCode().isEmpty()) {
            client.setCode(generateUniqueCode());
        }

        Contracts contract = new Contracts();
        contract.setClient(client);
        contract.setDate(Date.valueOf(LocalDate.now()));
        contract.setPeriod(Date.valueOf(LocalDate.now().plusMonths(5)));
        contract.setIsValid(true);
        clientsRepository.save(client);
        contractsService.create(contract);
        return Optional.ofNullable(clientsMapper.apply(client));
    }

    public Optional<ClientsRecord> update(Clients client) {
        return Optional.ofNullable(clientsRepository.findByCode(client.getCode()))
                .map(existingClient -> {
                    NullAwareBeanUtils.copyNonNullProperties(client, existingClient);
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

    public Optional<CountResponse> count() {
        Long count = clientsRepository.count();
        return Optional.of(new CountResponse(count));
    }

    public boolean check(Clients client) {
        return Stream.of(client.getName(), client.getSurname(), client.getEmail(), client.getNumber())
                .anyMatch(field -> Objects.isNull(field) || (field instanceof String && ((String) field).isEmpty()));
    }

    private String generateUniqueCode() {
        String uniqueCode;
        do {
            uniqueCode = CodeGenerator.generateCode("CL");
        } while (clientsRepository.existsByCode(uniqueCode));
        return uniqueCode;
    }
}
