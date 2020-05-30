package com.models.configApp.common.sectionStructures;

import lombok.Data;
import com.models.section.article.Button;

@Data
public class SiteHeader {
    Button button;
    WelcomeMessage welcomeMessage;
}
