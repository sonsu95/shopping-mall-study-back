package com.shoppingmall.backend.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shoppingmall.backend.constant.ItemSellStatus;
import com.shoppingmall.backend.entity.Item;
import com.shoppingmall.backend.entity.QItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class ItemRepositoryTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    ItemRepository itemRepository;

    @BeforeEach
    void setUp() {
        itemRepository.deleteAll();
    }

    public void createItemList() {
        for(int i = 1; i <= 10; i++) {
            Item item = new Item();
            item.setItemNm("테스트 상품" + i);
            item.setPrice(10000 + i);
            item.setItemDetail("테스트 상품 상세 설명" + i);
            item.setItemSellStatus(ItemSellStatus.SELL);
            item.setStockNumber(100);;
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            Item savedItem = itemRepository.save(item);
        }
    }

    @Test
    @DisplayName("상품 저장 테스트")
    public void createItemTest() {
        Item item = new Item();
        item.setItemNm("테스트 상품");
        item.setPrice(10000);
        item.setItemDetail("테스트 상품 상세 설명");
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setStockNumber(100);
        item.setRegTime(LocalDateTime.now());
        item.setUpdateTime(LocalDateTime.now());
        Item savedItem = itemRepository.save(item);
        System.out.println(savedItem.toString());
    }

    @Test
    @DisplayName("상품명 조회 테스트")
    public void findByItemNmTest() {
        this.createItemList();
        List<Item> itemList = itemRepository.findByItemNm("테스트 상품1"); // 생성된 상품명과 일치하도록 변경
        assertFalse(itemList.isEmpty(), "Item list should not be empty"); // 결과가 비어있지 않은지 확인
        assertEquals(1, itemList.size(), "There should be exactly one item with the name '테스트 상품1'");
        Item item = itemList.getFirst();
        assertEquals("테스트 상품1", item.getItemNm(), "Item name should be '테스트 상품1'");
        assertEquals(10001, item.getPrice(), "Item price should be 10001");
        System.out.println(item.toString());
    }

    @Test
    @DisplayName("상품명, 상품상세설명 or 테스트")
    public void findByItemNmOrItemDetailTest() {
        this.createItemList();
        List<Item> itemList = itemRepository.findByItemNmOrItemDetail("테스트 상품1", "테스트 상품 상세 설명5");
        assertFalse(itemList.isEmpty(), "Item list should not be empty");
        assertEquals(2, itemList.size(), "There should be exactly two item with the '테스트 상품1' and '테스트 상품5'");


        for (Item item : itemList) {
            System.out.println(item.toString());
        }

        // 추가 검증: 각각의 아이템 속성을 확인
        Item item1 = itemList.stream().filter(i -> "테스트 상품1".equals(i.getItemNm())).findFirst().orElse(null);
        assertNotNull(item1, "Item with name '테스트 상품1' should exist");
        assertEquals("테스트 상품1", item1.getItemNm(), "Item name should be '테스트 상품1'");
        assertEquals(10001, item1.getPrice(), "Item price should be 10001");

        Item item2 = itemList.stream().filter(i -> "테스트 상품 상세 설명5".equals(i.getItemDetail())).findFirst().orElse(null);
        assertNotNull(item2, "Item with detail '테스트 상품 상세 설명5' should exist");
        assertEquals("테스트 상품5", item2.getItemNm(), "Item name should be '테스트 상품5'");
        assertEquals(10005, item2.getPrice(), "Item price should be 10005");
    }

    @Test
    @DisplayName("가격 LessThan 테스트")
    public void findByPriceLessThanTest() {
        this.createItemList();
        List<Item> itemList = itemRepository.findByPriceLessThan(10005);
        for (Item item : itemList) {
            System.out.println(item.toString());
        }
        assertFalse(itemList.isEmpty(), "Item list should not be empty");
        assertEquals(4, itemList.size(), "There should be exactly four items with price less than 10005");
    }

    @Test
    @DisplayName("가격 내림차순 조회 테스트")
    public void findByPriceLessThanOrderByPriceDesc() {
        this.createItemList();
        List<Item> itemList = itemRepository.findByPriceLessThanOrderByPriceDesc(10005);
        for (Item item : itemList) {
            System.out.println(item.toString());
        }

        assertFalse(itemList.isEmpty(), "Item list should not be empty");
        assertEquals(4, itemList.size(), "There should be exactly four items with price less than 10005");

        // 품목이 가격별로 내림차순으로 정렬되었는지 확인
        for (int i = 0; i < itemList.size() - 1; i++) {
            assertTrue(itemList.get(i).getPrice() >= itemList.get(i + 1).getPrice(), "Items should be sorted by price in descending order");
        }
    }

    @Test
    @DisplayName("@Query를 이용한 상품 조회 테스트")
    public void findByItemDetailTest() {
        this.createItemList();
        List<Item> itemList = itemRepository.findByItemDetail("테스트 상품 상세 설명1");

        assertFalse(itemList.isEmpty(), "Item list should not be empty");
        assertEquals(2, itemList.size(), "There should be exactly two items in item list");

        for (Item item : itemList) {
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("Native Query를 이용한 상품 조회 테스트")
    public void findByItemDetailByNative() {
        this.createItemList();
        List<Item> itemList = itemRepository.findByItemDetailByNative("테스트 상품 상세 설명");
        for (Item item : itemList) {
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("Querydsl 조회테스트1")
    public void queryDslTest() {
        this.createItemList();
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QItem qItem = QItem.item;
        JPAQuery<Item> query = queryFactory.selectFrom(qItem)
                .where(qItem.itemSellStatus.eq(ItemSellStatus.SELL))
                .where(qItem.itemDetail.like("%" + "테스트 상품 상세 설명" + "%"))
                .orderBy(qItem.price.desc());

        List<Item> itemList = query.fetch();

        for (Item item : itemList) {
            System.out.println(item.toString());
        }
    }

    public void createItemList2() {
        for(int i=1; i<=5; i++) {
            Item item = new Item();
            item.setItemNm("테스트 상품" + i);
            item.setPrice(10000 + i);
            item.setItemSellStatus(ItemSellStatus.SELL);
            item.setItemDetail("테스트 상품 상세 설명" + i);
            item.setStockNumber(100);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            itemRepository.save(item);
        }
    }

    @Test
    @DisplayName("상품 Querydsl 조회 테스트 2")
    public void queryDslTest2() {
        this.createItemList2();

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QItem item = QItem.item;
        String itemDetail = "테스트 상품 상세 설명";
        int price = 10003;

        booleanBuilder
                .and(item.itemDetail.like("%" + itemDetail + "%"))
                .and(item.price.gt(price))
                .and(item.itemSellStatus.eq(ItemSellStatus.SELL));

        // 쿼리 실행 및 결과 검증
        List<Item> results = (List<Item>) itemRepository.findAll(booleanBuilder);
        assertThat(results).isNotEmpty();
        assertThat(results.size()).isEqualTo(2); // 가격 조건을 충족하는 상품은 "테스트 상품4", "테스트 상품5"
        for (Item result : results) {
            assertThat(result.getItemDetail()).contains(itemDetail);
            assertThat(result.getPrice()).isGreaterThan(price);
            assertThat(result.getItemSellStatus()).isEqualTo(ItemSellStatus.SELL);
        }
    }
}