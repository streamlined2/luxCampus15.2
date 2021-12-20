package org.training.campus.onlineshop.view;

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
	private static final File TEMPLATE_FOLDER = new File("templates");

	private final Configuration cfg;

	public static PageGenerator instance() {
		return Holder.INSTANCE;
	}

	private static class Holder {
		private static final PageGenerator INSTANCE;
		static {
			try {
				INSTANCE = new PageGenerator();
			} catch (Exception e) {
				throw new InstantiationError(String.format(
						"can't instantiate page generator because template folder is invalid, %s", e.getMessage()));
			}
		}
	}

	public String getPage(String filename, Map<String, Object> data) {
		try {
			Template template = cfg.getTemplate(filename);
			Writer stream = new StringWriter();
			template.process(data, stream);
			return stream.toString();
		} catch (IOException | TemplateException e) {
			throw new ViewException(e);
		}
	}

	private PageGenerator() throws IOException {
		cfg = new Configuration(new Version(2, 3, 31));
		cfg.setDirectoryForTemplateLoading(TEMPLATE_FOLDER);
	}
}
