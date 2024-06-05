package com.task.Controller;

import com.task.DTO.Records.ProductsRecord;
import com.task.Model.CountResponse;
import com.task.Model.Products;
import com.task.Service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("unused")
@PreAuthorize("hasRole('ADMIN')")
@RestController
@RequestMapping("/api/v1/products")
public class ProductsController {
    private final ProductsService productsService;

    @Autowired
    public ProductsController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<Optional<List<ProductsRecord>>> getAll() {
        Optional<List<ProductsRecord>> productsRecordList = productsService.getAll();
        return productsRecordList.isPresent() ?
                new ResponseEntity<>(productsRecordList, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/getOne")
    public ResponseEntity<Optional<ProductsRecord>> getOne(@RequestParam String code) {
        Optional<ProductsRecord> productsRecord = productsService.getOne(code);
        return productsRecord.isPresent() ?
                new ResponseEntity<>(productsRecord, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create")
    public ResponseEntity<Optional<ProductsRecord>> create(@RequestBody Products product) {
        Optional<ProductsRecord> productsRecord = productsService.create(product);
        return productsRecord.isPresent() ?
                new ResponseEntity<>(productsRecord, HttpStatus.CREATED) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/update")
    public ResponseEntity<Optional<ProductsRecord>> update(@RequestBody Products product) {
        Optional<ProductsRecord> productsRecord = productsService.update(product);
        return productsRecord.isPresent() ?
                new ResponseEntity<>(productsRecord, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Optional<ProductsRecord>> delete(@RequestParam String code) {
        Optional<ProductsRecord> productsRecord = productsService.delete(code);
        return productsRecord.isPresent() ?
                new ResponseEntity<>(productsRecord, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/count")
    public ResponseEntity<Optional<CountResponse>> getCount() {
        Optional<CountResponse> count = productsService.count();
        return count.isPresent() ?
                new ResponseEntity<>(count, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
