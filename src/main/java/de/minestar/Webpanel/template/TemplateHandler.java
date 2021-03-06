package de.minestar.Webpanel.template;

import java.util.concurrent.ConcurrentHashMap;

public class TemplateHandler {
    private static ConcurrentHashMap<String, Template> templateMap = new ConcurrentHashMap<String, Template>();

    public static boolean hasTemplate(String name) {
        return templateMap.containsKey(name);
    }

    protected static Template getTemplate(String name) {
        if (!hasTemplate(name)) {
            return Template.emptyTemplate();
        } else {
            return templateMap.get(name).clone();
        }
    }

    public static boolean removeTemplate(Template template) {
        return removeTemplate(template.getName());
    }

    public static boolean removeTemplate(String name) {
        return (templateMap.remove(name) != null);
    }

    public static boolean addTemplate(Template template) {
        if (hasTemplate(template.getName())) {
            return false;
        }
        templateMap.put(template.getName(), template);
        return true;
    }
}
