package com.bitozen.fms.service.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class MetadataCreateDTO implements Serializable {

    private String id;
    private String es;
    private String tag;
    private String type;
    private String version;

    @JsonIgnore
    public MetadataCreateDTO getInstance() {
        return new MetadataCreateDTO(
                UUID.randomUUID().toString().substring(0, 8).toUpperCase(),
                "es",
                "tag",
                "type",
                "version"
        );
    }

}
