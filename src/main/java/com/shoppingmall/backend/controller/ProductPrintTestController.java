package com.shoppingmall.backend.controller;

import com.shoppingmall.backend.dto.ItemDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;

@Controller
@ResponseBody
public class ProductPrintTestController {
    @GetMapping("/product/test")
    public ItemDto printTest() {
        ItemDto itemDto = new ItemDto();
        itemDto.setItemDetail("상품 상세 설명");
        itemDto.setItemNm("테스트 상품1");
        itemDto.setPrice(10000);
        itemDto.setSellStatCd("판매중");
        itemDto.setRegTime(LocalDateTime.now());
        return itemDto;
    }
}
