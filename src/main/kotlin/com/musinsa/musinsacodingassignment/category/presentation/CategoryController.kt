package com.musinsa.musinsacodingassignment.category.presentation

import com.musinsa.musinsacodingassignment.category.presentation.dto.request.CreateCategoryRequest
import com.musinsa.musinsacodingassignment.category.presentation.dto.request.UpdateCategoryRequest
import com.musinsa.musinsacodingassignment.category.presentation.dto.request.toVO
import com.musinsa.musinsacodingassignment.category.presentation.dto.response.*
import com.musinsa.musinsacodingassignment.category.service.CategoryService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/categories")
class CategoryController(
    private val categoryService: CategoryService
) {

    @GetMapping
    fun getCategories(): ResponseEntity<GetCategoryListResponse> {
        return ResponseEntity.status(HttpStatus.OK)
            .body(categoryService.getCategories().toGetCategoryListResponse())
    }

    @PostMapping
    fun createCategory(
        @RequestBody request: CreateCategoryRequest
    ): ResponseEntity<CreateCategoryResponse> {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(categoryService.createCategory(request.toVO()).toCreateCategoryResponse())
    }

    @PatchMapping("/{id}")
    fun updateCategory(
        @PathVariable id: Long,
        @RequestBody request: UpdateCategoryRequest
    ): ResponseEntity<UpdateCategoryResponse> {
        return ResponseEntity.status(HttpStatus.OK)
            .body(categoryService.updateCategory(request.toVO(id)).toUpdateCategoryResponse())
    }
}
