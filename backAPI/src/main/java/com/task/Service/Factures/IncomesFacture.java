package com.task.Service.Factures;

import com.codingerror.model.AddressDetails;
import com.codingerror.model.HeaderDetails;
import com.codingerror.model.Product;
import com.codingerror.model.ProductTableHeader;
import com.codingerror.service.CodingErrorPdfInvoiceCreator;
import com.task.Model.Clients;
import com.task.Model.Incomes;
import com.task.Model.Products;
import com.task.Model.Sales;
import com.task.Repository.IncomesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class IncomesFacture {
    private final IncomesRepository incomesRepository;

    @Autowired
    public IncomesFacture(IncomesRepository incomesRepository) {
        this.incomesRepository = incomesRepository;
    }

    public void generate(String code) throws FileNotFoundException {
        Incomes incomes = incomesRepository.findByCode(code);
        Sales sale = incomes.getSale();
        Products product = sale.getProduct();
        Clients clients = sale.getClient();

        String date = formatDate(incomes.getDate());
        String pdfName = "IncomeFacture-" + date + ".pdf";
        CodingErrorPdfInvoiceCreator codingErrorPdfInvoiceCreator = new CodingErrorPdfInvoiceCreator(pdfName);
        codingErrorPdfInvoiceCreator.createDocument();

        HeaderDetails headerDetails = new HeaderDetails();
        headerDetails.setInvoiceTitle("Income Facture")
                .setInvoiceNo(incomes.getCode())
                .setInvoiceDate(date)
                .build();
        codingErrorPdfInvoiceCreator.createHeader(headerDetails);

        AddressDetails addressDetails = new AddressDetails();
        addressDetails
                .setBillingCompanyText("From")
                .setBillingCompany("Client")
                .setBillingNameText("Name")
                .setBillingName(clients.getName())
                .setBillingAddressText("Info")
                .setBillingAddress(clients.getName() + "\n" + clients.getSurname() + "\n" + clients.getNumber())
                .setBillingEmail(clients.getEmail())
                .setBillingNameText("Facture_ID")
                .setBillingName(incomes.getCode())
                .setBillingAddressText("Sale Date")
                .setBillingAddress(formatDate(incomes.getDate()))
                .build();
        codingErrorPdfInvoiceCreator.createAddress(addressDetails);

        ProductTableHeader productTableHeader = new ProductTableHeader();
        codingErrorPdfInvoiceCreator.createTableHeader(productTableHeader);
        List<Product> productList = new ArrayList<>();
        double doubleValue = product.getPrice();
        float floatValue = (float) doubleValue;
        productList.add(new Product(product.getName(), sale.getAmount(), floatValue));
        productList = codingErrorPdfInvoiceCreator.modifyProductList(productList);
        codingErrorPdfInvoiceCreator.createProduct(productList);

        List<String> TncList = new ArrayList<>();
        TncList.add("1. The Seller shall not be liable to the Buyer directly or indirectly for any loss or damage suffered by the Buyer.");
        TncList.add("2. The Seller warrants the product for one (1) year from the date of shipment");
        String image = "backAPI/src/main/resources/logo.png";
        codingErrorPdfInvoiceCreator.createTnc(TncList, false, image);
    }

    private String formatDate(java.util.Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date);
    }
}
