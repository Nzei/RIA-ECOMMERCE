package com.ria.ait.drv_database.controller;

import com.ria.ait.drv_database.exception.ItemNotFoundException;
import com.ria.ait.drv_database.model.Item;
import com.ria.ait.drv_database.model.ItemJsonResponse;
import com.ria.ait.drv_database.service.ItemService;
import com.ria.ait.drv_database.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private StockService stockService;

    @GetMapping("/get-all-items")
    public List<Item> getAllItems() {
        return this.itemService.fetchAllItems();
    }

    @GetMapping("/keyword-search/{keyword}")
    public ItemJsonResponse findAllKeywordOrPartial(@PathVariable("keyword") String keyWord, Pageable pageable) {
        return itemService.fetchAllItemNamesOrPartialName(keyWord, pageable);
    }

    @GetMapping("/get-item-by-id/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable int id) {
        Optional<Item> findItem = this.itemService.findById(id);
        if (findItem.isPresent()) {
            return new ResponseEntity<>(findItem.get(), HttpStatus.OK);
        } else {
            throw new ItemNotFoundException("Unable to retrieve item with id : " + id);
        }
    }

    @DeleteMapping("/delete-item-by-id/{id}")
    public ResponseEntity<Item> deleteItemById(@PathVariable int id) {
        try {
            if (this.itemService.deleteItemById(id)) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            } else {
                throw new ItemNotFoundException("Item with specified id does not exist : " + id);
            }
        } catch (EmptyResultDataAccessException e) {
            throw new ItemNotFoundException("Unable to delete item with id : " + id);
        }
    }

    @PostMapping(path = "/add-item")
    public ResponseEntity<Item> createNewItem(@Valid @RequestBody Item item) {
        Item createdItem = this.itemService.createItem(item);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("{id}").buildAndExpand(createdItem.getItemId()).toUri();
        return ResponseEntity.created(location).body(createdItem);
    }


    @PutMapping("/update-item")
    public ResponseEntity updateItem(@Valid @RequestBody Item item) {
        if (item.getItemId() != null) {
            Item updatedItem = this.itemService.updateItem(item) ? itemService.findById(item.getItemId()).get() : null;
            if(updatedItem == null) throw new ItemNotFoundException(String.format("Item with id %s not found",String.valueOf(item.getItemId())));
            return new ResponseEntity<>(updatedItem, HttpStatus.OK);
        } else {
            throw new ItemNotFoundException("No item id found in body.");
        }
    }

    /*
    @GetMapping("/get-stock-from-item-by-id/{id}")
    public Optional<Stock> getStockFromItem(@PathVariable int id) {
        Optional<Stock> findStock = this.stockService.findById(id);
        if (findStock.isPresent()) {
            return Optional.ofNullable(findStock.get());
        } else {
            throw new StockNotFoundException("Unable to retrieve stock with id : " + id);
        }
    }

     */


}
