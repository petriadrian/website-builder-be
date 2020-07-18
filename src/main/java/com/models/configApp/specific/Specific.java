package com.models.configApp.specific;

import lombok.Data;

@Data
public class Specific {
    private String defaultLanguage;
    private Style style;
    private String googleAnalyticsTrackingId;
    private Facebook facebook;
}
