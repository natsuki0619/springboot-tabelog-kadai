package com.example.nagoyameshi.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.nagoyameshi.entity.DownloadHelper;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.entity.Usercsv;
import com.example.nagoyameshi.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.csv.CsvGenerator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

@Controller
public class CsvuserController {

    private final UserRepository userRepository;

    public CsvuserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 会員情報をCSVで出力する
    @Autowired
    DownloadHelper downloadHelper;

    /**
     * CsvMapperで、csvを作成する。
     * @return csv(String)
     * @throws JsonProcessingException
     */
    public String getCsvText() throws JsonProcessingException {
        CsvMapper mapper = new CsvMapper();

        //文字列にダブルクオートをつける
        mapper.configure(CsvGenerator.Feature.ALWAYS_QUOTE_STRINGS, true);
        //ヘッダをつける
        CsvSchema schema = mapper.schemaFor(Usercsv.class).withHeader();
        //DBからデータを取得する。

        List<User> userList = userRepository.findAll();

        List<Usercsv> usercsvList = userList.stream().map(
                e -> new Usercsv(e.getId(), e.getName(), e.getFurigana(), e.getPostalCode(), e.getAddress(),
                        e.getPhoneNumber(), e.getEmail(), e.getPassword(), e.getRole(), e.getEnabled()))
                .collect(Collectors.toList());

        return mapper.writer(schema).writeValueAsString(usercsvList);
    }

    /**
     * csvをダウンロードする。
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/download/user/csv", method = RequestMethod.POST)
    public ResponseEntity<byte[]> download() throws IOException {
        HttpHeaders headers = new HttpHeaders();
        String csvText = getCsvText();
        byte[] csvBytes = csvText.getBytes("MS932");
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(csvBytes);
    }
}