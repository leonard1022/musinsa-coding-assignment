package com.musinsa.musinsacodingassignment.brand.presentation

import com.musinsa.musinsacodingassignment.category.domain.Category
import com.musinsa.musinsacodingassignment.category.presentation.dto.request.CreateCategoryRequest
import com.musinsa.musinsacodingassignment.category.presentation.dto.request.UpdateCategoryRequest
import com.musinsa.musinsacodingassignment.category.presentation.dto.response.CreateCategoryResponse
import com.musinsa.musinsacodingassignment.category.presentation.dto.response.UpdateCategoryResponse
import com.musinsa.musinsacodingassignment.category.presentation.dto.response.toCreateCategoryResponse
import com.musinsa.musinsacodingassignment.category.presentation.dto.response.toUpdateCategoryResponse
import com.musinsa.musinsacodingassignment.category.service.CategoryService
import com.musinsa.musinsacodingassignment.common.presentation.V1Controller
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class CategoryController(
    private val categoryService: CategoryService
) : V1Controller() {

    @PostMapping("/categories")
    fun createCategory(
        @RequestBody request: CreateCategoryRequest
    ): ResponseEntity<CreateCategoryResponse> {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(categoryService.createCategory(request).toCreateCategoryResponse())
    }

    @GetMapping("/categories")
    fun getBrands(): ResponseEntity<List<Category>> {
        return ResponseEntity.status(HttpStatus.OK)
            .body(categoryService.getCategories())
    }

    @PatchMapping("/categories/{id}")
    fun updateBrand(
        @PathVariable id: Long,
        @RequestBody request: UpdateCategoryRequest
    ): ResponseEntity<UpdateCategoryResponse> {
        return ResponseEntity.status(HttpStatus.OK)
            .body(categoryService.updateCategory(id, request).toUpdateCategoryResponse())
    }
}
