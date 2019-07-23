/*
 * Copyright 2019 the original author or authors.
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

package org.springframework.batch.item.avro;

import org.apache.avro.generic.GenericRecord;
import org.junit.Test;

import org.springframework.batch.item.avro.example.User;
import org.springframework.batch.item.avro.support.AvroItemReaderTestSupport;
import org.springframework.core.io.ClassPathResource;

/**
 * @author David Turanski
 */
public class AvroItemReaderTests extends AvroItemReaderTestSupport {

	@Test
	public void readGenericRecordsUsingResources() throws Exception {

		AvroItemReader<GenericRecord> itemReader = new AvroItemReader<>(dataResource, schemaResource);
		itemReader.setName(itemReader.getClass().getSimpleName());
		itemReader.setEmbeddedHeader(false);

		verify(itemReader, genericAvroGeneratedUsers());
	}


	@Test
	public void readSpecificUsers() throws Exception {

		AvroItemReader<User> itemReader = new AvroItemReader<>(dataResource, User.class);
		itemReader.setEmbeddedHeader(false);
		itemReader.setName(itemReader.getClass().getSimpleName());

		verify(itemReader, avroGeneratedUsers());
	}

	@Test
	public void readSpecificUsersWithEmbeddedSchema() throws Exception {

		AvroItemReader<User> itemReader = new AvroItemReader<>(dataResourceWithSchema, User.class);
		itemReader.setEmbeddedHeader(true);
		itemReader.setName(itemReader.getClass().getSimpleName());

		verify(itemReader, avroGeneratedUsers());
	}

	@Test(expected = IllegalStateException.class)
	public void dataResourceDoesNotExist() {
		new AvroItemReader<User>(new ClassPathResource("doesnotexist"), schemaResource);
	}

	@Test(expected = IllegalStateException.class)
	public void schemaResourceDoesNotExist() {
		new AvroItemReader<User>(dataResource, new ClassPathResource("doesnotexist"));
	}
}
