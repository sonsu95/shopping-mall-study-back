package com.shoppingmall.backend.repository;

import com.shoppingmall.backend.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
