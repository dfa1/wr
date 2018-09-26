package com.humaorie.wr.cli;

import java.io.IOException;

import org.junit.Test;
import org.junit.Assert;
import org.junit.Ignore;

public class CliIT {

	@Test
	public void missingDictAndWord() throws IOException, InterruptedException {
		ProcessBuilder processBuilder = new ProcessBuilder("java", "-jar", "target/dist/wrcli.jar");
		Process process = processBuilder.start();
		int exitValue = process.waitFor();
		Assert.assertEquals(1, exitValue);
	}
	
	@Test
	public void missingWord() throws IOException, InterruptedException {
		ProcessBuilder processBuilder = new ProcessBuilder("java", "-jar", "target/dist/wrcli.jar", "enit");
		Process process = processBuilder.start();
		int exitValue = process.waitFor();
		Assert.assertEquals(1, exitValue);
	}

	@Ignore("bitbucket pipelines forbid access to api.wordreference.com")
	@Test
	public void happyPath() throws IOException, InterruptedException {
		ProcessBuilder processBuilder = new ProcessBuilder("java", "-jar", "target/dist/wrcli.jar", "enit", "word");
		Process process = processBuilder.start();
		int exitValue = process.waitFor();
		Assert.assertEquals(0, exitValue);
	}

}
