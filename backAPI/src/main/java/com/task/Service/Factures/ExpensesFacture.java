package com.task.Service.Factures;

import com.codingerror.model.AddressDetails;
import com.codingerror.model.HeaderDetails;
import com.codingerror.model.Product;
import com.codingerror.model.ProductTableHeader;
import com.codingerror.service.CodingErrorPdfInvoiceCreator;
import com.task.DTO.Records.*;
import com.task.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ExpensesFacture {
    private final ExpensesService expensesService;
    private final ProjectsService projectsService;
    private final String pdfDirectory;

    @Autowired
    public ExpensesFacture(ExpensesService expensesService, ProjectsService projectsService,
                          @Value("${pdf.directory}") String pdfDirectory) {
        this.expensesService = expensesService;
        this.projectsService = projectsService;
        this.pdfDirectory = pdfDirectory;
    }

    public String generate(Date date) throws FileNotFoundException {
        cleanPdfDirectory();

        Optional<List<ExpensesRecord>> optionalExpensesRecordsList = expensesService.history(date);
        List<ExpensesRecord> expensesRecords = optionalExpensesRecordsList.orElseThrow(() -> new RuntimeException("expenses not found"));
        String pdfName = "ExpensesFacture-" + date + ".pdf";
        String fullPath = pdfDirectory + File.separator + pdfName;
        CodingErrorPdfInvoiceCreator codingErrorPdfInvoiceCreator = new CodingErrorPdfInvoiceCreator(fullPath);
//        CodingErrorPdfInvoiceCreator codingErrorPdfInvoiceCreator = new CodingErrorPdfInvoiceCreator(pdfName);
        codingErrorPdfInvoiceCreator.createDocument();

        HeaderDetails headerDetails = new HeaderDetails();
        headerDetails.setInvoiceTitle("Expenses Facture")
                .setInvoiceNoText("")
                .setInvoiceDateText("Expenses Date: ")
                .setInvoiceDate(String.valueOf(date))
                .build();
        codingErrorPdfInvoiceCreator.createHeader(headerDetails);

        Optional<Double> optionalV = expensesService.total(date);
        Double total = optionalV.orElseThrow(() -> new RuntimeException("Total not found"));

        AddressDetails addressDetails = new AddressDetails();
        addressDetails
                .setBillingInfoText("Expenses Information")
                .setBillingCompanyText("Date")
                .setBillingCompany(String.valueOf(date))
                .setBillingNameText("Total Expenses")
                .setBillingName(String.valueOf(total))
                .setShippingAddressText("")
                .setBillingEmailText("")
                .setBillingAddressText("")
                .setShippingInfoText("")
                .setShippingNameText("")
                .build();

        codingErrorPdfInvoiceCreator.createAddress(addressDetails);

        ProductTableHeader productTableHeader = new ProductTableHeader();
        codingErrorPdfInvoiceCreator.createTableHeader(productTableHeader);
        List<Product> productList = new ArrayList<>();

        for (ExpensesRecord expensesRecord : expensesRecords) {
            Optional<ProjectsRecord> projectsRecord = projectsService.getOne(expensesRecord.projectCode());
            double doubleValue = expensesRecord.amount();
            float floatValue = (float) doubleValue;
            productList.add(new Product(projectsRecord.get().name(), 1, floatValue));
        }

        productList = codingErrorPdfInvoiceCreator.modifyProductList(productList);
        codingErrorPdfInvoiceCreator.createProduct(productList);

        List<String> TncList = new ArrayList<>();
        String image = "backAPI/src/main/resources/logo.png";
        codingErrorPdfInvoiceCreator.createTnc(TncList, false, image);

        return pdfName;
    }

    private void cleanPdfDirectory() {
        File directory = new File(pdfDirectory);
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    file.delete();
                }
            }
        }
    }
}
