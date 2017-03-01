package ru.tickerparser.controller;

import org.bytedeco.javacpp.lept;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.tickerparser.util.Tesseract;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@RestController
@RequestMapping("/ocr")
public class OcrController {

    @PostMapping("/recognize")
    public String ocr(@RequestParam("file") MultipartFile image)
            throws IOException {
        save(image);
        //final lept.PIX pix = lept.pixReadMem(image.getBytes(), image.getSize());
        final lept.PIX pix = lept.pixRead(image.getOriginalFilename());
        final Tesseract tesseract = new Tesseract("rus");

        lept.PIX pixOut = tesseract.ocrWords(pix);
        lept.pixWrite("out.png", pixOut, lept.IFF_PNG);
        pix.deallocate();
        pixOut.deallocate();
        tesseract.release();
        return "OK";
    }

    @GetMapping(path = "/recognize", produces = MediaType.IMAGE_PNG_VALUE)
    public FileSystemResource ocr(HttpServletResponse response) throws IOException {
        //response.setContentType("image/png");
        return new FileSystemResource("out.png");
    }

    private File save(MultipartFile file) throws IOException {
        final File dest = new File(file.getOriginalFilename());
        dest.createNewFile();
        FileOutputStream fos = new FileOutputStream(dest);
        fos.write(file.getBytes());
        fos.close();
        return dest;
    }
}
