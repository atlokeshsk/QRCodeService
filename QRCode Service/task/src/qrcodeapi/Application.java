package qrcodeapi;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import jakarta.validation.Valid;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.image.BufferedImage;
import java.util.Map;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

@RestController
@Validated
class QRService{

    @GetMapping("/api/health")
    public ResponseEntity<?> checkHealth(){
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/api/qrcode")
    public ResponseEntity<BufferedImage> qrcode(
             @Valid QRCodeRequestParamter requestParamter
            ){

        QRCodeWriter writer = new QRCodeWriter();
        Map<EncodeHintType, ?> hints = Map.of(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        switch (requestParamter.correction){
            case "L":
                hints = Map.of(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
                break;
            case "M":
                hints = Map.of(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
                break;
            case "Q":
                hints = Map.of(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.Q);
                break;
        }
        BufferedImage bufferedImage;
        try {
            BitMatrix bitMatrix = writer.encode(requestParamter.contents, BarcodeFormat.QR_CODE, requestParamter.size, requestParamter.size, hints);
             bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
        } catch (WriterException e) {
            throw new RuntimeException(e);
        }
        MediaType mediaType;
        switch (requestParamter.type){
            case "png":
                mediaType = MediaType.IMAGE_PNG;
                break;
            case "jpeg":
                mediaType = MediaType.IMAGE_JPEG;
                break;
            case "gif":
                mediaType = MediaType.IMAGE_GIF;
                break;
            default:
                mediaType = MediaType.ALL;
        }
        return ResponseEntity
                .ok()
                .contentType(mediaType)
                .body(bufferedImage);
    }
}

@Configuration
class AppConfiguration{
    @Bean
    public HttpMessageConverter<BufferedImage> bufferedImageHttpMessageConverter() {
        return new BufferedImageHttpMessageConverter();
    }
}
