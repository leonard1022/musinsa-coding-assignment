package com.musinsa.musinsacodingassignment.brand.presentation

import com.musinsa.musinsacodingassignment.brand.domain.Brand
import com.musinsa.musinsacodingassignment.brand.presentation.dto.request.CreateBrandRequest
import com.musinsa.musinsacodingassignment.brand.presentation.dto.request.UpdateBrandRequest
import com.musinsa.musinsacodingassignment.brand.presentation.dto.response.CreateBrandResponse
import com.musinsa.musinsacodingassignment.brand.presentation.dto.response.UpdateBrandResponse
import com.musinsa.musinsacodingassignment.brand.presentation.dto.response.toCreateBrandResponse
import com.musinsa.musinsacodingassignment.brand.presentation.dto.response.toUpdateBrandResponse
import com.musinsa.musinsacodingassignment.brand.service.BrandService
import com.musinsa.musinsacodingassignment.common.presentation.V1Controller
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class BrandController(
    private val brandService: BrandService
) : V1Controller() {

    @PostMapping("/brands")
    fun createBrand(
        @RequestBody request: CreateBrandRequest
    ): ResponseEntity<CreateBrandResponse> {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(brandService.createBrand(request).toCreateBrandResponse())
    }

    @GetMapping("/brands")
    fun getBrands(): ResponseEntity<List<Brand>> {
        return ResponseEntity.status(HttpStatus.OK)
            .body(brandService.getBrands())
    }

    @PatchMapping("/brands/{id}")
    fun updateBrand(
        @PathVariable id: Long,
        @RequestBody request: UpdateBrandRequest
    ): ResponseEntity<UpdateBrandResponse> {
        return ResponseEntity.status(HttpStatus.OK)
            .body(brandService.updateBrand(id, request).toUpdateBrandResponse())
    }

    @DeleteMapping("/brands/{id}")
    fun deleteBrand(
        @PathVariable id: Long
    ): ResponseEntity<Unit> {
        brandService.deleteBrand(id)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }
}
