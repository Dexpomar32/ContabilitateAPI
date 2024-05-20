package com.task.crud.Service;

import com.task.crud.DTO.Mapper.ContractsMapper;
import com.task.crud.DTO.Records.ContractsRecord;
import com.task.crud.Model.Contracts;
import com.task.crud.Repository.ContractsRepository;
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

    @Autowired
    public ContractsService(ContractsRepository contractsRepository, ContractsMapper contractsMapper) {
        this.contractsRepository = contractsRepository;
        this.contractsMapper = contractsMapper;
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

        contractsRepository.save(contract);
        return Optional.ofNullable(contractsMapper.apply(contract));
    }

    public Optional<ContractsRecord> update(Contracts contract) {
        if (check(contract)) {
            return Optional.empty();
        }

        return Optional.ofNullable(contractsRepository.findByCode(contract.getCode()))
                .map(existingContract -> {
                    existingContract.setDate(contract.getDate());
                    existingContract.setPeriod(contract.getPeriod());
                    existingContract.getClient().setCode(contract.getCode());
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
        return Stream.of(contract.getCode(), contract.getDate(), contract.getPeriod(), contract.getClient().getCode())
                .anyMatch(field -> Objects.isNull(field) || (field instanceof String && ((String) field).isEmpty()));
    }
}
