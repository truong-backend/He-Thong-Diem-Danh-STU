package vn.diemdanh.hethong.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.diemdanh.hethong.entity.Khoa;
import vn.diemdanh.hethong.repository.KhoaRepository;

import java.util.Optional;

@Service
public class KhoaService {

    @Autowired
    private KhoaRepository khoaRepository;

    public Khoa getKhoaById(String maKhoa) {
        return khoaRepository.findById(maKhoa)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khoa"));
    }
}