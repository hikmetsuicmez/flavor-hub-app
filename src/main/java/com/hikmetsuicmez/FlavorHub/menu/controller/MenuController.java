package com.hikmetsuicmez.FlavorHub.menu.controller;

import com.hikmetsuicmez.FlavorHub.contants.ApiEndpoints;
import com.hikmetsuicmez.FlavorHub.docs.MenuApiDocs;
import com.hikmetsuicmez.FlavorHub.menu.dtos.MenuDTO;
import com.hikmetsuicmez.FlavorHub.menu.services.MenuService;
import com.hikmetsuicmez.FlavorHub.response.Response;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiEndpoints.Menu.BASE)
public class MenuController implements MenuApiDocs {

    private final MenuService menuService;

    @PostMapping(path = ApiEndpoints.Menu.CREATE,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response<MenuDTO>> createMenu(
            @ModelAttribute @Valid MenuDTO menuDTO,
            @RequestPart(value = "imageFile", required = true)MultipartFile imageFile
    ) {
        menuDTO.setImageFile(imageFile);
        return ResponseEntity.ok(menuService.createMenu(menuDTO));
    }

    @PutMapping(path = ApiEndpoints.Menu.UPDATE,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response<MenuDTO>> updateMenu(
            @ModelAttribute @Valid MenuDTO menuDTO,
            @RequestPart(value = "imageFile", required = false)MultipartFile imageFile
    ) {
        menuDTO.setImageFile(imageFile);
        return ResponseEntity.ok(menuService.updateMenu(menuDTO));
    }

    @GetMapping(ApiEndpoints.Menu.GET_BY_ID)
    public ResponseEntity<Response<MenuDTO>> getMenuById(@PathVariable Long id) {
        return ResponseEntity.ok(menuService.getMenuById(id));
    }

    @DeleteMapping(ApiEndpoints.Menu.DELETE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response<?>> deleteMenu(@PathVariable Long id) {
        return ResponseEntity.ok(menuService.deleteMenu(id));
    }

    @GetMapping(ApiEndpoints.Menu.GET_ALL)
    public ResponseEntity<Response<List<MenuDTO>>> getMenus(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String search) {
        return ResponseEntity.ok(menuService.getMenus(categoryId, search));
    }
}

