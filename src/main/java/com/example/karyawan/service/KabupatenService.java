package com.example.karyawan.service;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class KabupatenService {
    private static final Map<String, List<String>> dataKabupaten = new HashMap<>();

    static {
        // Data dummy
        dataKabupaten.put("Jawa Barat", Arrays.asList("Bandung", "Bogor", "Bekasi"));
        dataKabupaten.put("Jawa Timur", Arrays.asList("Surabaya", "Malang", "Kediri"));
    }

    public List<String> findKabupatenByProvinsi(String provinsi) {
        return dataKabupaten.getOrDefault(provinsi, Collections.emptyList());
    }
}
