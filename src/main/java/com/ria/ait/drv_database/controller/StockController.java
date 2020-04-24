package com.ria.ait.drv_database.controller;


import com.ria.ait.drv_database.exception.StockNotFoundException;
import com.ria.ait.drv_database.model.Stock;
import com.ria.ait.drv_database.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/stock")
public class StockController {

    @Autowired
    private StockService stockService;

    @GetMapping("/get-all-stock")
    public List<Stock> getAllItems() {
        return this.stockService.findAll();
    }

    @GetMapping("/get-stock-by-id/{id}")
    public ResponseEntity<Stock> getStockById(@PathVariable int id) {
        Optional<Stock> findStock = this.stockService.findByItemId(id);
        if (findStock.isPresent()) {
            return new ResponseEntity<>(findStock.get(),HttpStatus.OK);
        } else {
            throw new StockNotFoundException("Unable to retrieve stock with id : " + id);
        }
    }

    @GetMapping("/get-stock-by-item-id/{id}")
    public ResponseEntity<Stock> getStockByItemId(@PathVariable int id) {
        Optional<Stock> findStock = this.stockService.findByItemId(id);
        if (findStock.isPresent()) {
            return new ResponseEntity<>(findStock.get(),HttpStatus.OK);
        } else {
            throw new StockNotFoundException("Unable to retrieve stock with id : " + id);
        }
    }



    @DeleteMapping("/delete-stock-by-id/{id}")
    public ResponseEntity<Stock> deleteStockById(@PathVariable int id) {
        try {
            if (this.stockService.deleteStockById(id)) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            } else {
                throw new StockNotFoundException("Stock with specified id does not exist : " + id);
            }
        } catch (EmptyResultDataAccessException e) {
            throw new StockNotFoundException("Unable to delete stock with id : " + id);
        }
    }

    @PostMapping("/add-stock")
    public ResponseEntity createNewStock(@RequestBody Stock stock) {
        Stock createdStock = this.stockService.createStock(stock);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("{id}").buildAndExpand(stock.getStockId()).toUri();
        return ResponseEntity.created(location).body(createdStock);
    }

    @PutMapping("/update-stock")
    public ResponseEntity updateStock(@RequestBody Stock stock) {
        if (stock.getStockId() != null) {
            Stock updatedStock = fetchUpdatedStock(stock);
            if(updatedStock == null) throw new StockNotFoundException(String.format("Stock with id %s not found",String.valueOf(stock.getStockId())));
            return new ResponseEntity<>(updatedStock, HttpStatus.OK);
        }
        else if(stock.getItem().getItemId() != null){
            stock.setStockId(stockService.findByItemId(stock.getItem().getItemId()).get().getStockId());
            Stock updatedStock = fetchUpdatedStock(stock);
            if(updatedStock == null) throw new StockNotFoundException(String.format("Stock with id %s not found",String.valueOf(stock.getStockId())));
            return new ResponseEntity<>(updatedStock, HttpStatus.OK);
        }
        else {
            throw new StockNotFoundException("No stock id found in body.");
        }
    }

    private Stock fetchUpdatedStock(Stock stock){
        return this.stockService.updateStock(stock) ? stockService.findByItemId(stock.getItem().getItemId()).get() : null;
    }
}
