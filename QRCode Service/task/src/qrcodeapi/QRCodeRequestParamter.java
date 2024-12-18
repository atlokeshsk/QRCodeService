package qrcodeapi;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.bind.DefaultValue;

public class QRCodeRequestParamter {

    @NotNull(message = "Contents cannot be null or blank")
    @NotBlank(message = "Contents cannot be null or blank")
    String contents;

    @Range(min = 150, max = 350, message = "Image size must be between 150 and 350 pixels")
    Integer size = 250;

    @Pattern(regexp = "png|jpeg|gif", message = "Only png, jpeg and gif image types are supported")
    String type = "png";
    
    @Pattern(regexp = "L|M|Q|H", message = "Permitted error correction levels are L, M, Q, H")
    String correction = "L";

    public int getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getCorrection() {
        return correction;
    }

    public void setCorrection(String correction) {
        this.correction = correction;
    }
}
