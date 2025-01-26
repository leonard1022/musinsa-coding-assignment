package com.musinsa.musinsacodingassignment.brand.presentation

import com.musinsa.musinsacodingassignment.brand.presentation.dto.request.CreateBrandRequest
import com.musinsa.musinsacodingassignment.brand.presentation.dto.request.UpdateBrandRequest
import com.musinsa.musinsacodingassignment.brand.presentation.dto.request.toVO
import com.musinsa.musinsacodingassignment.brand.presentation.dto.response.*
import com.musinsa.musinsacodingassignment.brand.service.BrandService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/brands")
class BrandController(
    private val brandService: BrandService
) {

    @GetMapping
    fun getBrands(): ResponseEntity<GetBrandListResponse> {
        return ResponseEntity.status(HttpStatus.OK)
            .body(brandService.getBrands().toGetBrandListResponse())
    }

    @PostMapping
    fun createBrand(
        @RequestBody request: CreateBrandRequest
    ): ResponseEntity<CreateBrandResponse> {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(brandService.createBrand(request.toVO()).toCreateBrandResponse())
    }

    @PatchMapping("/{id}")
    fun updateBrand(
        @PathVariable id: Long,
        @RequestBody request: UpdateBrandRequest
    ): ResponseEntity<UpdateBrandResponse> {
        return ResponseEntity.status(HttpStatus.OK)
            .body(brandService.updateBrand(request.toVO(id)).toUpdateBrandResponse())
    }

    @DeleteMapping("/{id}")
    fun deleteBrand(
        @PathVariable id: Long
    ): ResponseEntity<Unit> {
        brandService.deleteBrand(id)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }
}
