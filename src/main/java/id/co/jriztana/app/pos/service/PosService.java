package id.co.jriztana.app.pos.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

public interface PosService {

    List<List<String>> processCsv(List<List<String>> data);
}
