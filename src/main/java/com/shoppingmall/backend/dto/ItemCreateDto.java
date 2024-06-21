package com.shoppingmall.backend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemCreateDto {

    @NotNull(message = "상품 분류 코드는 필수 입력 값입니다.")
    @Min(value = 1, message = "상품 분류 코드는 1 이상이어야 합니다.")
    private int CategoryId; // 상품 분류 코드

    @NotBlank(message = "가격은 필수 입력 값입니다.")
    private String itemNm; // 상품명

    @NotNull(message = "가격은 필수 입력 값입니다.")
    @PositiveOrZero(message = "가격은 0 이상이어야 합니다.")
    private int price; // 가격

    @NotNull(message = "재고수량은 필수 입력 값입니다.")
    @PositiveOrZero(message = "재고수량은 0 이상이어야 합니다.")
    private int stockCnt;

    private String itemDetail; // 상품 상세 설명
}
