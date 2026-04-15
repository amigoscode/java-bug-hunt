package com.amigoscode.bughunt.easy.bug39;

import java.util.HashMap;
import java.util.Map;

public class FeatureFlags {

    private final Map<String, Boolean> flags = new HashMap<>();
    private final String environment;

    public FeatureFlags(String environment) {
        this.environment = environment;
    }

    public void enable(String flag) {
        flags.put(flag, true);
    }

    public void disable(String flag) {
        flags.put(flag, false);
    }

    public boolean isEnabled(String flag) {
        return flags.get(flag);
    }

    public boolean isEnabledOrDefault(String flag, boolean fallback) {
        Boolean current = flags.get(flag);
        if (current == null) {
            return fallback;
        }
        return current;
    }

    public int flagCount() {
        return flags.size();
    }

    public String environment() {
        return environment;
    }
}
