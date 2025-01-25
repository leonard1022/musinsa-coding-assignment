package com.musinsa.musinsacodingassignment.product.presentation.price

import com.musinsa.musinsacodingassignment.common.presentation.V1Controller
import com.musinsa.musinsacodingassignment.product.application.PriceService
import com.musinsa.musinsacodingassignment.product.presentation.price.dto.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class PriceController(
    private val priceService: PriceService
) : V1Controller() {

    @GetMapping("/prices/minimum-by-category")
    fun getMinimumPriceByCategory(): ResponseEntity<MinimumPriceByCategoryResponse> {
        return ResponseEntity.status(HttpStatus.OK)
            .body(priceService.getMinimumPriceByCategory().toMinimumPriceByCategoryResponse())
    }

    @GetMapping("/prices/price-range-by-category/{category}")
    fun getMinimumPriceByBrand(
        @PathVariable category: String
    ): ResponseEntity<CategoryPriceRangeResponse> {
        return ResponseEntity.status(HttpStatus.OK)
            .body(priceService.getMinimumAndMaximumBrandPrice(category).toCategoryPriceRangeResponse())
    }

    @GetMapping("/prices/all-category-price-by-brand")
    fun getAllCategoryPriceByBrand(): ResponseEntity<AllCategoryPriceByBrandResponse> {
        return ResponseEntity.status(HttpStatus.OK)
            .body(priceService.getAllCategoryPriceByBrand().toAllCategoryPriceByBrandResponse())
    }
}
