package org.training.campus.onlineshop;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;

public class PageGenerator {
	private static final String HTML_DIR = "templates/lab1";

	private final Configuration cfg;

	public static PageGenerator instance() {
		return Holder.INSTANCE;
	}

	private static class Holder {
		private static final PageGenerator INSTANCE = new PageGenerator();
	}

	public String getPage(String filename, Map<String, Object> data) {
		Writer stream = new StringWriter();
		try {
			Template template = cfg.getTemplate(HTML_DIR + File.separator + filename);
			template.process(data, stream);
		} catch (IOException | TemplateException e) {
			throw new RuntimeException(e);
		}
		return stream.toString();
	}

	private PageGenerator() {
		cfg = new Configuration(new Version(2,3,31));
	}
}
