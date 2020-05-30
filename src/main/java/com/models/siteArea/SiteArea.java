package com.models.siteArea;

import lombok.Data;

import java.util.List;
import com.models.section.Section;


@Data
public class SiteArea {
    AreaConfig areaConfig;
    Metadata metadata;
    List<? extends Section> sections;
}
