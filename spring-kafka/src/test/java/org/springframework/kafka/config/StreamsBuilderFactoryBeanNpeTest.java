/*
 * Copyright 2026-present the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.kafka.config;

import java.util.Properties;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for NPE fix in StreamsBuilderFactoryBean.
 *
 * @author Fix for issue #4434
 */
class StreamsBuilderFactoryBeanNpeTest {

	@Test
	void testGetStreamsConfigurationWithNullProperties() {
		StreamsBuilderFactoryBean factoryBean = new StreamsBuilderFactoryBean();
		factoryBean.setAutoStartup(false);
		
		// This should not throw NPE after the fix
		Properties config = factoryBean.getStreamsConfiguration();
		assertThat(config).isNull();
	}

	@Test
	void testGetStreamsConfigurationWithNonNullProperties() {
		StreamsBuilderFactoryBean factoryBean = new StreamsBuilderFactoryBean();
		Properties props = new Properties();
		props.setProperty("test.key", "test.value");
		factoryBean.setStreamsConfiguration(props);
		
		Properties config = factoryBean.getStreamsConfiguration();
		assertThat(config).isNotNull();
		assertThat(config.getProperty("test.key")).isEqualTo("test.value");
		// Should return a copy, not the same instance
		assertThat(config).isNotSameAs(props);
	}
}
