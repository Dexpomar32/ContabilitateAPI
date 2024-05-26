package com.task.Controller;

import com.task.Model.EndpointDetail;
import com.task.Model.EndpointDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@SuppressWarnings("unused")
@RestController
@RequestMapping("/endpoints")
public class EndpointController {
    @GetMapping
    public List<EndpointDetails> getEndpoints() {
        EndpointDetails clients = getClientsInfo();
        EndpointDetails contracts = getContractsInfo();
        EndpointDetails documents = getDocumentsInfo();
        EndpointDetails expenses = getExpensesInfo();
        EndpointDetails notes = getNotesInfo();
        EndpointDetails projects = getProjectsInfo();
        EndpointDetails reports = getReportsInfo();
        EndpointDetails sales = getSalesInfo();

        return List.of(clients, contracts, documents, expenses, notes, projects, reports, sales);
    }

    private static EndpointDetails getClientsInfo() {
        EndpointDetails clients = new EndpointDetails();
        clients.setClassName("Clients");

        List<EndpointDetail> navigation = List.of(
                new EndpointDetail("GET_ALL", "/clients/getAll", "GET", List.of(), List.of()),
                new EndpointDetail("GET_ONE", "/clients/getOne", "GET", List.of("Code"), List.of("code")),
                new EndpointDetail("CREATE", "/clients/create", "POST", List.of("Code", "Name",
                        "Surname", "Email", "Number"), List.of("code", "name", "surname", "email", "number")),
                new EndpointDetail("UPDATE", "/clients/update", "POST", List.of("Code", "Name",
                        "Surname", "Email", "Number"), List.of("code", "name", "surname", "email", "number")),
                new EndpointDetail("DELETE", "/clients/delete", "DELETE", List.of("Code"), List.of("code"))
        );

        clients.setNavigation(navigation);
        return clients;
    }

    private static EndpointDetails getContractsInfo() {
        EndpointDetails contracts = new EndpointDetails();
        contracts.setClassName("Contracts");

        List<EndpointDetail> navigation = List.of(
                new EndpointDetail("GET_ALL", "/contracts/getAll", "GET", List.of(), List.of()),
                new EndpointDetail("GET_ONE", "/contracts/getOne", "GET", List.of("Code"), List.of("code")),
                new EndpointDetail("CREATE", "/contracts/create", "POST", List.of("Code", "Date",
                        "Period", "Client Code"), List.of("code", "date", "period", "clientCode")),
                new EndpointDetail("UPDATE", "/contracts/update", "POST", List.of("Code", "Date",
                        "Period", "Client Code"), List.of("code", "date", "period", "clientCode")),
                new EndpointDetail("DELETE", "/contracts/delete", "DELETE", List.of("Code"), List.of("code"))
        );

        contracts.setNavigation(navigation);
        return contracts;
    }

    private static EndpointDetails getDocumentsInfo() {
        EndpointDetails documents = new EndpointDetails();
        documents.setClassName("Documents");

        List<EndpointDetail> navigation = List.of(
                new EndpointDetail("GET_ALL", "/documents/getAll", "GET", List.of(), List.of()),
                new EndpointDetail("GET_ONE", "/documents/getOne", "GET", List.of("Code"), List.of("code")),
                new EndpointDetail("CREATE", "/documents/create", "POST", List.of("Code", "Type",
                        "Date", "Text", "Client Code"), List.of("code", "type", "date", "text", "clientCode")),
                new EndpointDetail("UPDATE", "/documents/update", "POST", List.of("Code", "Type",
                        "Date", "Text", "Client Code"), List.of("code", "type", "date", "text", "clientCode")),
                new EndpointDetail("DELETE", "/documents/delete", "DELETE", List.of("Code"), List.of("code"))
        );

        documents.setNavigation(navigation);
        return documents;
    }

    private static EndpointDetails getExpensesInfo() {
        EndpointDetails expenses = new EndpointDetails();
        expenses.setClassName("Expenses");

        List<EndpointDetail> navigation = List.of(
                new EndpointDetail("GET_ALL", "/expenses/getAll", "GET", List.of(), List.of()),
                new EndpointDetail("GET_ONE", "/expenses/getOne", "GET", List.of("Code"), List.of("code")),
                new EndpointDetail("CREATE", "/expenses/create", "POST", List.of("Code", "Date",
                        "Amount", "Description", "Project Code"), List.of("code", "date", "amount", "description", "projectCode")),
                new EndpointDetail("UPDATE", "/expenses/update", "POST", List.of("Code", "Date",
                        "Amount", "Description", "Project Code"), List.of("code", "date", "amount", "description", "projectCode")),
                new EndpointDetail("DELETE", "/expenses/delete", "DELETE", List.of("Code"), List.of("code"))
        );

        expenses.setNavigation(navigation);
        return expenses;
    }

    private static EndpointDetails getNotesInfo() {
        EndpointDetails notes = new EndpointDetails();
        notes.setClassName("Notes");

        List<EndpointDetail> navigation = List.of(
                new EndpointDetail("GET_ALL", "/notes/getAll", "GET", List.of(), List.of()),
                new EndpointDetail("GET_ONE", "/notes/getOne", "GET", List.of("Code"), List.of("code")),
                new EndpointDetail("CREATE", "/notes/create", "POST", List.of("Code", "Text",
                        "Date", "Project Code"), List.of("code", "text", "date", "projectCode")),
                new EndpointDetail("UPDATE", "/notes/update", "POST", List.of("Code", "Text",
                        "Date", "Project Code"), List.of("code", "text", "date", "projectCode")),
                new EndpointDetail("DELETE", "/notes/delete", "DELETE", List.of("Code"), List.of("code"))
        );

        notes.setNavigation(navigation);
        return notes;
    }

    private static EndpointDetails getProjectsInfo() {
        EndpointDetails projects = new EndpointDetails();
        projects.setClassName("Projects");

        List<EndpointDetail> navigation = List.of(
                new EndpointDetail("GET_ALL", "/projects/getAll", "GET", List.of(), List.of()),
                new EndpointDetail("GET_ONE", "/projects/getOne", "GET", List.of("Code"), List.of("code")),
                new EndpointDetail("CREATE", "/projects/create", "POST", List.of("Code", "Name",
                        "Description", "Status", "Start Date", "End Date", "Client Code"), List.of("code", "name", "description", "status",
                        "startDate", "endDate", "clientCode")),
                new EndpointDetail("UPDATE", "/projects/update", "POST", List.of("Code", "Name",
                        "Description", "Status", "Start Date", "End Date", "Client Code"), List.of("code", "name", "description", "status",
                        "startDate", "endDate", "clientCode")),
                new EndpointDetail("DELETE", "/projects/delete", "DELETE", List.of("Code"), List.of("code"))
        );

        projects.setNavigation(navigation);
        return projects;
    }

    private static EndpointDetails getReportsInfo() {
        EndpointDetails reports = new EndpointDetails();
        reports.setClassName("Reports");

        List<EndpointDetail> navigation = List.of(
                new EndpointDetail("GET_ALL", "/reports/getAll", "GET", List.of(), List.of()),
                new EndpointDetail("GET_ONE", "/reports/getOne", "GET", List.of("Code"), List.of("code")),
                new EndpointDetail("CREATE", "/reports/create", "POST", List.of("Code", "Date",
                        "Text", "Project Code"), List.of("code", "date", "text", "projectCode")),
                new EndpointDetail("UPDATE", "/reports/update", "POST", List.of("Code", "Date",
                        "Text", "Project Code"), List.of("code", "date", "text", "projectCode")),
                new EndpointDetail("DELETE", "/reports/delete", "DELETE", List.of("Code"), List.of("code"))
        );

        reports.setNavigation(navigation);
        return reports;
    }

    private static EndpointDetails getSalesInfo() {
        EndpointDetails sales = new EndpointDetails();
        sales.setClassName("Sales");

        List<EndpointDetail> navigation = List.of(
                new EndpointDetail("GET_ALL", "/sales/getAll", "GET", List.of(), List.of()),
                new EndpointDetail("GET_ONE", "/sales/getOne", "GET", List.of("Code"), List.of("code")),
                new EndpointDetail("CREATE", "/sales/create", "POST", List.of("Code", "Date",
                        "Amount", "Client Code"), List.of("code", "date", "amount", "clientCode")),
                new EndpointDetail("UPDATE", "/sales/update", "POST", List.of("Code", "Date",
                        "Amount", "Client Code"), List.of("code", "date", "amount", "clientCode")),
                new EndpointDetail("DELETE", "/sales/delete", "DELETE", List.of("Code"), List.of("code"))
        );

        sales.setNavigation(navigation);
        return sales;
    }
}
