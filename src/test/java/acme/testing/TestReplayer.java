/*
 * TestReplayer.java
 *
 * Copyright (C) 2012-2024 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.testing;

import java.io.File;
import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import acme.client.helpers.FileHelper;
import acme.client.testing.ReplayerAbstractTest;
import acme.client.testing.TraceReplayer;

@Order(1000)
public class TestReplayer extends ReplayerAbstractTest {

	@Test
	public void test() {
		TraceReplayer replayer;
		final List<File> traceFiles;

		traceFiles = FileHelper.listFiles("./src/test/resources/", "trace");
		if (traceFiles.isEmpty())
			System.err.println("No trace resources were found!");
		else {
			replayer = new TraceReplayer();
			for (final File file : traceFiles)
				replayer.run(file);
		}
	}

}
