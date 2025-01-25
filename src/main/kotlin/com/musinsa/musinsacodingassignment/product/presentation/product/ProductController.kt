package com.musinsa.musinsacodingassignment.product.presentation.product

import com.musinsa.musinsacodingassignment.common.presentation.V1Controller
import com.musinsa.musinsacodingassignment.product.application.CreateProductService
import com.musinsa.musinsacodingassignment.product.application.UpdateProductService
import com.musinsa.musinsacodingassignment.product.presentation.product.dto.request.CreateProductRequest
import com.musinsa.musinsacodingassignment.product.presentation.product.dto.request.InquiryProductResponse
import com.musinsa.musinsacodingassignment.product.presentation.product.dto.request.UpdateProductRequest
import com.musinsa.musinsacodingassignment.product.presentation.product.dto.request.toInquiryProductResponse
import com.musinsa.musinsacodingassignment.product.presentation.product.dto.response.CreateProductResponse
import com.musinsa.musinsacodingassignment.product.presentation.product.dto.response.UpdateProductResponse
import com.musinsa.musinsacodingassignment.product.presentation.product.dto.response.toCreateProductResponse
import com.musinsa.musinsacodingassignment.product.presentation.product.dto.response.toUpdateProductResponse
import com.musinsa.musinsacodingassignment.product.service.ProductService
import org.apache.coyote.Response
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class ProductController(
    private val productService: ProductService,
    private val createProductService: CreateProductService,
    private val updateProductService: UpdateProductService
) : V1Controller() {
    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)
    }

    @PostMapping("/products")
    fun createProduct(
        @RequestBody request: CreateProductRequest
    ): ResponseEntity<CreateProductResponse> {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(createProductService.createProduct(request).toCreateProductResponse())
    }

    @GetMapping("/products")
    fun getProducts(): ResponseEntity<InquiryProductResponse> {
        return ResponseEntity.status(HttpStatus.OK)
            .body(productService.getProducts().toInquiryProductResponse())
    }

    @PatchMapping("/products/{id}")
    fun updateProduct(
        @PathVariable id: Long,
        @RequestBody request: UpdateProductRequest
    ): ResponseEntity<UpdateProductResponse> {
        return ResponseEntity.status(HttpStatus.OK)
            .body(updateProductService.updateProduct(id, request).toUpdateProductResponse())

    }

    @DeleteMapping("/products/{id}")
    fun deleteProduct(
        @PathVariable id: Long
    ): ResponseEntity<Response> {
        productService.deleteProduct(id)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }
}
