package com.task.Service.Factures;

import com.codingerror.model.AddressDetails;
import com.codingerror.model.HeaderDetails;
import com.codingerror.model.Product;
import com.codingerror.model.ProductTableHeader;
import com.codingerror.service.CodingErrorPdfInvoiceCreator;
import com.task.DTO.Records.IncomesRecord;
import com.task.DTO.Records.ProductsRecord;
import com.task.DTO.Records.SalesRecord;
import com.task.Service.IncomesService;
import com.task.Service.ProductsService;
import com.task.Service.SalesService;
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
public class IncomesFacture {
    private final IncomesService incomesService;
    private final SalesService salesService;
    private final ProductsService productsService;
    private final String pdfDirectory;

    @Autowired
    public IncomesFacture(IncomesService incomesService, SalesService salesService, ProductsService productsService,
                          @Value("${pdf.directory}") String pdfDirectory) {
        this.incomesService = incomesService;
        this.salesService = salesService;
        this.productsService = productsService;
        this.pdfDirectory = pdfDirectory;
    }

    public String generate(Date date) throws FileNotFoundException {
        cleanPdfDirectory();

        Optional<List<IncomesRecord>> optionalIncomesRecordsList = incomesService.history(date);
        List<IncomesRecord> incomesRecords = optionalIncomesRecordsList.orElseThrow(() -> new RuntimeException("incomes not found"));
        String pdfName = "IncomeFacture-" + date + ".pdf";
        String fullPath = pdfDirectory + File.separator + pdfName;
        CodingErrorPdfInvoiceCreator codingErrorPdfInvoiceCreator = new CodingErrorPdfInvoiceCreator(fullPath);
        codingErrorPdfInvoiceCreator.createDocument();

        HeaderDetails headerDetails = new HeaderDetails();
        headerDetails.setInvoiceTitle("Income Facture")
                .setInvoiceNoText("")
                .setInvoiceDateText("Income Date: ")
                .setInvoiceDate(String.valueOf(date))
                .build();
        codingErrorPdfInvoiceCreator.createHeader(headerDetails);

        Optional<Double> optionalV = incomesService.total(date);
        Double total = optionalV.orElseThrow(() -> new RuntimeException("Total not found"));

        AddressDetails addressDetails = new AddressDetails();
        addressDetails
                .setBillingInfoText("Incomes Information")
                .setBillingCompanyText("Date")
                .setBillingCompany(String.valueOf(date))
                .setBillingNameText("Total Income")
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

        for (IncomesRecord incomesRecord : incomesRecords) {
            Optional<SalesRecord> sales = salesService.getOne(incomesRecord.saleCode());
            Optional<ProductsRecord> product = productsService.getOne(sales.get().productCode());
            double doubleValue = product.get().price();
            float floatValue = (float) doubleValue;
            productList.add(new Product(product.get().name(), 1, floatValue));
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
