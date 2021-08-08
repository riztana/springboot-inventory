package id.co.jriztana.app.pos.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {
    List<List<String>> readCsv(MultipartFile file);
}
