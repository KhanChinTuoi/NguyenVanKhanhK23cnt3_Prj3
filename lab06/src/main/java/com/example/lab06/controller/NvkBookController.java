package com.example.lab06.controller;

import com.example.lab06.entity.NvkAuthor;
import com.example.lab06.entity.NvkBook;
import com.example.lab06.service.NvkAuthorService;
import com.example.lab06.service.NvkBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/nvkbooks")
public class NvkBookController {

    @Autowired
    private NvkBookService nvkBookService;

    @Autowired
    private NvkAuthorService nvkAuthorService;

    // Danh sách sách
    @GetMapping("/")
    public String getNvkBooks(Model model) {
        model.addAttribute("nvkBooks", nvkBookService.getAllNvkBooks());
        return "nvkbooks/nvk-book-list";
    }

    // Form thêm sách
    @GetMapping("/new")
    public String showCreateFormNvkBook(Model model) {
        model.addAttribute("nvkBook", new NvkBook());
        model.addAttribute("nvkAuthors", nvkAuthorService.getAllNvkAuthors());
        return "nvkbooks/nvk-book-form";
    }

    // Lưu sách mới
    @PostMapping("/new")
    public String createNvkBook(
            @ModelAttribute NvkBook nvkBook,
            @RequestParam(required = false) List<Long> nvkAuthorIds,
            @RequestParam("imageBook") MultipartFile imageFile
    ) {
        // Xử lý tác giả
        List<NvkAuthor> authors = new ArrayList<>();
        if (nvkAuthorIds != null) {
            authors = nvkAuthorService.findNvkAuthorById(nvkAuthorIds);
        }
        nvkBook.setNvkAuthors(authors);

        // Xử lý ảnh
        if (!imageFile.isEmpty()) {
            try {
                String uploadDir = "uploads/";
                java.io.File folder = new java.io.File(uploadDir);
                if (!folder.exists()) folder.mkdirs();
                String fileName = imageFile.getOriginalFilename();
                imageFile.transferTo(new java.io.File(uploadDir + fileName));
                nvkBook.setNvkImgUrl("/" + uploadDir + fileName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        nvkBookService.saveNvkBook(nvkBook);
        return "redirect:/nvkbooks";
    }

    // Form sửa sách
    @GetMapping("/edit/{id}")
    public String showEditFormNvkBook(@PathVariable("id") Long nvkId, Model model) {
        NvkBook book = nvkBookService.getNvkBookById(nvkId);
        if (book == null) {
            return "redirect:/nvkbooks";
        }
        model.addAttribute("nvkBook", book);
        model.addAttribute("nvkAuthors", nvkAuthorService.getAllNvkAuthors());
        return "nvkbooks/nvk-book-form";
    }

    // Lưu sửa sách
    @PostMapping("/edit/{id}")
    public String updateNvkBook(
            @PathVariable("id") Long nvkId,
            @ModelAttribute NvkBook nvkBook,
            @RequestParam(required = false) List<Long> nvkAuthorIds,
            @RequestParam("imageBook") MultipartFile imageFile
    ) {
        NvkBook existingBook = nvkBookService.getNvkBookById(nvkId);
        if (existingBook == null) {
            return "redirect:/nvkbooks";
        }

        // Cập nhật thông tin
        existingBook.setNvkCode(nvkBook.getNvkCode());
        existingBook.setNvkName(nvkBook.getNvkName());
        existingBook.setNvkDescription(nvkBook.getNvkDescription());
        existingBook.setNvkPrice(nvkBook.getNvkPrice());
        existingBook.setNvkQuantity(nvkBook.getNvkQuantity());
        existingBook.setNvkActive(nvkBook.getNvkActive());

        // Cập nhật tác giả
        List<NvkAuthor> authors = new ArrayList<>();
        if (nvkAuthorIds != null) {
            authors = nvkAuthorService.findNvkAuthorById(nvkAuthorIds);
        }
        existingBook.setNvkAuthors(authors);

        // Cập nhật ảnh nếu có
        if (!imageFile.isEmpty()) {
            try {
                String uploadDir = "uploads/";
                java.io.File folder = new java.io.File(uploadDir);
                if (!folder.exists()) folder.mkdirs();
                String fileName = imageFile.getOriginalFilename();
                imageFile.transferTo(new java.io.File(uploadDir + fileName));
                existingBook.setNvkImgUrl("/" + uploadDir + fileName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        nvkBookService.saveNvkBook(existingBook);
        return "redirect:/nvkbooks";
    }

    // Xóa sách
    @GetMapping("/delete/{id}")
    public String deleteNvkBook(@PathVariable("id") Long nvkId) {
        nvkBookService.deleteNvkBook(nvkId);
        return "redirect:/nvkbooks";
    }
}