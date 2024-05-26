package com.task.Service;

import com.task.DTO.Mapper.ContractsMapper;
import com.task.DTO.Records.ContractsRecord;
import com.task.Model.Clients;
import com.task.Model.Contracts;
import com.task.Repository.ClientsRepository;
import com.task.Repository.ContractsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class ContractsService {
    private final ContractsRepository contractsRepository;
    private final ContractsMapper contractsMapper;
    private final ClientsRepository clientsRepository;

    @Autowired
    public ContractsService(ContractsRepository contractsRepository, ContractsMapper contractsMapper, ClientsRepository clientsRepository) {
        this.contractsRepository = contractsRepository;
        this.contractsMapper = contractsMapper;
        this.clientsRepository = clientsRepository;
    }

    public Optional<List<ContractsRecord>> getAll() {
        List<ContractsRecord> contractsRecordList = contractsRepository
                .findAll()
                .stream()
                .map(contractsMapper)
                .toList();

        return Optional.of(contractsRecordList);
    }

    public Optional<ContractsRecord> getOne(String code) {
        return Optional.ofNullable(contractsRepository.findByCode(code))
                .map(contractsMapper);
    }

    public Optional<ContractsRecord> create(Contracts contract) {
        if (check(contract)) {
            return Optional.empty();
        }

        Clients client = clientsRepository.findByCode(contract.getClientCode());
        contract.setClient(client);
        contractsRepository.save(contract);
        return Optional.ofNullable(contractsMapper.apply(contract));
    }

    public Optional<ContractsRecord> update(Contracts contract) {
        if (check(contract)) {
            return Optional.empty();
        }

        Clients client = clientsRepository.findByCode(contract.getClientCode());
        contract.setClient(client);

        return Optional.ofNullable(contractsRepository.findByCode(contract.getCode()))
                .map(existingContract -> {
                    existingContract.setDate(contract.getDate());
                    existingContract.setPeriod(contract.getPeriod());
                    existingContract.setClient(contract.getClient());
                    contractsRepository.save(existingContract);
                    return contractsMapper.apply(existingContract);
                });
    }

    public Optional<ContractsRecord> delete(String code) {
        Optional<Contracts> optionalContract = Optional.ofNullable(contractsRepository.findByCode(code));

        return optionalContract.map(contract -> {
            optionalContract.ifPresent(contractsRepository::delete);
            return contractsMapper.apply(contract);
        });
    }

    public boolean check(Contracts contract) {
        return Stream.of(contract.getCode(), contract.getDate(), contract.getPeriod(), contract.getClientCode())
                .anyMatch(field -> Objects.isNull(field) || (field instanceof String && ((String) field).isEmpty()));
    }
}
