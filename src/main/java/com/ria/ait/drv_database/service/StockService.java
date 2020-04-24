package com.ria.ait.drv_database.service;

import com.ria.ait.drv_database.model.Stock;
import com.ria.ait.drv_database.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    public Stock createStock(Stock stock) {
        return stockRepository.save(stock);
    }

    public Optional<Stock> findById(int id) {
        Optional<Stock> findStock = findStockById(id);
            return findStock;
    }

    private Optional<Stock> findStockById(int id) {
        return this.stockRepository.findById(id);
    }


    public boolean updateStock(Stock stock) {
        boolean updated = true;
        Optional<Stock> foundStock = findStockById(stock.getStockId());
        if (foundStock != null) {
            this.stockRepository.save(stock);
        } else {
            updated = false;
        }
        return updated;
    }

    public boolean deleteStockById(int id) {
        Optional<Stock> findStock = this.stockRepository.findById(id);
        if (findStock.isPresent()) {
            this.stockRepository.delete(findStock.get());
            return true;
        } else {
            return false;
        }
    }

    public List<Stock> findAll() {
        return stockRepository.findAll();
    }

    public Optional<Stock> findByItemId(int id) {
        return stockRepository.findByItem_ItemId(id);
    }

}
