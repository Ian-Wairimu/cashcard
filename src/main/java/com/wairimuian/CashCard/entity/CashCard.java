package com.wairimuian.CashCard.entity;

import org.springframework.data.annotation.Id;

public record CashCard(@Id Long id, Double amount) {
}
