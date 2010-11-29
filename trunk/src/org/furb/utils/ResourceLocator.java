package org.furb.utils;

import java.io.File;
import java.io.InputStream;

/**
 * Classe utilitaria para localizacao
 * dos resources da aplicacao.
 * @since Nov 13, 2010
 */
public final class ResourceLocator {
	
	/**
	 * Construtor padrao.
	 */
	private ResourceLocator() {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Retorna o caminho absoluto do projeto
	 * no sistema do usuario.
	 * @return
	 */
	public static String getProjectPath() 
	{
		String projectPath = null;
		File projectFolder  = null;
		
		try {
			
			projectFolder = new File(ResourceLocator.class.getResource("/").toURI());
			projectPath = projectFolder.getAbsolutePath();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return projectPath;
	}
	
	/**
	 * Recupera o stream do arquivo existente no
	 * projeto ou dentro do jar.
	 * @param resourceName
	 * @param caller
	 * @return
	 */
	public static InputStream getResource(final String resourceName, final Class<?> caller) 
	{
		final String resource;
		if (resourceName.startsWith("/")) {
			resource = resourceName.substring(1);
		} else {
			final Package callerPackage = caller.getPackage();
			if (callerPackage != null) {
				resource = callerPackage.getName().replace('.', '/') + '/'
						+ resourceName;
			} else {
				resource = resourceName;
			}
		}
		final ClassLoader threadClassLoader = Thread.currentThread()
				.getContextClassLoader();
		if (threadClassLoader != null) {
			final InputStream is = threadClassLoader
					.getResourceAsStream(resource);
			if (is != null) {
				return is;
			}
		}

		final ClassLoader classLoader = caller.getClassLoader();
		if (classLoader != null) {
			final InputStream is = classLoader.getResourceAsStream(resource);
			if (is != null) {
				return is;
			}
		}

		return ClassLoader.getSystemResourceAsStream(resource);
	}
}
