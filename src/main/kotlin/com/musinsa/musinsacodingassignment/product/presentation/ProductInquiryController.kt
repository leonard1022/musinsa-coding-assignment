package com.musinsa.musinsacodingassignment.product.presentation

import com.musinsa.musinsacodingassignment.product.service.PriceService
import com.musinsa.musinsacodingassignment.product.presentation.dto.response.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/product-prices")
class ProductInquiryController(
    private val priceService: PriceService
) {

    @GetMapping("/minimum-by-category")
    fun getMinimumPriceByCategory(): ResponseEntity<MinimumPriceByCategoryResponse> {
        return ResponseEntity.status(HttpStatus.OK)
            .body(priceService.getMinimumPriceByCategory().toMinimumPriceByCategoryResponse())
    }

    @GetMapping("/price-range-by-category/{category}")
    fun getMinimumPriceByBrand(
        @PathVariable category: String
    ): ResponseEntity<CategoryPriceRangeResponse> {
        return ResponseEntity.status(HttpStatus.OK)
            .body(priceService.getMinimumAndMaximumBrandPrice(category).toCategoryPriceRangeResponse())
    }

    @GetMapping("/all-category-price-by-brand")
    fun getAllCategoryPriceByBrand(): ResponseEntity<AllCategoryPriceByBrandResponse> {
        return ResponseEntity.status(HttpStatus.OK)
            .body(priceService.getAllCategoryPriceByBrand().toAllCategoryPriceByBrandResponse())
    }
}
