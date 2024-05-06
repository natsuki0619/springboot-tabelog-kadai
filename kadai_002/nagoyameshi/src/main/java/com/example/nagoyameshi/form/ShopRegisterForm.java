package com.example.nagoyameshi.form;

import org.springframework.web.multipart.MultipartFile;

import com.example.nagoyameshi.entity.Category;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ShopRegisterForm {
    @NotBlank(message = "店舗名を入力してください。")
    private String name;

    private MultipartFile imageFile;

    @NotBlank(message = "営業開始時間を入力してください。")
    private String opening;

    @NotBlank(message = "営業終了時間を入力してください。")
    private String closed;

    @NotBlank(message = "定休日を入力してください。")
    private String regularHoliday;

    @NotNull(message = "最低料金を入力してください。")
    private Integer lowestPrice;

    @NotNull(message = "最大料金を入力してください。")
    private Integer highestPrice;

    @NotNull(message = "説明を入力してください。")
    private String capacity;

    @NotNull(message = "メニューを入力してください。")
    private String menu;

    @NotBlank(message = "郵便番号を入力してください。")
    private String postalCode;

    @NotBlank(message = "住所を入力してください。")
    private String address;

    @NotBlank(message = "電話番号を入力してください。")
    private String phoneNumber;

    @NotNull(message = "カテゴリを選択してください。")
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
