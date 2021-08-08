package id.co.jriztana.app.pos.controller;

import id.co.jriztana.app.pos.service.FileService;
import id.co.jriztana.app.pos.service.PosService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/pos")
@AllArgsConstructor
@Slf4j
public class PosController {
    private final PosService  posServiceImpl;
    private final FileService fileServiceImpl;

    @PostMapping("/upload")
    public void upload(HttpServletResponse response,
            @RequestParam("file") MultipartFile file) throws IOException {
        response.setContentType("text/csv");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=file_process_result.csv";
        response.setHeader(headerKey, headerValue);

        List<List<String>> data = fileServiceImpl.readCsv(file);
        List<List<String>> processedData = posServiceImpl.processCsv(data);

        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
        for (List<String> row : processedData) {
            csvWriter.write(row, "a", "b", "c");
        }
        csvWriter.close();

    }
}
