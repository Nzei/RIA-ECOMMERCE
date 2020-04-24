package com.ria.ait.drv_database.service;

import com.ria.ait.drv_database.model.Item;
import com.ria.ait.drv_database.model.ItemJsonResponse;
import com.ria.ait.drv_database.model.ItemObject;
import com.ria.ait.drv_database.repository.ItemRepository;
import com.ria.ait.drv_database.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private StockRepository stockRepository;

    public List<Item> fetchAllItems(){
        return itemRepository.findAll();
    }

    public ItemJsonResponse fetchAllItemNamesOrPartialName(String keyWord, Pageable pageable){
        if(keyWord.equals("*")) {
            Page<Item> items = itemRepository.findAll(pageable);
            List<ItemObject> itemObjects = new ArrayList<>();
            for(Item item : items.getContent()){
                itemObjects.add(convertItemToItemObject(item));
            }
            return new ItemJsonResponse(items, itemObjects);
        }
        else {
            Page<Item> items = pageable.getSort().toString().contains("description")?
                    itemRepository.findAllByDescriptionOrDescriptionContains(keyWord,keyWord,pageable):
                    itemRepository.findAllByItemNameOrItemNameContains(keyWord,keyWord,pageable);
            List<ItemObject> itemObjects = new ArrayList<>();
            for(Item item : items.getContent()){
                itemObjects.add(convertItemToItemObject(item));
            }
            return new ItemJsonResponse(items, itemObjects);
        }
    }

    ItemObject convertItemToItemObject(Item item){
        ItemObject itemObject = new ItemObject();
        itemObject.setItemId(item.getItemId());
        itemObject.setItemDescription(item.getDescription());
        itemObject.setItemName(item.getItemName());
        itemObject.setItemPrice(item.getItemPrice());
        return itemObject;
    }

    public Item createItem(Item item){
        return itemRepository.save(item);
    }

    public Optional<Item> findById(int id) {
        Optional<Item> findItem = findItemById(id);
        return findItem;
    }

    private Optional<Item> findItemById(int id) {
        return this.itemRepository.findById(id);
    }

    public boolean updateItem(Item item) {
        boolean updated = true;
        Optional<Item> foundItem = findItemById(item.getItemId());
        if (foundItem != null) {
            this.itemRepository.save(item);
        } else {
            updated = false;
        }
        return updated;
    }

    public boolean deleteItemById(int itemId) {
        Optional<Item> foundItem = this.itemRepository.findById(itemId);
        if (foundItem.isPresent()) {
            this.stockRepository.deleteById(stockRepository.findByItem_ItemId(itemId).get().getStockId());
            this.itemRepository.delete(foundItem.get());
            return true;
        } else {
            return false;
        }
    }


}
