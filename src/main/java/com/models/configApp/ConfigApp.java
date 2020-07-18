package com.models.configApp;

import com.models.configApp.common.Common;
import com.models.configApp.specific.Specific;
import lombok.Data;

@Data
public class ConfigApp {
    private Specific specific;
    private Common common;
}
