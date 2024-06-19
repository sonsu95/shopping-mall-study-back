package com.shoppingmall.backend.service;

import com.shoppingmall.backend.dto.ItemDto;
import com.shoppingmall.backend.entity.Item;
import com.shoppingmall.backend.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {
    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<ItemDto> getAllItems() {
        List<Item> items = itemRepository.findAll();
        return items.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private ItemDto convertToDto(Item item) {
        ItemDto itemDto = new ItemDto();
        itemDto.setId(item.getId());
        itemDto.setItemNm(item.getItemNm());
        itemDto.setPrice(item.getPrice());
        itemDto.setStockNumber(item.getStockNumber());
        itemDto.setItemDetail(item.getItemDetail());
        itemDto.setItemSellStatus(item.getItemSellStatus());
        itemDto.setRegTime(item.getRegTime());
        itemDto.setUpdateTime(item.getUpdateTime());

        return itemDto;
    }

    public ItemDto createItem(ItemDto itemDto) {
        Item item = convertToEntity(itemDto);
        Item savedItem = itemRepository.save(item);
        return convertToDto(savedItem);
    }

    private Item convertToEntity(ItemDto itemDto) {
        Item item = new Item();
        item.setItemNm(itemDto.getItemNm());
        item.setItemDetail(itemDto.getItemDetail());
        item.setPrice(itemDto.getPrice());
        return item;
    }
}
