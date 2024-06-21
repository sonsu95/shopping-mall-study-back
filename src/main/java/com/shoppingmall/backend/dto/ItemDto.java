package com.shoppingmall.backend.dto;

import com.shoppingmall.backend.constant.ItemSellStatus;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ItemDto {
    private Long id; // 상품 코드

    @NotBlank(message = "상품명은 필수 입력 값입니다.")
    private String itemNm; // 상품명

    @NotNull(message = "가격은 필수 입력 값입니다.")
    @PositiveOrZero(message = "가격은 0 이상이어야 합니다.")
    private Integer price; // 가격

    @NotNull(message = "재고수량은 필수 입력 값입니다.")
    @PositiveOrZero(message = "재고수량은 0 이상이어야 합니다.")
    private Integer stockCnt; // 재고수량

    @NotBlank(message = "상품 상세 설명은 필수 입력 값입니다.")
    private String itemDetail; // 상품 상세 설명

    @NotNull(message = "상품 판매 상태는 필수 입력 값입니다.")
    private ItemSellStatus itemSellStatus; // 상품 판매 상태

    private LocalDateTime regTime; // 등록 시간
    private LocalDateTime updateTime; // 수정 시간
}
