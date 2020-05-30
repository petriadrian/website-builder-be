package com.models.configApp;

import com.models.configApp.common.ConfigAppCommon;
import lombok.Data;

@Data
public class ConfigApp {
    String defaultLanguage;
    Style style;
    String googleAnalyticsTrackingId;
    Facebook facebook;
    ConfigAppCommon configAppCommon;
}
