package com.models.section.type;

import com.models.Site;
import com.models.section.Section;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Document
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Common extends Section {
    @Id
    private String id;
    @DBRef
    private Site site;
    @Indexed(unique = true)
    private String key;
    private List<Section> sections;
    @CreatedDate
    private LocalDateTime createdDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Common common = (Common) o;
        return id.equals(common.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }
}
