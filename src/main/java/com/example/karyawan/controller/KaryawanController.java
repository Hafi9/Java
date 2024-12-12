package com.example.karyawan.controller;

import com.example.karyawan.model.Karyawan;
import com.example.karyawan.repository.KaryawanRepository;
import com.example.karyawan.service.KabupatenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/karyawan")
public class KaryawanController {

    @Autowired
    private KaryawanRepository repository;

    @Autowired
    private KabupatenService kabupatenService; // Service untuk menangani data Kabupaten

    // Menampilkan daftar karyawan
    @GetMapping
    public String listKaryawan(Model model) {
        List<Karyawan> karyawanList = repository.findAll();
        model.addAttribute("karyawanList", karyawanList);
        return "karyawan/list";
    }

    // Form tambah karyawan baru
    @GetMapping("/new")
    public String newKaryawanForm(Model model) {
        model.addAttribute("karyawan", new Karyawan());
        return "karyawan/form";
    }

    // Menyimpan data karyawan (untuk menyimpan karyawan baru)
    @PostMapping
    public String saveKaryawan(@ModelAttribute Karyawan karyawan) {
        repository.save(karyawan);
        return "redirect:/karyawan";
    }

    // Form edit karyawan
    @GetMapping("/edit/{id}")
    public String editKaryawanForm(@PathVariable Long id, Model model) {
        Karyawan karyawan = repository.findById(id).orElse(null);
        if (karyawan == null) {
            return "redirect:/karyawan";
        }
        model.addAttribute("karyawan", karyawan);
        return "karyawan/edit";
    }

    // Update data karyawan
    @PostMapping("/update/{id}")
    public String updateKaryawan(@PathVariable Long id, @ModelAttribute Karyawan karyawan) {
        Karyawan existingKaryawan = repository.findById(id).orElse(null);
        if (existingKaryawan != null) {
            existingKaryawan.setNik(karyawan.getNik());
            existingKaryawan.setNama(karyawan.getNama());
            existingKaryawan.setNoHp(karyawan.getNoHp());
            existingKaryawan.setAlamat(karyawan.getAlamat());
            existingKaryawan.setProvinsi(karyawan.getProvinsi());
            existingKaryawan.setKabupaten(karyawan.getKabupaten());
            repository.save(existingKaryawan);
        }
        return "redirect:/karyawan";
    }

    // Menampilkan detail karyawan (View)
    @GetMapping("/view/{id}")
    public String viewKaryawan(@PathVariable Long id, Model model) {
        Karyawan karyawan = repository.findById(id).orElse(null);
        if (karyawan == null) {
            return "redirect:/karyawan";
        }
        model.addAttribute("karyawan", karyawan);
        return "karyawan/view";
    }

    // Endpoint REST untuk mendapatkan kabupaten berdasarkan provinsi
    @GetMapping("/api/kabupaten")
    @ResponseBody
    public List<String> getKabupatenByProvinsi(@RequestParam String provinsi) {
        return kabupatenService.findKabupatenByProvinsi(provinsi);
    }
}
