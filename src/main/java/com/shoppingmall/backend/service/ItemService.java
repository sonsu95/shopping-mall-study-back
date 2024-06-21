package com.shoppingmall.backend.service;

import com.shoppingmall.backend.constant.ItemSellStatus;
import com.shoppingmall.backend.dto.ItemDto;
import com.shoppingmall.backend.dto.ItemCreateDto;
import com.shoppingmall.backend.entity.Item;
import com.shoppingmall.backend.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
        return items.stream().map(this::toItemDto).collect(Collectors.toList());
    }

    private ItemDto toItemDto(Item item) {
        ItemDto itemDto = new ItemDto();
        itemDto.setId(item.getId());
        itemDto.setItemNm(item.getItemNm());
        itemDto.setPrice(item.getPrice());
        item.setStockCnt(item.getStockCnt());
        itemDto.setItemDetail(item.getItemDetail());
        itemDto.setItemSellStatus(item.getItemSellStatus());
        itemDto.setRegTime(item.getRegTime());
        itemDto.setUpdateTime(item.getUpdateTime());

        return itemDto;
    }

    public ItemDto createItem(ItemCreateDto itemCreateDto) {
        Item createdItem = toItemEntity(itemCreateDto);
        Item savedItem = itemRepository.save(createdItem);
        return toItemDto(savedItem);
    }

    private Item toItemEntity(ItemCreateDto itemCreateDto) {
        Item item = new Item();
        item.setCategoryId(itemCreateDto.getCategoryId());
        item.setItemNm(itemCreateDto.getItemNm());
        item.setPrice(itemCreateDto.getPrice());
        item.setStockCnt(itemCreateDto.getStockCnt());
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setItemDetail(itemCreateDto.getItemDetail());
        item.setRegTime(LocalDateTime.now());
        item.setUpdateTime(LocalDateTime.now());
        return item;
    }
}
