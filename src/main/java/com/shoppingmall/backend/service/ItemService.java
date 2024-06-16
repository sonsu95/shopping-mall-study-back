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
        return items.stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }

    private ItemDto convertEntityToDto(Item item) {
        ItemDto itemDto = new ItemDto();
        itemDto.setId(item.getId());
        itemDto.setItemNm(item.getItemNm());
        itemDto.setPrice(item.getPrice());
        itemDto.setStockNumber(item.getStockNumber());
        itemDto.setItemDetail(item.getItemDetail());
        itemDto.setItemSellStatus(item.getItemSellStatus().toString());
        itemDto.setRegTime(item.getRegTime());
        itemDto.setUpdateTime(item.getUpdateTime());

        return itemDto;
    }
}
