package com.musinsa.musinsacodingassignment.product.presentation

import com.musinsa.musinsacodingassignment.product.presentation.dto.request.CreateProductRequest
import com.musinsa.musinsacodingassignment.product.presentation.dto.request.UpdateProductRequest
import com.musinsa.musinsacodingassignment.product.presentation.dto.request.toCreateProductVO
import com.musinsa.musinsacodingassignment.product.presentation.dto.request.toUpdateProductVO
import com.musinsa.musinsacodingassignment.product.presentation.dto.response.*
import com.musinsa.musinsacodingassignment.product.service.ProductInquiryService
import com.musinsa.musinsacodingassignment.product.service.ProductService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/products")
class ProductController(
    private val productService: ProductService,
    private val productInquiryService: ProductInquiryService
) {

    @GetMapping
    fun getProducts(): ResponseEntity<GetProductListResponse> {
        return ResponseEntity.status(HttpStatus.OK)
            .body(productService.getProducts().toGetProductListResponse())
    }

    @PostMapping
    fun createProduct(
        @Valid @RequestBody request: CreateProductRequest
    ): ResponseEntity<CreateProductResponse> {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(productService.createProduct(request.toCreateProductVO()).toCreateProductResponse())
    }

    @PatchMapping("/{id}")
    fun updateProduct(
        @PathVariable id: Long,
        @RequestBody request: UpdateProductRequest
    ): ResponseEntity<UpdateProductResponse> {
        return ResponseEntity.status(HttpStatus.OK)
            .body(productService.updateProduct(request.toUpdateProductVO(id)).toUpdateProductResponse())
    }

    @DeleteMapping("/{id}")
    fun deleteProduct(
        @PathVariable id: Long
    ): ResponseEntity<Void> {
        productService.deleteProduct(id)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }

    @GetMapping("/lowest-by-category")
    fun getLowestPricesByCategory(): ResponseEntity<LowestPricesByCategoryResponse> {
        return ResponseEntity.status(HttpStatus.OK)
            .body(productInquiryService.getLowestPricesByCategory().toMLowestPricesByCategoryResponse())
    }

    @GetMapping("/lowest-single-brand")
    fun getLowestSingleBrand(): ResponseEntity<LowestSingleBrandResponse> {
        return ResponseEntity.status(HttpStatus.OK)
            .body(productInquiryService.getLowestSingleBrand().toLowestSingleBrandResponse())
    }

    @GetMapping("/{categoryName}/min-max")
    fun getMinMaxPriceByCategory(
        @PathVariable categoryName: String
    ): ResponseEntity<MinMaxPriceResponse> {
        return ResponseEntity.status(HttpStatus.OK)
            .body(productInquiryService.getMinMaxPriceByCategory(categoryName).toMinMaxPriceResponse())
    }
}
