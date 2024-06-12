package com.task.Service;

import com.task.DTO.Mapper.ContractsMapper;
import com.task.DTO.Records.ContractsRecord;
import com.task.Model.Clients;
import com.task.Model.Contracts;
import com.task.Repository.ClientsRepository;
import com.task.Repository.ContractsRepository;
import com.task.Utils.CodeGenerator;
import com.task.Utils.NullAwareBeanUtils;
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

        if (contract.getCode() == null || contract.getCode().isEmpty()) {
            contract.setCode(generateUniqueCode());
        }

        System.out.println(contract);
        Clients client = clientsRepository.findByCode(contract.getClient().getCode());
        contract.setClient(client);
        contractsRepository.save(contract);
        return Optional.ofNullable(contractsMapper.apply(contract));
    }

    public Optional<ContractsRecord> update(Contracts contract) {
        if (contract.getClientCode() != null && !contract.getClientCode().isEmpty()) {
            Clients client = clientsRepository.findByCode(contract.getClientCode());
            contract.setClient(client);
        }

        return Optional.ofNullable(contractsRepository.findByCode(contract.getCode()))
                .map(existingContract -> {
                    NullAwareBeanUtils.copyNonNullProperties(contract, existingContract);
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

    public Boolean findByClientCode(String code) {
        Optional<ContractsRecord> contractsRecord = Optional.ofNullable(contractsRepository.findByClientCode(code))
                .map(contractsMapper);

        Contracts contracts = new Contracts();
        contractsRecord.ifPresent(record -> contracts.setIsValid(!record.isValid()));
        contractsRecord.ifPresent(record -> contracts.setCode(record.code()));
        update(contracts);
        return contracts.getIsValid();
    }

    public boolean check(Contracts contract) {
        return Stream.of(contract.getDate(), contract.getPeriod(), contract.getIsValid())
                .anyMatch(Objects::isNull);
    }

    private String generateUniqueCode() {
        String uniqueCode;
        do {
            uniqueCode = CodeGenerator.generateCode("CO");
        } while (contractsRepository.existsByCode(uniqueCode));
        return uniqueCode;
    }
}
