package com.task.Service;

import com.task.DTO.Mapper.ProjectsMapper;
import com.task.DTO.Records.ProjectsRecord;
import com.task.Model.*;
import com.task.Repository.ClientsRepository;
import com.task.Repository.ProjectsRepository;
import com.task.Utils.CodeGenerator;
import com.task.Utils.NullAwareBeanUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class ProjectsService {
    private final ProjectsRepository projectsRepository;
    private final ProjectsMapper projectsMapper;
    private final ClientsRepository clientsRepository;
    private final EntityManager entityManager;
    private final ExpensesService expensesService;
    private final ProductsService productsService;
    private final SalesService salesService;

    @Autowired
    public ProjectsService(ProjectsRepository projectsRepository, ProjectsMapper projectsMapper,
                           ClientsRepository clientsRepository, EntityManager entityManager,
                           ExpensesService expensesService, ProductsService productsService,
                           SalesService salesService) {
        this.projectsRepository = projectsRepository;
        this.projectsMapper = projectsMapper;
        this.clientsRepository = clientsRepository;
        this.entityManager = entityManager;
        this.expensesService = expensesService;
        this.productsService = productsService;
        this.salesService = salesService;
    }

    public Optional<List<ProjectsRecord>> getAll() {
        List<ProjectsRecord> projectsRecordList = projectsRepository
                .findAll()
                .stream()
                .map(projectsMapper)
                .toList();

        return Optional.of(projectsRecordList);
    }

    public Optional<ProjectsRecord> getOne(String code) {
        return Optional.ofNullable(projectsRepository.findByCode(code))
                .map(projectsMapper);
    }

    public Optional<ProjectsRecord> create(Projects project) {
        if (check(project)) {
            return Optional.empty();
        }

        if (project.getCode() == null || project.getCode().isEmpty()) {
            project.setCode(generateUniqueCode());
        }

        Clients client = clientsRepository.findByCode(project.getClientCode());
        project.setClient(client);
        Expenses expenses = new Expenses();
        Products products = new Products();
        Sales sales = new Sales();
        expenses.setDate(Date.valueOf(LocalDate.now()));
        expenses.setAmount(project.getBudget());
        expenses.setDescription("Expense for project " + project.getCode());
        expenses.setProjectCode(project.getCode());
        products.setPrice((int) (project.getBudget() * 1.20));
        products.setDescription("Project " + project.getCode());
        products.setName(project.getName());
        products.setCode(productsService.generateUniqueCode());
        sales.setClientCode(client.getCode());
        sales.setProductCode(products.getCode());
        sales.setPrice((int) (project.getBudget() * 1.20));
        sales.setDate(Date.valueOf(LocalDate.now()));
        projectsRepository.save(project);
        expensesService.create(expenses);
        productsService.create(products);
        salesService.create(sales);
        return Optional.ofNullable(projectsMapper.apply(project));
    }

    public Optional<ProjectsRecord> update(Projects project) {
        if (project.getClientCode() != null && !project.getClientCode().isEmpty()) {
            Clients client = clientsRepository.findByCode(project.getClientCode());
            project.setClient(client);
        }

        return Optional.ofNullable(projectsRepository.findByCode(project.getCode()))
                .map(existingProject -> {
                    NullAwareBeanUtils.copyNonNullProperties(project, existingProject);
                    projectsRepository.save(existingProject);
                    return projectsMapper.apply(existingProject);
                });
    }

    public Optional<ProjectsRecord> delete(String code) {
        Optional<Projects> optionalProject = Optional.ofNullable(projectsRepository.findByCode(code));

        return optionalProject.map(client -> {
            optionalProject.ifPresent(projectsRepository::delete);
            return projectsMapper.apply(client);
        });
    }

    public Optional<CountResponse> count() {
        Long count = projectsRepository.count();
        return Optional.of(new CountResponse(count));
    }

    public Optional<List<CompletionPercentage>> percentage() {
        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("CompletionPercentage");
        List<Object[]> results = storedProcedureQuery.getResultList();
        List<CompletionPercentage> completionPercentageList = new ArrayList<>();

        for (Object[] result : results) {
            String code = (String) result[0];
            String name = (String) result[1];
            Integer percentage = (Integer) result[2];
            CompletionPercentage completionPercentage = new CompletionPercentage(code, name, percentage);
            completionPercentageList.add(completionPercentage);
        }

        return Optional.of(completionPercentageList);
    }

    public boolean check(Projects project) {
        return Stream.of(project.getName(), project.getDescription(), project.getStatus(), project.getStartDate(),
                        project.getEndDate(), project.getClientCode(), project.getPercentage(), project.getBudget())
                .anyMatch(field -> Objects.isNull(field) || (field instanceof String && ((String) field).isEmpty()));
    }

    private String generateUniqueCode() {
        String uniqueCode;
        do {
            uniqueCode = CodeGenerator.generateCode("PRJ");
        } while (projectsRepository.existsByCode(uniqueCode));
        return uniqueCode;
    }
}
